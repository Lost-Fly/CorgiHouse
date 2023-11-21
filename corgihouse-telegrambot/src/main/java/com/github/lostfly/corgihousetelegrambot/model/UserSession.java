package com.github.lostfly.corgihousetelegrambot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Value;

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

    private Long meetingRegisterFunctionId;

    public Long getMeetingRegisterFunctionId() {
        return meetingRegisterFunctionId;
    }

    public void setMeetingRegisterFunctionId(Long meetingRegisterFunctionId) {
        this.meetingRegisterFunctionId = meetingRegisterFunctionId;
    }

    public String getMeetingRegisterFunctionContext() {
        return meetingRegisterFunctionContext;
    }

    public void setMeetingRegisterFunctionContext(String meetingRegisterFunctionContext) {
        this.meetingRegisterFunctionContext = meetingRegisterFunctionContext;
    }

    public String getPetRegisterFunctionContext() {
        return petRegisterFunctionContext;
    }

    public void setPetRegisterFunctionContext(String petRegisterFunctionContext) {
        this.petRegisterFunctionContext = petRegisterFunctionContext;
    }


    public String getEditFunctionContext() {
        return editFunctionContext;
    }

    public void setEditFunctionContext(String editFunctionContext) {
        this.editFunctionContext = editFunctionContext;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getGlobalFunctionContext() {
        return globalFunctionContext;
    }

    public void setGlobalFunctionContext(String globalFunctionContext) {
        this.globalFunctionContext = globalFunctionContext;
    }

    public String getRegisterFunctionContext() {
        return registerFunctionContext;
    }

    public void setRegisterFunctionContext(String registerFunctionContext) {
        this.registerFunctionContext = registerFunctionContext;
    }

    @Override
    public String toString() {
        return "Session{" +
                "chatId=" + chatId +
                '}';
    }
}
