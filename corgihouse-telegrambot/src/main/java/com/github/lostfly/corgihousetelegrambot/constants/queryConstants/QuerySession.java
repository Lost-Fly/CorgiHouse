package com.github.lostfly.corgihousetelegrambot.constants.queryConstants;

public class QuerySession {
    // SQL QUERY FOR USER_SESSION MODEL
    public static final String ENTITY_NAME_USER_SESSION = "sessionDataTable";
    public static final String DEFAULT_CONTEXT_STATE_VALUE = "default";
    public static final String SET_GLOBAL_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.globalFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_REG_USER_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.registerFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_EDIT_USER_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.editFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_PET_REGISTER_FUNCTION_CONTEXT = "update sessionDataTable u set u.petRegisterFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_MEETING_REGISTER_FUNCTION_CONTEXT = "update sessionDataTable u set u.meetingRegisterFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_MEETING_REGISTER_FUNCTION_ID = "update sessionDataTable u set u.meetingRegisterFunctionId = ?1 where u.chatId = ?2";
    public static final String SET_EDIT_MEETING_FUNCTION_CONTEXT_BY_CHAT_ID = "update sessionDataTable u set u.editMeetingFunctionContext = ?1 where u.chatId = ?2";
    public static final String SET_NUMBER_EDIT_MEETING_BY_CHAT_ID = "update sessionDataTable u set u.numberEditMeeting = ?1 where u.chatId = ?2";

}
