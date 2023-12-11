package com.github.lostfly.corgihousetelegrambot.service.api;

import com.github.lostfly.corgihousetelegrambot.config.BotConfig;
import com.github.lostfly.corgihousetelegrambot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.github.lostfly.corgihousetelegrambot.config.BotInitializer;
import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import com.github.lostfly.corgihousetelegrambot.repository.*;
import com.github.lostfly.corgihousetelegrambot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.PetFuncsConstants.INCORRECT_NUMBER_ANS;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.MeetingRegConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.INCORRECT_PET_BREED;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.INCORRECT_PET_TYPE;

@Slf4j
@Component
public class YandexWeather {

    public void sendSpecificMessage(long chatId, String msg_to_send) throws IOException {
        String telegramBotToken = "5872985123:AAF9v8cu2AjjIJC_M9CUA0VOva5BpVaqK6o";
        String telegramChatId = "-1001855827776";

        String encodedMessage = URLEncoder.encode(msg_to_send, StandardCharsets.UTF_8);
        String urlString = "https://api.telegram.org/bot" + telegramBotToken + "/sendMessage?chat_id=" + telegramChatId + "&text=" + encodedMessage;

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.connect();


    }


}
