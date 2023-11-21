package com.github.lostfly.corgihousetelegrambot.constants;

import com.vdurmont.emoji.EmojiParser;

public class GlobalConstants {

    // GLOBAL
    // Global texts

    public static final String INDEV_TEXT = "Пока я в разработке, но скоро смогу понять тебя!";

    public static final String HELP_TEXT = "Привет! С помощью данного бота ты можешь:" + "\n" +
            "найти/создать и прийти на встречу любителей животных," + "\n" +
            "зарегистрировать своих питомцев и получать " + "\n" +
            "персонализированные рекомендации для них." + "\n" +
            "Как пользоваться? " + "\n" +
            "1) Для начала пройдите регистрацию - " + "\n" +
            "в левом меню нужно нажать Регистрация." + "\n" +
            "После этого вам будут доступны остальные возможности." + "\n" +
            "2) Для регистрации питомца, в правом меню нажмите Питомцы, " + "\n" +
            "далее вы сможете добавлять/удалять/редактировать " + "\n" +
            "своих любимчиков. " + "\n" +
            "3) Для поиска встреч в правом меню нажмите Поиск встреч " + "\n" +
            "вы можете выбрать тип и породу живтного для поиска " + "\n" +
            "или смотреть встречи касающиеся ваших питомцев. " + "\n" +
            "Просматривая доступные встречи, вы можете записаться на" + "\n" +
            "понравившиеся вам, нажам соответсвующую кнопку под сообщением. " + "\n" +
            "4) В разделе Мои встречи, вы можете: " + "\n" +
            "-Просмотреть встречи, на которые вы записались" + "\n" +
            "-Просмотреть созданные вами встречи и их изменить" + "\n" +
            "-Создать встречи" + "\n\n" +
            "Если остались вопросы, обратитесь к нашему администратору - " + "\n" +
            "TG: @eveprova";

    public static final String NO_PETS_TEXT = "У вас пока что нет питомцев! Хотите зарегистрировать?";
    public static final String DELETE_PROFILE_QUESTION_TEXT = "Вы точно хотите удалить профиль?";

    public static final String BLUSH_EMOJI = ":blush:";

    public static final String generateStartMeetingMessage(String name) {
        return EmojiParser.parseToUnicode("Привет, " + name + ", рад тебя видеть!" + " " + BLUSH_EMOJI);
    }


    // Global session context

    public static final String GLOBAL_CONTEXT_PET_DELETE = "global_context_pet_delete";
    public static final String GLOBAL_CONTEXT_DEFAULT = "default";
    public static final String GLOBAL_CONTEXT_USER_REGISTRATION = "user_registration";
    public static final String GLOBAL_CONTEXT_PET_REGISTRATION = "pet_registration";
    public static final String GLOBAL_CONTEXT_USER_EDIT = "user_edit";
    public static final String GLOBAL_CONTEXT_MEETING_REGISTRATION = "meeting_registration";

    // Global files/dirs names
    public static final String PHOTO_STORAGE_DIR = "downloaded_photos";

    // Logs texts
    public static final String ERROR_OCCURRED = "Error occurred: ";


}
