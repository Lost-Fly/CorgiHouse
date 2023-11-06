package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.config.BotConfig;
import com.github.lostfly.corgihousetelegrambot.keyboardMenus.KeyboardMenus;
import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.SessionRepository;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus.DELETE_PROFILE_QUESTION;


@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRegistration userRegistration;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PetRegistration petRegistration;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private GeneralFuncs generalFuncs;

    @Autowired
    private UserFuncs userFuncs;
    @Autowired
    private PetsFuncs petsFuncs;
    @Autowired
    private FileService fileService;

    @Autowired
    private KeyboardMenus keyboardMenus;
    @Autowired
    private ListMenus listMenus;

    final BotConfig config;

    public TelegramBot(BotConfig config) {

        this.config = config;
        // ----------------TO_DECOMPOSE--------------------
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "Начать"));
        listofCommands.add(new BotCommand("/help", "Помощь"));
        listofCommands.add(new BotCommand("/register", "Регистрация пользователя"));
        // ------------------------------------
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot command list " + e.getMessage());
        }

    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        }

        sessionService.CreateSession(chatId);


        if (update.hasCallbackQuery()) {

            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            String callBackData = update.getCallbackQuery().getData();

            switch (callBackData) {
                case DELETE_PROFILE_QUESTION:
                    sendEditMessage(chatId, messageId, userFuncs.deleteProfileQuestion(), listMenus.profileDeleteKeyboard());
                    break;
                default:
                    sendMessage(chatId, INDEV_TEXT, keyboardMenus.mainKeyboard());
                    break;
            }
        }


        if (update.hasMessage() && Objects.equals(sessionRepository.findByChatId(chatId).getGlobalFunctionContext(), GLOBAL_CONTEXT_DEFAULT)) {
            String messageText = update.getMessage().getText();

            switch (messageText) {
                case "/start" ->
                        sendMessage(chatId, generalFuncs.startCommandReceived(chatId, update.getMessage().getChat().getFirstName()), keyboardMenus.mainKeyboard());
                case "/help" -> sendMessage(chatId, HELP_TEXT, keyboardMenus.mainKeyboard());
                case "/register" ->
                        sendMessage(chatId, userRegistration.initializeRegistration(update), keyboardMenus.mainKeyboard());
                case "/register_pet" ->
                        sendMessage(chatId, petRegistration.registerPet(), keyboardMenus.mainKeyboard());
                case "Питомцы" -> sendMessage(chatId, petsFuncs.showPets(chatId), keyboardMenus.mainKeyboard());
                case "Профиль" -> sendMessage(chatId, userFuncs.showProfile(chatId), listMenus.profileButtonKeyboard());
                default -> sendMessage(chatId, INDEV_TEXT, keyboardMenus.mainKeyboard());
            }
        } else {
            switch (sessionRepository.findByChatId(chatId).getGlobalFunctionContext()) {
                case GLOBAL_CONTEXT_USER_REGISTRATION ->
                        sendMessage(chatId, userRegistration.continueRegistration(update), keyboardMenus.mainKeyboard());
                case GLOBAL_CONTEXT_PET_REGISTRATION -> sendMessage(chatId, INDEV_TEXT, keyboardMenus.mainKeyboard());
                default -> {
                    sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                    log.error("No global context found -  " + update.getMessage().getText());
                    sendMessage(chatId, INDEV_TEXT, keyboardMenus.mainKeyboard());
                }
            }
        }

    }


    private void downloadAndSendImage(Update update) {

        long chatId = update.getMessage().getChatId();

        PhotoSize photo = update.getMessage().getPhoto().stream().findFirst().orElse(null);

        if (photo != null) {
            try {

                GetFile getFileRequest = new GetFile();
                getFileRequest.setFileId(photo.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFileRequest);
                java.io.File localFile = fileService.downloadPhotoByFilePath(file.getFilePath(), PHOTO_STORAGE_DIR, chatId, getBotToken());
                sendPhoto(chatId, localFile);

            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendPhoto(Long chatId, File imageFile) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(imageFile));
        execute(sendPhoto);
    }

    void sendMessage(long chatId, String textToSend, ReplyKeyboardMarkup msgKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(msgKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred" + e.getMessage());
        }
    }

    void sendMessage(long chatId, String textToSend, InlineKeyboardMarkup msgKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(msgKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred" + e.getMessage());
        }
    }

    void sendEditMessage(long chatId, long messageId, String textToSend, InlineKeyboardMarkup msgKeyboard) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setMessageId((int) messageId);
        message.setText(textToSend);
        message.setReplyMarkup(msgKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred" + e.getMessage());
        }
    }


}
