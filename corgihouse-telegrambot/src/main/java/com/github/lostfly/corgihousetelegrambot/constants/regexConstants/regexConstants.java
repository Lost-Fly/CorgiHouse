package com.github.lostfly.corgihousetelegrambot.constants.regexConstants;

public class regexConstants {
    // REGULAR EXPRESSIONS CONSTANTS
    // regex

    public static final String PHONE_REGEX = "^(\\+7|7|8)?[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{3}[\\s\\-]?[0-9]{2}[\\s\\-]?[0-9]{2}$";

    public static final String NAME_REGEX = "^[^0-9~`'!@#^&*()+=]{1,40}$";

    public static final String PLACE_REGEX = "^[a-zA-Zа-яА-Я\\s]+,[a-zA-Zа-яА-Я\\s]+,[\\da-zA-Zа-яА-Я\\s]+$";

    public static final String DATE_FORMAT_STYLE = "dd.MM.yyyy HH:mm";

    public static final String FORBIDDEN_WORDS = "\\Q|идитенахуй|реклама|хуй|пиздец|подписка|подписывайтесь|ебать|уебок" +
            "|мусор|вошь|пиздюк|блядь|пиздюк|мудак|пидор|блядь|говнюк|плешь|сука|говнюк|хуй" +
            "|отсос|чмырь|отсос|хуй|блядь|говнюк|мандец|уебок|говнюк|хряк|хуй|пидор|" +
            "мудня|пизда|гомосиха|чмыриха|залупа|пидориха|пизда|манда|блядь|сука|мусорнюха|чмыриха|дерьмовая" +
            "|козлиная|блядовая|козлиная|сосливая|сосливая|залупастая|пидрильная|сосливая|сучавая|ебаная|сосливая" +
            "|мудяцкий|пиздатый|гомосявый|хуевый|перхотный|чмошный|мандявый|гомосявый|вшивый|мандявый|свинский|" +
            "сучавый|свинский|сосливый|говяный|говяный|чмошный|мандявый|хуею|рукаблуд|ссанина|сука|блядун|вагина" +
            "|блять|ебланище|влагалище|очкун|пердун|дрочила\\E";

    public static final String FORBIDDEN_WORDS_REGEX = String.format("(?i)\\b(?:%s)\\b", FORBIDDEN_WORDS);



}
