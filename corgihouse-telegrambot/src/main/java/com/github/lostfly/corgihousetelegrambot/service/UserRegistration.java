package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.config.BotConfig;
import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.User;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.interfaces.BotApiObject;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Objects;
import java.util.Optional;

import static com.github.lostfly.corgihousetelegrambot.service.TelegramBot.globalFunctionContext;

@Slf4j
@Component
public class UserRegistration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public static String registerFunctionContext = "default";

    public String initializeRegistration(Update update) {

        var chatId = update.getMessage().getChatId();
        var chat = update.getMessage().getChat();
        var message = update.getMessage();


        if (userRepository.findById(message.getChatId()).isEmpty()) {

            globalFunctionContext="UserRegistration";

            User user = new User();

            user.setChatId(chatId);
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            log.info("user saved: " + user);
            registerFunctionContext = "set_name";
            return NewUserCommandReceived(chatId);

        }
        else {
            System.out.println(" user");
            System.out.println("funcContext" + " " + registerFunctionContext);
            return registeredUserCommandReceived(chatId, message.getChat().getFirstName());
        }

    }


    private static String NewUserCommandReceived(long chatId) {
        String answer = "Раздел регистрации! Сначала введите имя: ";
        log.info("Register user " + " " + chatId);
        return answer;
    }

    private static String registeredUserCommandReceived(long chatId, String name) {
        return "Вы уже зарегистрированны)";
    }


    public String continueRegistration(Update update) {
        switch (registerFunctionContext) {
            case ("set_name"):
                return Set_name(update);

            case ("set_lastname"):
                return Set_lastname(update);

            case ("set_phonenumber"):
                return Set_phonenumber(update);

            default:
                return "no such function context";
        }
    }


    private  String Set_phonenumber(Update update) {
        var chatId = update.getMessage().getChatId();
        var message = update.getMessage();
        userRepository.setPhoneNumberByChatId(message.getText(),chatId);
        registerFunctionContext="default";
        globalFunctionContext="default";
        return("Регистрация завершена");

    }

    private String Set_lastname(Update update) {
        var chatId = update.getMessage().getChatId();
        var message = update.getMessage();
        userRepository.setLastNameByChatId(message.getText(),chatId);
        registerFunctionContext="set_phonenumber";
        return("Введите номер телефона:");
    }

    private String Set_name(Update update) {
        var chatId = update.getMessage().getChatId();
        var message = update.getMessage();
        userRepository.setFirstNameByChatId(message.getText(),chatId);
        registerFunctionContext="set_lastname";
        return("Введите фамилию:");
    }

}
