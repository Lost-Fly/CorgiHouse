package com.github.lostfly.corgihousetelegrambot.constants.queryConstants;

public class QueryUser {
    // SQL QUERY FOR USER MODEL
    public static final String ENTITY_NAME_USER = "usersDataTable";
    public static final String SET_FIRST_NAME_BY_CHAT_ID = "update usersDataTable u set u.firstName = ?1 where u.chatId = ?2";
    public static final String SET_LAST_NAME_BY_CHAT_ID = "update usersDataTable u set u.lastName = ?1 where u.chatId = ?2";
    public static final String SET_PHONE_NUMBER_BY_CHAT_ID = "update usersDataTable u set u.phoneNumber = ?1 where u.chatId = ?2";

}
