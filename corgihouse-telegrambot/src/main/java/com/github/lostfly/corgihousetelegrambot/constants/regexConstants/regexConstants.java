package com.github.lostfly.corgihousetelegrambot.constants.regexConstants;

import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.MeetingRegConstants.FORBIDDEN_WORDS;

public class regexConstants {
    // REGULAR EXPRESSIONS CONSTANTS
    // regex

    public static final String PHONE_REGEX = "^(\\+7|7|8)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$";

    public static final String NAME_REGEX = "^[^0-9~`'\".,<>/\\\\|!@#^&*()+=]{1,40}$";

    public static final String FORBIDDEN_WORDS_REGEX = String.format("^(?:(?!%s).){1,100}$", FORBIDDEN_WORDS);

    public static final String PLACE_REGEX = "^[a-zA-Zа-яА-Я\\s]+,[a-zA-Zа-яА-Я\\s]+,[\\da-zA-Zа-яА-Я\\s]+$";

    public static final String DATE_FORMAT_STYLE = "dd.MM.yyyy HH:mm";

}
