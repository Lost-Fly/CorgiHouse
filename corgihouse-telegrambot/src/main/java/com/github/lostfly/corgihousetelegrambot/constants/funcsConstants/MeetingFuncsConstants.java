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
    public static final String CHANGE_TO_MY_MEETINGS_TEXT = EmojiParser.parseToUnicode("Раздел просмотра событий");
    public static final String CHANGE_TO_MAIN_MENU = EmojiParser.parseToUnicode("Главное меню");
    public static final String SELECT_FULL_MEETING_INFO_TEXT = """
            Введите ID встречи (без лишних символов, только цифры) для просмотра полной информации по ней.""";

    public static final String INCORRECT_FULL_INFO_NUMBER_ANS = "Ты дурак! Вам в первый класс.";
    public static final String SELECT_MEETING_DELETE_TEXT = """
            Введите ID встречи (без лишних символов, только цифры) для просмотра полной информации по ней.""";
    public static final String MEETING_DELETE_TEXT = "Встреча удалена";
    public static final String SELECT_NUMBER_MEETING_EDIT_TEXT = """
            Введите ID встречи (без лишних символов, только цифры) для просмотра полной информации по ней.""";
    public static final String SELECT_NUMBER_MEETING_EDIT_CONTEXT = "select_number_meeting_edit_context";
    public static final String SELECT_NAME_FIELD_MEETING_EDIT_CONTEXT = "select_name_field_meeting_edit_context";

    public static final String SELECT_NAME_FIELD_MEETING_EDIT_TEXT = """
            Выберите поле для редактирования.""";

    public static final String SET_NAME_FIELD_MEETING = "set_name_field_meeting";


    public static final String CHANGED_MEETING_TITTLE_TEXT = "Название изменено";
    public static final String CHANGED_MEETING_BREED_TEXT = "Кличка изменена";
    public static final String CHANGED_MEETING_DATA_TEXT = "Дата изменена";
    public static final String CHANGED_MEETING_DESCRIPTION_TEXT = "Описание изменено";
    public static final String CHANGED_MEETING_ANIMAL_TYPE_TEXT = "Тип животного изменен";
    public static final String CHANGED_MEETING_PLACE_TEXT = "Место проведения изменено";

    public static final String CHANGED_MEETING_LIMIT_TEXT = "Максимальное количество участников изменено";
    public static final String INCORRECT_MEETING_ANIMAL_TYPE = "Некорректно введен тип животного";
    public static final String INCORRECT_MEETING_BREED = "Некорректно введена кличка животного";
    public static final String INCORRECT_MEETING_DATA = "Некорректно введена дата события";
    public static final String INCORRECT_MEETING_TITTLE = "Некорректно введено название события";

    public static final String MEETING_ID_FOR_MSG = "ID события: ";
    public static final String MEETING_NAME_FOR_MSG = "Название: ";
    public static final String MEETING_DATE_FOR_MSG = "Дата: ";
    public static final String MEETING_PET_TYPE_FOR_MSG = "Тип животного: ";
    public static final String MEETING_PET_BREED_FOR_MSG = "Порода: ";
    public static final String MEETING_DESCRIPTION_FOR_MSG = "Описание: ";
    public static final String MEETING_PLACE_FOR_MSG = "Место проведения: ";
    public static final String MEETING_MAX_PARTICIPANTS_AMOUNT_FOR_MSG = "Максимальное кол-во участников: ";
    public static final String MEETING_FULFILLED_FOR_MSG = "Заполнено: ";
    public static final String MEETING_HAS_ENDED_FOR_MSG = "Прошло: ";
    public static final String MEETING_PARTICIPANTS_APPLIED_AMOUNT_FOR_MSG = "Число записавшихся: ";
    public static final String YES_FOR_MSG = "Да";
    public static final String NO_FOR_MSG = "Нет";


}
