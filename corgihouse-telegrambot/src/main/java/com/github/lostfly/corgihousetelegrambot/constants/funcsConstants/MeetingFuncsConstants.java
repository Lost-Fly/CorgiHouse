package com.github.lostfly.corgihousetelegrambot.constants.funcsConstants;

import com.vdurmont.emoji.EmojiParser;

public class MeetingFuncsConstants {
    // MEETING_FUNCS
    // meetingFuncs texts
    public static final String NO_MEETINGS_TEXT = """
            У вас нет созданных встреч и встреч на которые вы записались.
            Если хотите записаться перейдите в раздел Поиск встреч.
            Чтобы создать свою нажмите Создать.""";

    public static final String SELECT_MEETINGS_TYPE_TEXT = "Выберете встречи которые хотите просмотреть";

    public static final String NO_APPLIED_MEETINGS_TEXT = """
            У вас нет встреч на которые вы записались.
            Если хотите записаться перейдите в раздел Поиск встреч.""";

    public static final String NO_CREATED_MEETINGS_TEXT = """
            У вас нет созданных встреч.
            Чтобы создать свою встречу нажмите Создать.""";
    public static final String CHANGE_TO_MY_MEETINGS_TEXT = EmojiParser.parseToUnicode("Раздел просмотра событий" );
    public static final String CHANGE_TO_MAIN_MENU = EmojiParser.parseToUnicode("Главное меню" );
    public static final String SELECT_FULL_MEETING_INFO_TEXT = """
            Введите ID встречи (без лишних символов, только цифры) для просмотра полной информации по ней.""";

    public static final String INCORRECT_FULL_INFO_NUMBER_ANS = "Ты дурак! Вам в первый класс.";
    public static final String SELECT_MEETING_DELETE_TEXT="""
            Введите ID встречи (без лишних символов, только цифры) для просмотра полной информации по ней.""";
    public static final String MEETING_DELETE_TEXT = "Встреча удалена";
    public static final String SELECT_MEETING_EDIT_TEXT="""
            Введите ID встречи (без лишних символов, только цифры) для просмотра полной информации по ней.""";




}
