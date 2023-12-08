package com.github.lostfly.corgihousetelegrambot.constants.regConstants;

import com.vdurmont.emoji.EmojiParser;

public class MeetingRegConstants {

    // MEETING_REGISTRATION
    // meeting registration func context
    public static final String SET_MEETING_TITLE = "set_meeting_title";
    public static final String SET_MEETING_DESCRIPTION = "set_meeting_description";
    public static final String SET_MEETING_PLACE = "set_meeting_place";
    public static final String SET_MEETING_EVENT_DATE = "set_meeting_event_date";
    public static final String SET_MEETING_USER_LIMIT = "set_meeting_user_limit";
    public static final String SET_MEETING_ANIMAL_TYPE = "set_meeting_animal_type";
    public static final String SET_MEETING_BREED = "set_meeting_breed";


    // meeting registration func texts
    public static final String SET_MEETING_TITLE_TEXT = "Введите заголовок события:";
    public static final String SET_MEETING_DESCRIPTION_TEXT = "Введите описание события:";
    public static final String SET_MEETING_PLACE_TEXT = "Введите место(адрес) проведения события:";
    public static final String SET_MEETING_EVENT_DATE_TEXT =
            EmojiParser.parseToUnicode("Введите дату проведения события:" + "\n" + ":exclamation:" +
                    " Вводить в формате: 31.04.2023 10:00 " + ":exclamation:");
    public static final String SET_MEETING_USER_LIMIT_TEXT = "Введите максимальное количество участников события:";
    public static final String INCORRECT_DATE_TEXT = "Дата неверна! Введите ещё раз:";
    public static final String SET_MEETING_ANIMAL_TYPE_TEXT = "Введите тип животных события:";
    public static final String SET_MEETING_BREED_TEXT = "Введите пароду животных события:";
    public static final String REGISTER_MEETING_END_TEXT = "Создание события успешно завершено!";

    public static final String INCORRECT_MEETING_TITLE = "Неверный формат заголовка встречи. Пожалуйста, используйте " +
            "только буквы и смайлики.";
    public static final String INCORRECT_MEETING_DESCRIPTION = "Неверный формат / или превышена допустимая длина " +
            "описания встречи. Пожалуйста, используйте только буквы и смайлики.";
    public static final String INCORRECT_MEETING_PLACE = "Неверный формат места проведения встречи. Пожалуйста, " +
            "используйте только буквы и смайлики.";


}
