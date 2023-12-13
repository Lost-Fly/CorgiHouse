package com.github.lostfly.corgihousetelegrambot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QuerySession.DEFAULT_CONTEXT_STATE_VALUE;
import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QuerySession.ENTITY_NAME_USER_SESSION;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_USER_SESSION)
public class UserSession {

    @Id
    private Long chatId;

    @Value(DEFAULT_CONTEXT_STATE_VALUE)
    private String globalFunctionContext;

    @Value(DEFAULT_CONTEXT_STATE_VALUE)
    private String registerFunctionContext;

    @Value(DEFAULT_CONTEXT_STATE_VALUE)
    private String editFunctionContext;

    @Value(DEFAULT_CONTEXT_STATE_VALUE)
    private String petRegisterFunctionContext;

    @Value(DEFAULT_CONTEXT_STATE_VALUE)
    private String meetingRegisterFunctionContext;

    @Value(DEFAULT_CONTEXT_STATE_VALUE)
    private String editMeetingFunctionContext;


    private Long meetingRegisterFunctionId;


    private Long numberEditMeeting;

}
