package com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants;

import com.vdurmont.emoji.EmojiParser;

public class ListMenusConstants {
    // LIST_MENUS
    // profileButtonKeyboard
    public static final String EDIT_PROFILE = "edit_profile";
    public static final String EDIT_PROFILE_BUTTON_TEXT = "Редактировать профиль";
    public static final String DELETE_PROFILE_QUESTION = "delete_profile_question";
    public static final String DELETE_PROFILE_BUTTON_TEXT = "Удалить профиль";


    // profileDeleteKeyboard
    public static final String DELETE_PROFILE_CONFIRM = "delete_profile_confirm";
    public static final String DELETE_PROFILE_DENY = "delete_profile_deny";
    public static final String DELETE_PROFILE_CONFIRM_TEXT = "Да";
    public static final String DELETE_PROFILE_DENY_TEXT = "Нет";


    // profileEditKeyboard
    public static final String EDIT_PROFILE_NAME_TEXT = "Имя";
    public static final String EDIT_PROFILE_NAME = "edit_profile_name";
    public static final String EDIT_PROFILE_LASTNAME_TEXT = "Фамилия";
    public static final String EDIT_PROFILE_LASTNAME = "edit_profile_lastname";
    public static final String EDIT_PROFILE_PHONE_NUMBER_TEXT = "Телефон";
    public static final String EDIT_PROFILE_PHONE_NUMBER = "edit_profile_phone_number";


    // petQuestionButtonKeyboard
    public static final String PET_QUESTION_CONFIRM_BUTTON_TEXT = "Да";
    public static final String PET_QUESTION_CONFIRM = "pet_question_confirm";
    public static final String PET_QUESTION_DENY_TEXT = "Нет";
    public static final String PET_QUESTION_DENY = "pet_question_deny";


    // petAddButtonKeyboard
    public static final String PET_ADD_TEXT = "Добавить";
    public static final String PET_ADD = "pet_add";
    public static final String PET_DELETE_SELECTION_TEXT = "Удалить";
    public static final String PET_DELETE_SELECTION = "delete_add";


    // meetingKeyboard
    public static final String MEETING_ADD_TEXT = "Создать";
    public static final String MEETING_ADD = "meeting_add";

    // createdMeetingKeyboard
    public static final String CREATED_MEETINGS_FULL_INFO_TEXT = "Подробнее";
    public static final String CREATED_MEETINGS_FULL_INFO = "created_meetings_full_info_text";

    // appliedMeetingKeyboard
    public static final String APPLIED_MEETINGS_FULL_INFO_TEXT = "Подробнее";
    public static final String APPLIED_MEETINGS_FULL_INFO = "applied_meetings_full_info";

    // searchMeetingKeyboard
    public static final String LIKE_MEETING_TEXT = EmojiParser.parseToUnicode(":heart:");
    public static final String LIKE_MEETING = "like_meeting";
    public static final String DISLIKE_MEETING_TEXT = EmojiParser.parseToUnicode(":broken_heart:");
    public static final String DISLIKE_MEETING = "dislike_meeting";
    public static final String PIN_MEETING_TEXT = EmojiParser.parseToUnicode(":stopwatch:");
    public static final String PIN_MEETING = "pin_meeting";

    // registrationKeyboard
    public static final String REGISTRATION_TEXT = "Зарегистрироваться";
    public static final String REGISTRATION = "registration";





}
