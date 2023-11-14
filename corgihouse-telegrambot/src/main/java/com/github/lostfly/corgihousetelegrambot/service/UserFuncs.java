package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.SessionRepository;
import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.UserRegConstants.*;
import static com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus.*;

@Slf4j
@Component
public class UserFuncs {

    public static final String DELETE_PROFILE_TEXT = "Профиль успешно удален";
    public static final String EDIT_CHOISE = "Выберите поле для редактирования";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private SessionRepository sessionRepository;


    public String showProfile(long chatId) {
        User user = userRepository.findByChatId(chatId);

        String profileInfo = "ID пользователя: " + user.getChatId() + "\n" +
                "Логин: " + user.getUserName() + "\n" +
                "Имя: " + user.getFirstName() + "\n" +
                "Фамилия: " + user.getLastName() + "\n" +
                "Телефон: " + user.getPhoneNumber() + "\n" +
                "Дата Регистрации: " + user.getRegisteredAt() + "\n\n";


        return profileInfo;

    }

    public String deleteProfileQuestion() {
        return DELETE_PROFILE_QUESTION_TEXT;
    }


    public String deleteProfile(Long chatId) {
        userRepository.deleteByChatId(chatId);
        petRepository.deleteByOwnerId(chatId);
        sessionRepository.deleteByChatId(chatId);
        return DELETE_PROFILE_TEXT;
    }



//    public String editProfileName(long chatId) {
//
//    }
//
//    public String editProfileLastName(long chatId) {
//    }
//
//    public String editProfilePhoneNumber(long chatId) {
//    }
    public String editProfile(long chatId, String editContext ) {

        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_USER_EDIT, chatId);

        switch(editContext){
            case EDIT_PROFILE_NAME:
                sessionRepository.setEditUserContextByChatId(EDIT_PROFILE_NAME, chatId);

                return GIVE_NAME_TEXT;

            case EDIT_PROFILE_LASTNAME:
                sessionRepository.setEditUserContextByChatId(EDIT_PROFILE_LASTNAME, chatId);
                return GIVE_LAST_NAME_TEXT;

            case EDIT_PROFILE_PHONE_NUMBER:
                sessionRepository.setEditUserContextByChatId(EDIT_PROFILE_PHONE_NUMBER, chatId);
                return GIVE_PHONE_NUMBER_TEXT;
            default:
                return INDEV_TEXT;

        }
    }

    public String editProfileAction(long chatId, String messageText) {
        switch(sessionRepository.findByChatId(chatId).getEditFunctionContext()){
            case EDIT_PROFILE_NAME:
                userRepository.setFirstNameByChatId(messageText, chatId);
                sessionRepository.setEditUserContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                return "Имя изменено";

            case EDIT_PROFILE_LASTNAME:
                userRepository.setLastNameByChatId(messageText, chatId);
                sessionRepository.setEditUserContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                return "Фамилия изменена";

            case EDIT_PROFILE_PHONE_NUMBER:
                userRepository.setPhoneNumberByChatId(messageText, chatId);
                sessionRepository.setEditUserContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                return "Телефон изменен";
            default:
                return INDEV_TEXT;

        }
    }
}
