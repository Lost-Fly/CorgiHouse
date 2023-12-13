package com.github.lostfly.corgihousetelegrambot.constants.queryConstants;

public class QueryUserToMeeting {
    // SQL QUERY FOR USER_TO_MEETING MODEL
    public static final String ENTITY_NAME_USER_TO_MEETING = "userToMeetingDataTable";
    public static final String FIND_TOP_BY_ORDER_BY_ID_DESC = "select max(element.id) from userToMeetingDataTable element";

}
