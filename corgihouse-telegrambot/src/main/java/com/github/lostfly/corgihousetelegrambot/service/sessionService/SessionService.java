package com.github.lostfly.corgihousetelegrambot.service.sessionService;

import com.github.lostfly.corgihousetelegrambot.repository.SessionRepository;
import com.github.lostfly.corgihousetelegrambot.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.GLOBAL_CONTEXT_DEFAULT;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.UserRegConstants.REGISTER_CONTEXT_DEFAULT;

@Slf4j
@Component
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public void CreateSession(long chatId){
        if (sessionRepository.findById(chatId).isEmpty()) {

            UserSession userSession = new UserSession();
            userSession.setChatId(chatId);
            userSession.setGlobalFunctionContext(GLOBAL_CONTEXT_DEFAULT);
            userSession.setRegisterFunctionContext(REGISTER_CONTEXT_DEFAULT);
            userSession.setEditFunctionContext(REGISTER_CONTEXT_DEFAULT);
            userSession.setPetRegisterFunctionContext(REGISTER_CONTEXT_DEFAULT);

            sessionRepository.save(userSession);

            log.info("New session saved to DB: " + userSession);
        }
    }

}
