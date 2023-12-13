package com.github.lostfly.corgihousetelegrambot.constants;

import com.vdurmont.emoji.EmojiParser;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static final String CORGI_PHOTO_TO_YOU = "КОРЖИКА ВАМ В ЛЕНТУ!";

    // Emojis
    public static final String BLUSH_EMOJI = ":blush:";
    public static final String STAR_EMOJI = ":star:";
    public static final String RELAXED_EMOJI = ":relaxed:";
    public static final String WOMAN_TECHNOLOGIST_EMOJI = ":woman_technologist:";
    public static final String MAN_TECHNOLOGIST_EMOJI = ":man_technologist:";

    // Static gen string funcs
    public static String generateStartMeetingMessage(String name) {
        return EmojiParser.parseToUnicode("Привет, " + name + ", рад тебя видеть!" + " " + BLUSH_EMOJI);
    }

    public static String generateAdminBroadcastMessage(String msg) {
        return EmojiParser.parseToUnicode(STAR_EMOJI + "Это сообщение является массовой рассылкой пользователям " +
                "бота от его администраторов. Если вы получили его, значит произошли важные изменения, " +
                "которые позволяют нам стать лучше." + RELAXED_EMOJI + "Расскажем подробнее: " + msg +
                " Если у вас есть пожелания или замечания по работе бота - обращайтесь к администраторам" +
                WOMAN_TECHNOLOGIST_EMOJI + MAN_TECHNOLOGIST_EMOJI + ": @eveprova & @lostfly");
    }

    // global session context

    public static final String GLOBAL_CONTEXT_PET_DELETE = "global_context_pet_delete";
    public static final String GLOBAL_CONTEXT_DEFAULT = "default";
    public static final String GLOBAL_CONTEXT_USER_REGISTRATION = "user_registration";
    public static final String GLOBAL_CONTEXT_PET_REGISTRATION = "pet_registration";
    public static final String GLOBAL_CONTEXT_USER_EDIT = "user_edit";
    public static final String GLOBAL_CONTEXT_MEETING_REGISTRATION = "meeting_registration";

    public static final String GLOBAL_ADMIN_BROADCAST = "global_admin_broadcast";

    public static final String GLOBAL_CONTEXT_FULL_MEETING_INFO = "global_context_full_meeting_info";
    public static final String GLOBAL_CONTEXT_MEETING_DELETE = "global_context_meeting_delete";
    public static final String GLOBAL_CONTEXT_MEETING_EDIT = "global_context_meeting_edit";

    public static final String GLOBAL_CONTEXT_SET_NAME_FIELD_MEETING = "global_context_set_name_field_meeting";

    public static final String GLOBAL_CONTEXT_MEETING_SELECT_NUMBER_MEETING = "global_context_meeting_select_number_meeting";

    // global files/dirs names
    public static final String PHOTO_STORAGE_DIR = "downloaded_photos";
    public static final String CORGI_STORAGE_DIR = "corgi_photos";
    public static final String PET_IMG_FILE_NAME = "pet_image_";
    public static final String PET_IMG_FILE_EXTENSION = ".jpg";
    public static final String TG_DOWNLOAD_FILE_LINK = "https://api.telegram.org/file/bot";


    // admins ID list && commands
    public static final ArrayList<Long> adminsIdList = new ArrayList<>(Arrays.asList(919433897L, 881861312L));

    public static final String ADMIN_INPUT_BROADCAST_MESSAGE = "Введите текст объявления (Внимание: " +
            "его увидят все пользователи бота!): ";
    public static final String NOT_AN_ADMIN = "У вас недостаточно прав на выполнение комманды!";


}
