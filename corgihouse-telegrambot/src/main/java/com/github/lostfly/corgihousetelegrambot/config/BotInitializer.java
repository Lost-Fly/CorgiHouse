package com.github.lostfly.corgihousetelegrambot.config;

import com.github.lostfly.corgihousetelegrambot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.ERROR_OCCURRED;


@Slf4j
@Component
public class BotInitializer {
    @Autowired
    TelegramBot bot;
    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            log.error(ERROR_OCCURRED + e.getMessage());
        }
    }

}
