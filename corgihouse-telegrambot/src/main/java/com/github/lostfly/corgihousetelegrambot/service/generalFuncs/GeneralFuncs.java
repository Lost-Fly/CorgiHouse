package com.github.lostfly.corgihousetelegrambot.service.generalFuncs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.generateStartMeetingMessage;
import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.START_COMMAND_USED_LOG;

@Slf4j
@Component
public class GeneralFuncs {

    public String startCommandReceived(long chatId, String name) {
        log.info(START_COMMAND_USED_LOG + chatId);
        return generateStartMeetingMessage(name);
    }


}
