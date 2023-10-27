package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.config.BotConfig;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private UserRegistration userRegistration;

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

    public static String globalFunctionContext = GLOBAL_CONTEXT_DEFAULT;

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

        long chatId = update.getMessage().getChatId();

        if (update.hasMessage() && Objects.equals(globalFunctionContext, GLOBAL_CONTEXT_DEFAULT)) {
            String messageText = update.getMessage().getText();

            if (update.getMessage().hasPhoto()) {
                downloadAndSendImage(update);
            }

            switch (messageText) {
                case "/start":
                    sendMessage(chatId, generalFuncs.startCommandReceived(chatId, update.getMessage().getChat().getFirstName()));
                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    break;
                case "/register":
                    sendMessage(chatId, userRegistration.initializeRegistration(update));
                    break;
                case "/register_pet":
                    sendMessage(chatId, petRegistration.registerPet());
                    break;
                case "Питомцы":
                    sendMessage(chatId, petsFuncs.showPets(chatId));
                    break;
                case "Профиль":
                    sendMessage(chatId, userFuncs.showProfile(chatId));
                    break;
                default:
                    sendMessage(chatId, INDEV_TEXT);
                    break;
            }
        } else {
            switch (globalFunctionContext) {
                case GLOBAL_CONTEXT_USER_REGISTRATION:
                    sendMessage(chatId, userRegistration.continueRegistration(update));
                    break;
                default:
                    globalFunctionContext = GLOBAL_CONTEXT_DEFAULT;
                    log.error("No global context found -  " + update.getMessage().getText());
                    sendMessage(chatId, INDEV_TEXT);
                    break;
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

    void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        // ----------------TO_DECOMPOSE--------------------
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row1 = new KeyboardRow();

        row1.add("Профиль");
        row1.add("Поиск встреч");
        keyboardRows.add(row1);

        KeyboardRow row2 = new KeyboardRow();

        row2.add("Питомцы");
        row2.add("Мои встречи");
        keyboardRows.add(row2);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);
        // ------------------------------------

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred" + e.getMessage());
        }
    }

}
