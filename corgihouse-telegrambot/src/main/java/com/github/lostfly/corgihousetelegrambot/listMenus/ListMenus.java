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
    public static final String EDIT_PROFILE_NAME_TEXT = "Имя";
    public static final String EDIT_PROFILE_NAME = "edit_profile_name";
    public static final String EDIT_PROFILE_LASTNAME_TEXT = "Фамилия";
    public static final String EDIT_PROFILE_LASTNAME = "edit_profile_lastname";
    public static final String EDIT_PROFILE_PHONE_NUMBER_TEXT = "Телефон";
    public static final String EDIT_PROFILE_PHONE_NUMBER = "edit_profile_phone_number";
    public static final String PET_QUESTION_CONFIRM_BUTTON_TEXT = "Да" ;
    public static final String PET_QUESTION_CONFIRM = "pet_question_confirm";
    public static final String PET_QUESTION_DENY_TEXT = "Нет";
    public static final String PET_QUESTION_DENY = "pet_question_deny";
    public static final String PET_ADD_TEXT = "Добавить";
    public static final String PET_ADD = "pet_add";
    public static final String PET_DELETE_SELECTION_TEXT = "Удалить";
    public static final String PET_DELETE_SELECTION = "delete_add";

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

    public static final String DELETE_PROFILE_QUESTION_TEXT = "Вы точно хотите удалить профиль?";
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

    public InlineKeyboardMarkup profileEditKeyboard() {
        InlineKeyboardMarkup profileButtonKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> profileEditRows = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow1 = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow2 = new ArrayList<>();
        List<InlineKeyboardButton> profileEditRow3 = new ArrayList<>();

        var editProfileButtonName = new InlineKeyboardButton();

        editProfileButtonName.setText(EDIT_PROFILE_NAME_TEXT);
        editProfileButtonName.setCallbackData(EDIT_PROFILE_NAME);

        profileEditRow1.add(editProfileButtonName);
        profileEditRows.add(profileEditRow1);

        var editProfileButtonLastName = new InlineKeyboardButton();

        editProfileButtonLastName.setText(EDIT_PROFILE_LASTNAME_TEXT);
        editProfileButtonLastName.setCallbackData(EDIT_PROFILE_LASTNAME);

        profileEditRow2.add(editProfileButtonLastName);
        profileEditRows.add(profileEditRow2);

        var editProfileButtonPhoneNumber = new InlineKeyboardButton();

        editProfileButtonPhoneNumber.setText(EDIT_PROFILE_PHONE_NUMBER_TEXT);
        editProfileButtonPhoneNumber.setCallbackData(EDIT_PROFILE_PHONE_NUMBER);

        profileEditRow3.add(editProfileButtonPhoneNumber);
        profileEditRows.add(profileEditRow3);


        profileButtonKeyboard.setKeyboard(profileEditRows);

        return  profileButtonKeyboard;
    }

    public InlineKeyboardMarkup petQuestionButtonKeyboard() {
        InlineKeyboardMarkup petQuestionKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> petQuestionRows = new ArrayList<>();
        List<InlineKeyboardButton> petQuestionRow1 = new ArrayList<>();


        var petQuestionButtonConfirm = new InlineKeyboardButton();

        petQuestionButtonConfirm.setText(PET_QUESTION_CONFIRM_BUTTON_TEXT);
        petQuestionButtonConfirm.setCallbackData(PET_QUESTION_CONFIRM);

        var petQuestionButtonDeny = new InlineKeyboardButton();

        petQuestionButtonDeny.setText(PET_QUESTION_DENY_TEXT);
        petQuestionButtonDeny.setCallbackData(PET_QUESTION_DENY);

        petQuestionRow1.add(petQuestionButtonConfirm);
        petQuestionRow1.add(petQuestionButtonDeny);
        petQuestionRows.add(petQuestionRow1);
        petQuestionKeyboard.setKeyboard(petQuestionRows);

        return  petQuestionKeyboard;
    }

    public InlineKeyboardMarkup petAddButtonKeyboard() {
        InlineKeyboardMarkup petAddKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> petAddRows = new ArrayList<>();
        List<InlineKeyboardButton> petAddRow1 = new ArrayList<>();


        var petAddButton = new InlineKeyboardButton();

        petAddButton.setText(PET_ADD_TEXT);
        petAddButton.setCallbackData(PET_ADD);

        var petDeleteButton = new InlineKeyboardButton();

        petDeleteButton.setText(PET_DELETE_SELECTION_TEXT);
        petDeleteButton.setCallbackData(PET_DELETE_SELECTION);


        petAddRow1.add(petDeleteButton);
        petAddRow1.add(petAddButton);
        petAddRows.add(petAddRow1);
        petAddKeyboard.setKeyboard(petAddRows);

        return  petAddKeyboard;
    }

}
