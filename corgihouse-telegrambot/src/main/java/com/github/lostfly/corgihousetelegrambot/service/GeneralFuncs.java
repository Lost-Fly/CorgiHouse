package com.github.lostfly.corgihousetelegrambot.service;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.HELP_TEXT;

@Slf4j
@Component
public class GeneralFuncs {

    public String startCommandReceived(long chatId, String name) {
        log.info("Replied /start to user " + name + " " + chatId);
        return EmojiParser.parseToUnicode("Привет, " + name + ", рад тебя видеть!" + " :blush:");
    }


}
