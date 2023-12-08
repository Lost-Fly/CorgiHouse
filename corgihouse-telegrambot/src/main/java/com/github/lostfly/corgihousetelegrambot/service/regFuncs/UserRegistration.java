package com.github.lostfly.corgihousetelegrambot.service.regFuncs;

import com.github.lostfly.corgihousetelegrambot.repository.PetRepository;
import com.github.lostfly.corgihousetelegrambot.repository.SessionRepository;
import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.UserRegConstants.*;


@Slf4j
@Component
public class UserRegistration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private SessionRepository sessionRepository;


    public String initializeRegistration(Update update) {
        long chatId = 0;
        Message message = new Message();
        Chat chat = new Chat();

        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            message = update.getCallbackQuery().getMessage();
            chat = update.getCallbackQuery().getMessage().getChat();
        } else if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            chat = update.getMessage().getChat();
            message = update.getMessage();
        }

        if (userRepository.findById(message.getChatId()).isEmpty()) {

            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_USER_REGISTRATION, chatId);

            User user = new User();

            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            log.info("User saved to DB: " + user);
            sessionRepository.setRegUserContextByChatId(SET_NAME, chatId);
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

        return switch (sessionRepository.findByChatId(chatId).getRegisterFunctionContext()) {
            case (SET_NAME) -> SetName(chatId, messageText);
            case (SET_LAST_NAME) -> SetLastName(chatId, messageText);
            case (SET_PHONE_NUMBER) -> SetPhoneNumber(chatId, messageText);
            default -> INDEV_TEXT;
        };
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^(\\+7|7|8)?[0-9]{10}$";
        return phoneNumber.matches(phoneRegex);
    }

    private String SetPhoneNumber(long chatId, String messageText) {
        if (isValidPhoneNumber(messageText)) {
            userRepository.setPhoneNumberByChatId(messageText, chatId);
            sessionRepository.setRegUserContextByChatId(REGISTER_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return REGISTER_ENDED_TEXT;
        } else {
            return INCORRECT_PHONE_NUMBER;
        }
    }

    private boolean isValidName(String name) {
        String nameRegex = "^[^\\d!@#$%^&*()_+=~`:;\"><,./|\\\\]{1,40}$";
        return name.matches(nameRegex);
    }

    private String SetLastName(long chatId, String messageText) {
        if (isValidName(messageText)) {
            userRepository.setLastNameByChatId(messageText, chatId);
            sessionRepository.setRegUserContextByChatId(SET_PHONE_NUMBER, chatId);
            return (GIVE_PHONE_NUMBER_TEXT);
        } else {
            return INCORRECT_LAST_NAME;
        }
    }

    private String SetName(long chatId, String messageText) {
        if (isValidName(messageText)) {
            userRepository.setFirstNameByChatId(messageText, chatId);
            sessionRepository.setRegUserContextByChatId(SET_LAST_NAME, chatId);
            return (GIVE_LAST_NAME_TEXT);
        } else {
            return INCORRECT_FIRST_NAME;
        }
    }

}
