package com.github.lostfly.corgihousetelegrambot.listMenus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ListMenus {

    public static final String EDIT_PROFILE = "edit_profile";
    public static final String EDIT_PROFILE_BUTTON_TEXT = "Редактировать профиль";
    public static final String DELETE_PROFILE_QUESTION = "delete_profile_question";
    public static final String DELETE_PROFILE_BUTTON_TEXT = "Удалить профиль";

    public InlineKeyboardMarkup profileButtonKeyboard() {
        InlineKeyboardMarkup profileButtonKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> profileEditRows = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow1 = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow2 = new ArrayList<>();

        var editPofileButton = new InlineKeyboardButton();

        editPofileButton.setText(EDIT_PROFILE_BUTTON_TEXT);
        editPofileButton.setCallbackData(EDIT_PROFILE);

        var deletePofileButton = new InlineKeyboardButton();

        deletePofileButton.setText(DELETE_PROFILE_BUTTON_TEXT);
        deletePofileButton.setCallbackData(DELETE_PROFILE_QUESTION);

        profileEditRow1.add(editPofileButton);
        profileEditRow2.add(deletePofileButton);
        profileEditRows.add(profileEditRow1);
        profileEditRows.add(profileEditRow2);
        profileButtonKeyboard.setKeyboard(profileEditRows);

        return  profileButtonKeyboard;
    }

    public static final String DELETE_PROFILE_TEXT = "Вы точно хотите удалить профиль?";
    public static final String DELETE_PROFILE_CONFIRM = "delete_profile_confirm";
    public static final String DELETE_PROFILE_DENY = "delete_profile_deny";
    public static final String DELETE_PROFILE_CONFIRM_TEXT = "Да";
    public static final String DELETE_PROFILE_DENY_TEXT = "Нет";

    public InlineKeyboardMarkup profileDeleteKeyboard() {
        InlineKeyboardMarkup profileButtonKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> profileDelRows = new ArrayList<>();
        List<InlineKeyboardButton> profileDelRow1 = new ArrayList<>();

        var editPofileButton = new InlineKeyboardButton();

        editPofileButton.setText(DELETE_PROFILE_CONFIRM_TEXT);
        editPofileButton.setCallbackData(DELETE_PROFILE_CONFIRM);

        var deletePofileButton = new InlineKeyboardButton();

        deletePofileButton.setText(DELETE_PROFILE_DENY_TEXT);
        deletePofileButton.setCallbackData(DELETE_PROFILE_DENY);

        profileDelRow1.add(editPofileButton);
        profileDelRow1.add(deletePofileButton);
        profileDelRows.add(profileDelRow1);
        profileButtonKeyboard.setKeyboard(profileDelRows);

        return  profileButtonKeyboard;
    }


}
