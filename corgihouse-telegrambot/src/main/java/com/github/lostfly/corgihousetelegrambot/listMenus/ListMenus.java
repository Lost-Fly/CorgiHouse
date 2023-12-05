package com.github.lostfly.corgihousetelegrambot.listMenus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.ListMenusConstants.*;

@Slf4j
@Component
public class ListMenus {

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

        return profileButtonKeyboard;
    }

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

        return profileButtonKeyboard;
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

        return profileButtonKeyboard;
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

        return petQuestionKeyboard;
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

        return petAddKeyboard;
    }

    public InlineKeyboardMarkup meetingKeyboard() {
        InlineKeyboardMarkup meetingCreateKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> meetingCreateRows = new ArrayList<>();
        List<InlineKeyboardButton> meetingCreateRow = new ArrayList<>();

        var meetingCreateButton = new InlineKeyboardButton();

        meetingCreateButton.setText(MEETING_ADD_TEXT);
        meetingCreateButton.setCallbackData(MEETING_ADD);

        meetingCreateRow.add(meetingCreateButton);
        meetingCreateRows.add(meetingCreateRow);
        meetingCreateKeyboard.setKeyboard(meetingCreateRows);

        return meetingCreateKeyboard;
    }

    public ReplyKeyboard createdMeetingKeyboard() {
        InlineKeyboardMarkup createdMeetingKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> createdMeetingRows = new ArrayList<>();
        List<InlineKeyboardButton> createdMeetingRow = new ArrayList<>();

        var showInfoCreatedMeetingButton = new InlineKeyboardButton();

        showInfoCreatedMeetingButton.setText(CREATED_MEETINGS_FULL_INFO_TEXT);
        showInfoCreatedMeetingButton.setCallbackData(CREATED_MEETINGS_FULL_INFO);

        createdMeetingRow.add(showInfoCreatedMeetingButton);
        createdMeetingRows.add(createdMeetingRow);
        createdMeetingKeyboard.setKeyboard(createdMeetingRows);

        return createdMeetingKeyboard;
    }

    public ReplyKeyboard appliedMeetingKeyboard() {
        InlineKeyboardMarkup appliedMeetingKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> appliedMeetingRows = new ArrayList<>();
        List<InlineKeyboardButton> appliedMeetingRow = new ArrayList<>();

        var showInfoAppliedMeetingButton = new InlineKeyboardButton();

        showInfoAppliedMeetingButton.setText(APPLIED_MEETINGS_FULL_INFO_TEXT);
        showInfoAppliedMeetingButton.setCallbackData(APPLIED_MEETINGS_FULL_INFO);

        appliedMeetingRow.add(showInfoAppliedMeetingButton);
        appliedMeetingRows.add(appliedMeetingRow);
        appliedMeetingKeyboard.setKeyboard(appliedMeetingRows);

        return appliedMeetingKeyboard;
    }

    public ReplyKeyboard searchMeetingKeyboard() {
        InlineKeyboardMarkup searchMeetingKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> searchMeetingRows = new ArrayList<>();
        List<InlineKeyboardButton> searchMeetingRow = new ArrayList<>();

        var likeMeetingButton = new InlineKeyboardButton();
        var dislikeMeetingButton = new InlineKeyboardButton();
        var pinnedMeetingButton = new InlineKeyboardButton();

        likeMeetingButton.setText(LIKE_MEETING_TEXT);
        likeMeetingButton.setCallbackData(LIKE_MEETING);

        dislikeMeetingButton.setText(DISLIKE_MEETING_TEXT);
        dislikeMeetingButton.setCallbackData(DISLIKE_MEETING);

        pinnedMeetingButton.setText(PIN_MEETING_TEXT);
        pinnedMeetingButton.setCallbackData(PIN_MEETING);

        searchMeetingRow.add(likeMeetingButton);
        searchMeetingRow.add(dislikeMeetingButton);
//        searchMeetingRow.add(pinnedMeetingButton); #Это для кнопки отложить. Можно включить, она частично работает
        searchMeetingRows.add(searchMeetingRow);
        searchMeetingKeyboard.setKeyboard(searchMeetingRows);

        return searchMeetingKeyboard;
    }


    public InlineKeyboardMarkup registrationKeyboard() {
        InlineKeyboardMarkup registrationKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> registrationRows = new ArrayList<>();
        List<InlineKeyboardButton> registrationRow = new ArrayList<>();

        var registrationButton = new InlineKeyboardButton();

        registrationButton.setText(REGISTRATION_TEXT);
        registrationButton.setCallbackData(REGISTRATION);

        registrationRow.add(registrationButton);
        registrationRows.add(registrationRow);
        registrationKeyboard.setKeyboard(registrationRows);

        return registrationKeyboard;
    }

}
