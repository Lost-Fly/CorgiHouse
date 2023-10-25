package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.sql.Timestamp;
import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.UserRegConstants.*;
import static com.github.lostfly.corgihousetelegrambot.service.TelegramBot.*;

@Slf4j
@Component
public class UserRegistration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;


    public static String registerFunctionContext = REGISTER_CONTEXT_DEFAULT;

    public String initializeRegistration(Update update) {

        var chatId = update.getMessage().getChatId();
        var chat = update.getMessage().getChat();
        var message = update.getMessage();


        if (userRepository.findById(message.getChatId()).isEmpty()) {

            globalFunctionContext = GLOBAL_CONTEXT_USER_REGISTRATION;

            User user = new User();

            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            log.info("User saved to DB: " + user);
            registerFunctionContext = SET_NAME;
            return NewUserCommandReceived(chatId);

        } else {
            return registeredUserCommandReceived(chatId, message.getChat().getFirstName());
        }

    }

    private static String NewUserCommandReceived(long chatId) {
        log.info("Register user " + " " + chatId);
        return NEW_USER_TEXT;
    }

    private static String registeredUserCommandReceived(long chatId, String name) {
        return REGISTERED_USER_TEXT;
    }


    public String continueRegistration(Update update) {
        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        switch (registerFunctionContext) {
            case (SET_NAME):
                return SetName(chatId, messageText);

            case (SET_LAST_NAME):
                return SetLastName(chatId, messageText);

            case (SET_PHONE_NUMBER):
                return SetPhoneNumber(chatId, messageText);

            default:
                return INDEV_TEXT;
        }
    }


    private String SetPhoneNumber(long chatId, String messageText) {
        userRepository.setPhoneNumberByChatId(messageText, chatId);
        registerFunctionContext = REGISTER_CONTEXT_DEFAULT;
        globalFunctionContext = GLOBAL_CONTEXT_DEFAULT;
        return (REGISTER_ENDED_TEXT);
    }

    private String SetLastName(long chatId, String messageText) {
        userRepository.setLastNameByChatId(messageText, chatId);
        registerFunctionContext = SET_PHONE_NUMBER;
        return (GIVE_PHONE_NUMBER_TEXT);
    }

    private String SetName(long chatId, String messageText) {
        userRepository.setFirstNameByChatId(messageText, chatId);
        registerFunctionContext = SET_LAST_NAME;
        return (GIVE_LAST_NAME_TEXT);
    }

}
