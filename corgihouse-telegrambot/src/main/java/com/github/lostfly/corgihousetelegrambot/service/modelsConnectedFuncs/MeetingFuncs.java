package com.github.lostfly.corgihousetelegrambot.service.modelsConnectedFuncs;

import com.github.lostfly.corgihousetelegrambot.keyboardMenus.KeyboardMenus;
import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import com.github.lostfly.corgihousetelegrambot.model.UserSession;
import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import com.github.lostfly.corgihousetelegrambot.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.sql.Timestamp;
import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.MeetingFuncsConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.PetFuncsConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.PetFuncsConstants.INCORRECT_PET_NUMBER_ANS;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.UserFuncsConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.ListMenusConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.ListMenusConstants.EDIT_PROFILE_PHONE_NUMBER;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.MeetingRegConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.UserRegConstants.*;


@Slf4j
@Component
public class MeetingFuncs {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ListMenus listMenus;

    @Autowired
    private KeyboardMenus keyboardMenus;

    @Autowired
    private UserFuncs userFuncs;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserToMeetingRepository userToMeetingRepository;
    @Autowired
    private UserRepository userRepository;

    public  ArrayList<Meeting> getMyCreatedMeetings(Long chatId){
        return meetingRepository.findAllByOwnerId(chatId);
    }

    public  ArrayList<Meeting> getMyAppliedMeetings(Long chatId){
        return meetingRepository.findMyAppliedMeetings(chatId);
    }

    public SendMessage showMyMeetings(Long chatId) {

        if (userRepository.findById(chatId).isEmpty()){
            return null;
        }

        ArrayList<Meeting> my_meetings_created = getMyCreatedMeetings(chatId);
        ArrayList<Meeting> my_meetings_applied = getMyAppliedMeetings(chatId);

        SendMessage message = new SendMessage();
        if (my_meetings_created.isEmpty() && my_meetings_applied.isEmpty()) {
            message.setText(NO_MEETINGS_TEXT);
            message.setReplyMarkup(listMenus.meetingKeyboard());
        }else{
            message.setText(SELECT_MEETINGS_TYPE_TEXT);
        }
        return message;

    }

    public SendMessage changeToMyMeetings(Long chatId) {
        if (userFuncs.checkExistingProfile(chatId) != null){
            return userFuncs.checkExistingProfile(chatId);
        }

        ArrayList<Meeting> my_meetings_created = getMyCreatedMeetings(chatId);
        ArrayList<Meeting> my_meetings_applied = getMyAppliedMeetings(chatId);
        SendMessage message = new SendMessage();

        if (my_meetings_created.isEmpty() && my_meetings_applied.isEmpty()){
            return null;
        }else {
            message.setText(CHANGE_TO_MY_MEETINGS_TEXT);
            message.setChatId(chatId);
            message.setReplyMarkup(keyboardMenus.myMeetingsKeyboard());
        }

        return message;
    }



    public SendMessage changeToMainMenu(long chatId) {
        SendMessage message = new SendMessage();
        message.setText(CHANGE_TO_MAIN_MENU);
        message.setChatId(chatId);
        message.setReplyMarkup(keyboardMenus.mainKeyboard());
        return message;
    }

    public String showMainMeetingInfo(Meeting meeting) {
        String created_meeting_item = "ID события: " + meeting.getMeetingId() + "\n" +
                "Название: " + meeting.getTitle() + "\n" +
                "Дата: " + meeting.getEventDate() + "\n" +
                "Тип животного: " + meeting.getAnimalType() + "\n" +
                "Порода: " + meeting.getBreed() + "\n\n";
        return created_meeting_item;
    }

    public String showFullMeetingInfo(Meeting meeting) {
        String created_meeting_item = "ID события: " + meeting.getMeetingId() + "\n" +
                "Название: " + meeting.getTitle() + "\n" +
                "Дата: " + meeting.getEventDate() + "\n" +
                "Место проведения: " + meeting.getPlace() + "\n" +
                "Тип животного: " + meeting.getAnimalType() + "\n" +
                "Порода: " + meeting.getBreed() + "\n" +
                "Описание: " + meeting.getDescription() +"\n" +
                "Число записавшихся: " + userToMeetingRepository.countByMeetingId(meeting.getMeetingId()) + "\n\n";


        return created_meeting_item;
    }


    public SendMessage showCreatedMeetings(long chatId) {
        ArrayList<Meeting> my_meetings_created = getMyCreatedMeetings(chatId);
        SendMessage message = new SendMessage();
        if(my_meetings_created.isEmpty()){
            message.setText(NO_CREATED_MEETINGS_TEXT);
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.meetingKeyboard());
        }else{
            StringBuilder created_meetings_list = new StringBuilder();

            for(Meeting meeting : my_meetings_created){
                created_meetings_list.append(showMainMeetingInfo(meeting));
            }

            message.setText(created_meetings_list.toString());
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.createdMeetingKeyboard());
        }
        return message;
    }

    public SendMessage showAppliedMeetings(long chatId) {
        ArrayList<Meeting> my_meetings_applied = getMyAppliedMeetings(chatId);

        SendMessage message = new SendMessage();
        if(my_meetings_applied.isEmpty()){
            message.setText(NO_APPLIED_MEETINGS_TEXT);
            message.setChatId(chatId);
        }else {
            StringBuilder applied_meetings_list = new StringBuilder();

            for (Meeting meeting : my_meetings_applied) {
                applied_meetings_list.append(showMainMeetingInfo(meeting));
            }

            message.setText(applied_meetings_list.toString());
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.appliedMeetingKeyboard());
        }

        return message;

    }


    public String fullInfoMeetingSelection(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_FULL_MEETING_INFO, chatId);
        return SELECT_FULL_MEETING_INFO_TEXT;
    }

    public String fullInfoMeetingByNumber(Long chatId, String getFromMsg) {
        Long meetingId;
        try {
            meetingId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (meetingRepository.findByMeetingIdAndOwnerId(meetingId,chatId)!= null) {
            String fullMeetingInfo=showFullMeetingInfo(meetingRepository.findByMeetingId(meetingId));
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return fullMeetingInfo;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_FULL_INFO_NUMBER_ANS;
        }
    }

    public String deleteMeetingSelection(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_DELETE, chatId);
        return SELECT_MEETING_DELETE_TEXT;
    }

    public String deleteMeeting(Long chatId, String getFromMsg) {
        Long meetingId;
        try {
            meetingId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (meetingRepository.findByMeetingIdAndOwnerId(meetingId,chatId) != null) {
            userToMeetingRepository.deleteAllByMeetingId(meetingId);
            meetingRepository.deleteAllByMeetingId(meetingId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return MEETING_DELETE_TEXT;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_FULL_INFO_NUMBER_ANS;
        }
    }


    public String editMeetingSelectionNumber(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_SELECT_NUMBER_MEETING, chatId);
        sessionRepository.setEditMeetingFunctionContextByChatId(SELECT_NAME_FIELD_MEETING_EDIT_CONTEXT, chatId);
        return SELECT_NUMBER_MEETING_EDIT_TEXT;
    }

    public String editMeetingNameField(Long chatId, String getFromMsg) {
        Long meetingId;
        try {
            meetingId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (meetingRepository.findByMeetingIdAndOwnerId(meetingId,chatId) != null) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_SET_NAME_FIELD_MEETING, chatId);
            sessionRepository.setNumberEditMeetingByChatId(meetingId,chatId);
            return SELECT_NAME_FIELD_MEETING_EDIT_TEXT;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_FULL_INFO_NUMBER_ANS;
        }
    }

    public String editMeeting(Long chatId, String editContext ) {
        if (sessionRepository.findByChatId(chatId).getNumberEditMeeting()==0L){
            return "Чет не записался номер редактируемой встречи в NumberEditMeetingByChatId";
        }
        switch(editContext){
            case EDIT_MEETING_BUTTON_ANIMALTYPE:
                sessionRepository.setEditMeetingFunctionContextByChatId(EDIT_MEETING_BUTTON_ANIMALTYPE, chatId);
                return SET_MEETING_ANIMAL_TYPE_TEXT;

            case EDIT_MEETING_BUTTON_BREED:
                sessionRepository.setEditMeetingFunctionContextByChatId(EDIT_MEETING_BUTTON_BREED, chatId);
                return SET_MEETING_BREED_TEXT;

            case EDIT_MEETING_BUTTON_DATE:
                sessionRepository.setEditMeetingFunctionContextByChatId(EDIT_MEETING_BUTTON_DATE, chatId);
                return SET_MEETING_EVENT_DATE_TEXT;

            case EDIT_MEETING_BUTTON_DESCRIPTION:
                sessionRepository.setEditMeetingFunctionContextByChatId(EDIT_MEETING_BUTTON_DESCRIPTION, chatId);

                return SET_MEETING_DESCRIPTION_TEXT;

            case EDIT_MEETING_BUTTON_PLACE:
                sessionRepository.setEditMeetingFunctionContextByChatId(EDIT_MEETING_BUTTON_PLACE, chatId);
                return SET_MEETING_PLACE_TEXT;

            case EDIT_MEETING_BUTTON_TITLE:
                sessionRepository.setEditMeetingFunctionContextByChatId(EDIT_MEETING_BUTTON_TITLE, chatId);
                return SET_MEETING_TITLE_TEXT;
            default:
                return INDEV_TEXT;
        }
    }

    public String editMeetingAction(Long chatId, String messageText) {
        Long meetingId=sessionRepository.findByChatId(chatId).getNumberEditMeeting();
        switch(sessionRepository.findByChatId(chatId).getEditMeetingFunctionContext()){
            case EDIT_MEETING_BUTTON_ANIMALTYPE:
                meetingRepository.setAnimalTypeByOwnerIdAndMeetingId(messageText, chatId,meetingId);
                sessionRepository.setEditMeetingFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setNumberEditMeetingByChatId(chatId,0L);
                return CHANGED_MEETING_ANIMAL_TYPE_TEXT;

            case EDIT_MEETING_BUTTON_BREED:
                meetingRepository.setBreedByOwnerIdAndMeetingId(messageText, chatId,meetingId);
                sessionRepository.setEditMeetingFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setNumberEditMeetingByChatId(chatId,0L);
                return CHANGED_MEETING_BREED_TEXT;

            case EDIT_MEETING_BUTTON_DATE:
                meetingRepository.setEventDateByOwnerIdAndMeetingId(Timestamp.valueOf(messageText), chatId,meetingId);
                sessionRepository.setEditMeetingFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setNumberEditMeetingByChatId(chatId,0L);
                return CHANGED_MEETING_DATA_TEXT;

            case EDIT_MEETING_BUTTON_DESCRIPTION:
                meetingRepository.setDescriptionByOwnerIdAndMeetingId(messageText, chatId,meetingId);
                sessionRepository.setEditMeetingFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setNumberEditMeetingByChatId(chatId,0L);
                return CHANGED_MEETING_DESCRIPTION_TEXT;

            case EDIT_MEETING_BUTTON_PLACE:
                meetingRepository.setPlaceByOwnerIdAndMeetingId(messageText, chatId,meetingId);
                sessionRepository.setEditMeetingFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setNumberEditMeetingByChatId(chatId,0L);
                return CHANGED_MEETING_PLACE_TEXT;

            case EDIT_MEETING_BUTTON_TITLE:
                meetingRepository.setTitleByOwnerIdAndMeetingId(messageText, chatId,meetingId);
                sessionRepository.setEditMeetingFunctionContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
                sessionRepository.setNumberEditMeetingByChatId(chatId,0L);
                return CHANGED_MEETING_TITTLE_TEXT;
            default:
                return INDEV_TEXT;

        }
    }

}


