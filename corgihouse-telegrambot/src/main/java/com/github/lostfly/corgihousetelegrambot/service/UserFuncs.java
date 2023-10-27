package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFuncs {

    @Autowired
    private UserRepository userRepository;
    public String showProfile(long chatId) {
        User user = userRepository.findByChatId(chatId);

        return "ID пользователя: " + user.getChatId() + "\n" +
                "Логин: " + user.getUserName() + "\n" +
                "Имя: " + user.getFirstName() + "\n" +
                "Фамилия: " + user.getLastName() + "\n" +
                "Телефон: " + user.getPhoneNumber() + "\n" +
                "Дата Регистрации: " + user.getRegisteredAt() + "\n\n";
    }

}
