package com.github.lostfly.corgihousetelegrambot.listMenus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.sql.Timestamp;
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
        List<InlineKeyboardButton> showInfoCreatedMeetingRow = new ArrayList<>();
        List<InlineKeyboardButton> createAndDeleteAndEditMeetingRow = new ArrayList<>();

        var showInfoCreatedMeetingButton = new InlineKeyboardButton();
        var createMeetingButton = new InlineKeyboardButton();
        var deleteMeetingButton = new InlineKeyboardButton();
        var editMeetingButton = new InlineKeyboardButton();

        showInfoCreatedMeetingButton.setText(CREATED_MEETINGS_FULL_INFO_TEXT);
        showInfoCreatedMeetingButton.setCallbackData(CREATED_MEETINGS_FULL_INFO_SELECT);

        createMeetingButton.setText(MEETING_ADD_TEXT);
        createMeetingButton.setCallbackData(MEETING_ADD);

        deleteMeetingButton.setText(MEETING_DELETE_BUTTON_TEXT);
        deleteMeetingButton.setCallbackData(MEETING_DELETE_BUTTON_SELECT);

        editMeetingButton.setText(MEETING_EDIT_BUTTON_TEXT);
        editMeetingButton.setCallbackData(MEETING_EDIT_BUTTON_SELECT);


        showInfoCreatedMeetingRow.add(showInfoCreatedMeetingButton);
        createAndDeleteAndEditMeetingRow.add(createMeetingButton);
        createAndDeleteAndEditMeetingRow.add(deleteMeetingButton);
        createAndDeleteAndEditMeetingRow.add(editMeetingButton);

        createdMeetingRows.add(showInfoCreatedMeetingRow);
        createdMeetingRows.add(createAndDeleteAndEditMeetingRow);
        createdMeetingKeyboard.setKeyboard(createdMeetingRows);

        return createdMeetingKeyboard;
    }
    public InlineKeyboardMarkup meetingEditKeyboard() {
        InlineKeyboardMarkup meetingEditKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> meetingEditRows = new ArrayList<>();
        List<InlineKeyboardButton> meetingEditTitleRow = new ArrayList<>();
        List<InlineKeyboardButton> meetingEditDataRow = new ArrayList<>();
        List<InlineKeyboardButton> meetingEditPlaceRow = new ArrayList<>();
        List<InlineKeyboardButton> meetingEditAnimalTypeRow = new ArrayList<>();
        List<InlineKeyboardButton> meetingEditBreedRow = new ArrayList<>();
        List<InlineKeyboardButton> meetingEditDescriptionRow = new ArrayList<>();


        var editMeetingButtonTitle = new InlineKeyboardButton();

        editMeetingButtonTitle.setText(EDIT_MEETING_BUTTON_TITLE_TEXT);
        editMeetingButtonTitle.setCallbackData(EDIT_MEETING_BUTTON_TITLE);

        meetingEditTitleRow.add(editMeetingButtonTitle);
        meetingEditRows.add(meetingEditTitleRow);

        var editMeetingButtonData = new InlineKeyboardButton();

        editMeetingButtonData.setText(EDIT_MEETING_BUTTON_DATE_TEXT);
        editMeetingButtonData.setCallbackData(EDIT_MEETING_BUTTON_DATE);

        meetingEditDataRow.add(editMeetingButtonData);
        meetingEditRows.add(meetingEditDataRow);

        var editMeetingButtonPlace = new InlineKeyboardButton();

        editMeetingButtonPlace.setText(EDIT_MEETING_BUTTON_PLACE_TEXT);
        editMeetingButtonPlace.setCallbackData(EDIT_MEETING_BUTTON_PLACE);

        meetingEditPlaceRow.add(editMeetingButtonPlace);
        meetingEditRows.add(meetingEditPlaceRow);

        var editMeetingButtonAnimalType = new InlineKeyboardButton();

        editMeetingButtonAnimalType.setText(EDIT_MEETING_BUTTON_ANIMALTYPE_TEXT);
        editMeetingButtonAnimalType.setCallbackData(EDIT_MEETING_BUTTON_ANIMALTYPE);

        meetingEditAnimalTypeRow.add(editMeetingButtonAnimalType);
        meetingEditRows.add(meetingEditAnimalTypeRow);

        var editMeetingButtonBreed = new InlineKeyboardButton();
        editMeetingButtonBreed.setText(EDIT_MEETING_BUTTON_BREED_TEXT);
        editMeetingButtonBreed.setCallbackData(EDIT_MEETING_BUTTON_BREED);

        meetingEditBreedRow.add(editMeetingButtonBreed);
        meetingEditRows.add(meetingEditBreedRow);


        var editMeetingButtonDescription = new InlineKeyboardButton();
        editMeetingButtonDescription.setText(EDIT_MEETING_BUTTON_DESCRIPTION_TEXT);
        editMeetingButtonDescription.setCallbackData(EDIT_MEETING_BUTTON_DESCRIPTION);

        meetingEditDescriptionRow.add(editMeetingButtonDescription);
        meetingEditRows.add(meetingEditDescriptionRow);

        meetingEditKeyboard.setKeyboard(meetingEditRows);

        return meetingEditKeyboard;
    }

    private String animalType;

    private String breed;

    private String title;

    private String description;

    private String place;

    private Boolean completed;

    private Boolean fullFilled;

    private Integer userLimit;

    private Long ownerId;

    private Timestamp eventDate;
    public ReplyKeyboard appliedMeetingKeyboard() {
        InlineKeyboardMarkup appliedMeetingKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> appliedMeetingRows = new ArrayList<>();
        List<InlineKeyboardButton> appliedMeetingRow = new ArrayList<>();


        var showInfoAppliedMeetingButton = new InlineKeyboardButton();

        showInfoAppliedMeetingButton.setText(APPLIED_MEETINGS_FULL_INFO_TEXT);
        showInfoAppliedMeetingButton.setCallbackData(APPLIED_MEETINGS_FULL_INFO_SELECT);



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
