package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.config.BotConfig;
import com.github.lostfly.corgihousetelegrambot.keyboardMenus.KeyboardMenus;
import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.repository.PetRepository;
import com.github.lostfly.corgihousetelegrambot.repository.SessionRepository;
import com.github.lostfly.corgihousetelegrambot.repository.UserRepository;
import com.github.lostfly.corgihousetelegrambot.service.commandProcessor.CommandProcessor;
import com.github.lostfly.corgihousetelegrambot.service.generalFuncs.FileService;
import com.github.lostfly.corgihousetelegrambot.service.generalFuncs.GeneralFuncs;
import com.github.lostfly.corgihousetelegrambot.service.sessionService.SessionService;
import com.github.lostfly.corgihousetelegrambot.service.modelsConnectedFuncs.*;
import com.github.lostfly.corgihousetelegrambot.service.regFuncs.MeetingRegistration;
import com.github.lostfly.corgihousetelegrambot.service.regFuncs.PetRegistration;
import com.github.lostfly.corgihousetelegrambot.service.regFuncs.UserRegistration;
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

import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.CommandListConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    public UserRegistration userRegistration;

    @Autowired
    public SessionRepository sessionRepository;

    @Autowired
    public SessionService sessionService;

    @Autowired
    public PetRegistration petRegistration;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PetRepository petRepository;

    @Autowired
    public GeneralFuncs generalFuncs;

    @Autowired
    public UserFuncs userFuncs;
    @Autowired
    public PetsFuncs petsFuncs;

    @Autowired
    public FileService fileService;

    @Autowired
    public KeyboardMenus keyboardMenus;
    @Autowired
    public ListMenus listMenus;

    @Autowired
    public MeetingFuncs meetingFuncs;

    @Autowired
    public MeetingRegistration meetingRegistration;
    @Autowired
    public SearchMeetings searchMeetings;

    final BotConfig config;

    final CommandProcessor commandProcessor = new CommandProcessor(this);

    public TelegramBot(BotConfig config) {

        super(config.getBotDefaultOptions());

        this.config = config;

        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand(START_MENU_COMMAND, START_MENU_COMMAND_TEXT));
        listOfCommands.add(new BotCommand(HELP_MENU_COMMAND, HELP_MENU_COMMAND_TEXT));
        listOfCommands.add(new BotCommand(REGISTER_MENU_COMMAND, REGISTER_MENU_COMMAND_TEXT));

        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(ERROR_SETTING_BOT_COMMAND + e.getMessage());
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
        long chatId = update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getChatId() :
                update.hasMessage() ?
                        update.getMessage().getChatId() :
                        0;

        long messageId = update.hasCallbackQuery() ?
                update.getCallbackQuery().getMessage().getMessageId() :
                update.hasMessage() ?
                        update.getMessage().getMessageId() :
                        0;

        sessionService.CreateSession(chatId);

        if (update.hasCallbackQuery()) {
            String callBackData = update.getCallbackQuery().getData();
            commandProcessor.processCallBackCommand(callBackData, chatId, messageId, update);
        }
        if (update.hasMessage() && Objects.equals(sessionRepository.findByChatId(chatId).getGlobalFunctionContext(), GLOBAL_CONTEXT_DEFAULT)) {
            String messageText = update.getMessage().getText();
            commandProcessor.processTextCommandDefaultContext(messageText, chatId, messageId, update);
        } else {
            String messageText = update.getMessage().getText();
            String globalFunctionContext = sessionRepository.findByChatId(chatId).getGlobalFunctionContext();
            commandProcessor.processGlobalContextCommand(globalFunctionContext, messageText, messageId, chatId, update);
        }

    }

    public void adminBroadcastNews(String msg, Long chatId) {
        ArrayList<User> users = userRepository.findAll();
        for (User user : users) {
            sendMessage(user.getChatId(), generateAdminBroadcastMessage(msg));
        }
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
    }

    public void downloadImage(Update update, Long pet_id) {

        long chatId = update.getMessage().getChatId();

        PhotoSize photo = update.getMessage().getPhoto().stream().reduce((first, second) -> second).orElse(null);

        if (photo != null) {
            try {

                GetFile getFileRequest = new GetFile();
                getFileRequest.setFileId(photo.getFileId());
                org.telegram.telegrambots.meta.api.objects.File file = execute(getFileRequest);
                fileService.downloadPhotoByFilePath(file.getFilePath(), PHOTO_STORAGE_DIR, chatId, pet_id, getBotToken());

            } catch (TelegramApiException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPhoto(Long chatId, File imageFile, String text_to_send) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(imageFile));
        sendPhoto.setCaption(text_to_send);
        execute(sendPhoto);
    }

    private void sendPhoto(Long chatId, File imageFile) throws TelegramApiException {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setPhoto(new InputFile(imageFile));
        execute(sendPhoto);
    }

    public void sendMessage(long chatId, String textToSend, ReplyKeyboardMarkup msgKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(msgKeyboard);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }

    public void sendMessage(long chatId, String textToSend, InlineKeyboardMarkup msgKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        message.setReplyMarkup(msgKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }

    public void sendMessage(long chatId, SendMessage message) {
        if (message == null) {
            return;
        }
        message.setChatId(String.valueOf(chatId));
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }


    public void sendEditMessage(long chatId, long messageId, String textToSend, InlineKeyboardMarkup msgKeyboard) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setMessageId((int) messageId);
        message.setText(textToSend);
        message.setReplyMarkup(msgKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }

    public void sendEditMessage(long chatId, long messageId, String textToSend) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setMessageId((int) messageId);
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }


}
