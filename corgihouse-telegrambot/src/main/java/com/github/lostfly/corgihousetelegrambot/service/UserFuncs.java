package com.github.lostfly.corgihousetelegrambot.service;

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

import static com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus.DELETE_PROFILE_TEXT;

@Slf4j
@Component
public class UserFuncs {

    @Autowired
    private UserRepository userRepository;

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

    public String deleteProfileQuestion(){
       return DELETE_PROFILE_TEXT;
    }


}
