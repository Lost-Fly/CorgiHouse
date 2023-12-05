package com.github.lostfly.corgihousetelegrambot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String token;

    public DefaultBotOptions getBotDefaultOptions(){
        DefaultBotOptions options = new DefaultBotOptions();
        options.setMaxThreads(10);

        return options;
    }

}
