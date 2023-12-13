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
    public static final String SET_MEETING_PLACE_TEXT = EmojiParser.parseToUnicode("Введите место(адрес) проведения " +
            "события:" + "\n" + ":exclamation:" + "Вводить в формате: " + ":exclamation:" + "\n" +
            "*Город*, *Улица*, *Номер дома*" + "\n" + "ИЛИ" + "\n" + "*Город*, *место*, *доп информация по месту проведения*");
    public static final String SET_MEETING_EVENT_DATE_TEXT =
            EmojiParser.parseToUnicode("Введите дату проведения события:" + "\n" + ":exclamation:" +
                    "Вводить в формате: 31.04.2023 10:00" + ":exclamation:");
    public static final String SET_MEETING_USER_LIMIT_TEXT = "Введите максимальное количество участников события:";
    public static final String INCORRECT_DATE_TEXT = "Дата неверна! Введите ещё раз:";
    public static final String SET_MEETING_ANIMAL_TYPE_TEXT = "Введите тип животных события:";
    public static final String SET_MEETING_BREED_TEXT = "Введите пароду животных события:";
    public static final String REGISTER_MEETING_END_TEXT = "Создание события успешно завершено!";

    public static final String INCORRECT_MEETING_TITLE = "Неверный формат заголовка встречи. Пожалуйста, используйте " +
            "только буквы и смайлики. Длина заголовка до 100 символовю";
    public static final String INCORRECT_MEETING_DESCRIPTION = "Неверный формат / или превышена допустимая длина (1000 символов) " +
            "описания встречи. Пожалуйста, не используйте запрещенные слова!";
    public static final String INCORRECT_MEETING_PLACE = "Неверный формат места проведения встречи. Пожалуйста, " +
            "используйте только буквы и смайлики.";

    public static final String FORBIDDEN_WORDS = "идитенахуй|реклама|хуй|пиздец|подписка|подписывайтесь|ебать|уебок" +
            "|мусор|жоп|вошь|пиздюк|блядь|пиздюк|жоп|мудак|пидор|блядь|сука|говнюк|плешь|сука|говнюк|хуй|хряк|говнюк" +
            "|мусор|отсос|чмырь|отсос|хуй|хуй|блядь|жоп|говнюк|мусор|хряк|мандец|уебок|говнюк|хряк|хуй|пидор|" +
            "мудня|пизда|гомосиха|жопа|чмыриха|залупа|пидориха|пизда|манда|блядь|сука|мусорнюха|чмыриха|дерьмовая" +
            "|козлиная|блядовая|козлиная|сосливая|сосливая|залупастая|пидрильная|сосливая|сучавая|ебаная|сосливая" +
            "|мудяцкий|пиздатый|гомосявый|хуевый|перхотный|чмошный|мандявый|гомосявый|вшивый|мандявый|свинский|" +
            "сучавый|свинский|сосливый|говяный|говяный|чмошный|мандявый|хуею|рукаблуд|ссанина|блядун|вагина" +
            "|ебланище|влагалище|пердун|дрочила";

}
