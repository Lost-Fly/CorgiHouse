package com.github.lostfly.corgihousetelegrambot.constants.queryConstants;

public class QueryMeeting {
    // SQL QUERY FOR MEETING MODEL
    public static final String ENTITY_NAME_MEETING = "meetingDataTable";
    public static final String FIND_MY_APPLIED_MEETINGS = "SELECT m FROM meetingDataTable m " +
            "JOIN userToMeetingDataTable utm ON m.meetingId = utm.meetingId " +
            "WHERE utm.chatId = :chatId AND utm.chatId != m.ownerId";
    public static final String PARAM_CHAT_ID = "chatId";
    public static final String FIND_TOP_BY_ORDER_BY_MEETING_ID_DESC = "select max(meeting.meetingId) from meetingDataTable meeting";
    public static final String SET_ANIMAL_TYPE_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.animalType = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_BREED_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.breed = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_TITLE_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.title = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_DESCRIPTION_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.description = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_PLACE_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.place = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_COMPLETED_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.completed = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_FULFILLED_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.fullFilled = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_USER_LIMIT_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.userLimit = ?1 where u.ownerId = ?2 and u.meetingId = ?3";
    public static final String SET_EVENT_DATE_BY_OWNER_ID_AND_MEETING_ID = "update meetingDataTable u set u.eventDate = ?1 where u.ownerId = ?2 and u.meetingId = ?3";

}
