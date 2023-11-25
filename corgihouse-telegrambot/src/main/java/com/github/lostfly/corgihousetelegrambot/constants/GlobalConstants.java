package com.github.lostfly.corgihousetelegrambot.constants;

import com.vdurmont.emoji.EmojiParser;

public class GlobalConstants {

    // GLOBAL_CONSTANTS
    // global texts

    public static final String INDEV_TEXT = "Пока я в разработке, но скоро смогу понять тебя!";

    public static final String HELP_TEXT = EmojiParser.parseToUnicode("""
            Привет! С помощью данного бота ты можешь:
            найти/создать и прийти на встречу любителей животных,
            зарегистрировать своих питомцев и получать\s
            персонализированные рекомендации для них.
            Как пользоваться?\s
            1) Для начала пройдите регистрацию -\s
            в левом меню нужно нажать Регистрация.
            После этого вам будут доступны остальные возможности.
            2) Для регистрации питомца, в правом меню нажмите Питомцы,\s
            далее вы сможете добавлять/удалять/редактировать\s
            своих любимчиков.\s
            3) Для поиска встреч в правом меню нажмите Поиск встреч\s
            вы можете выбрать тип и породу живтного для поиска\s
            или смотреть встречи касающиеся ваших питомцев.\s
            Просматривая доступные встречи, вы можете записаться на
            понравившиеся вам, нажам соответсвующую кнопку под сообщением.\s
            4) В разделе Мои встречи, вы можете:\s
            -Просмотреть встречи, на которые вы записались
            -Просмотреть созданные вами встречи и их изменить
            -Создать встречи

            Если остались вопросы, обратитесь к нашему администратору -\s
            TG: @eveprova""");

    public static final String NO_PETS_TEXT = "У вас пока что нет питомцев! Хотите зарегистрировать?";
    public static final String DELETE_PROFILE_QUESTION_TEXT = "Вы точно хотите удалить профиль?";

    public static final String BLUSH_EMOJI = ":blush:";

    public static String generateStartMeetingMessage(String name) {
        return EmojiParser.parseToUnicode("Привет, " + name + ", рад тебя видеть!" + " " + BLUSH_EMOJI);
    }


    // global session context

    public static final String GLOBAL_CONTEXT_PET_DELETE = "global_context_pet_delete";
    public static final String GLOBAL_CONTEXT_DEFAULT = "default";
    public static final String GLOBAL_CONTEXT_USER_REGISTRATION = "user_registration";
    public static final String GLOBAL_CONTEXT_PET_REGISTRATION = "pet_registration";
    public static final String GLOBAL_CONTEXT_USER_EDIT = "user_edit";
    public static final String GLOBAL_CONTEXT_MEETING_REGISTRATION = "meeting_registration";

    // global files/dirs names
    public static final String PHOTO_STORAGE_DIR = "downloaded_photos";

    // logs texts
    public static final String ERROR_OCCURRED = "Error occurred: ";


}
