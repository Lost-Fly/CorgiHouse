package com.github.lostfly.corgihousetelegrambot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
@Entity(name = "sessionDataTable")
public class UserSession {

    @Id
    private Long chatId;

    @Value("default")
    private String globalFunctionContext;

    @Value("default")
    private String registerFunctionContext;

    @Value("default")
    private String editFunctionContext;

    @Value("default")
    private String petRegisterFunctionContext;

    @Value("default")
    private String meetingRegisterFunctionContext;

    @Value("default")
    private String editMeetingFunctionContext;



    private Long meetingRegisterFunctionId;


    private Long numberEditMeeting;

}
