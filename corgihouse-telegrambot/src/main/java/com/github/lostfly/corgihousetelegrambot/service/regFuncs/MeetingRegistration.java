package com.github.lostfly.corgihousetelegrambot.service.regFuncs;

import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import com.github.lostfly.corgihousetelegrambot.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.PetFuncsConstants.INCORRECT_NUMBER_ANS;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.MeetingRegConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.INCORRECT_PET_BREED;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.INCORRECT_PET_TYPE;

@Slf4j
@Component
public class MeetingRegistration {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserToMeetingRepository userToMeetingRepository;
    @Autowired
    private SessionRepository sessionRepository;

    public String initializeRegistration(Update update) {

        var chatId = update.getCallbackQuery().getMessage().getChatId();

        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_REGISTRATION, chatId);

        Meeting meeting = new Meeting();
        UserToMeeting userToMeeting = new UserToMeeting();

        userToMeeting.setChatId(chatId);


        meeting.setOwnerId(chatId);
        meeting.setFullFilled(false);
        meeting.setCompleted(false);

        if (meetingRepository.findTopByOrderByMeetingIdDesc() != null) {
            Long meetingId = meetingRepository.findTopByOrderByMeetingIdDesc();
            meeting.setMeetingId(meetingId + 1);
            userToMeeting.setMeetingId(meetingId + 1);
            sessionRepository.setMeetingRegisterFunctionId(meetingId + 1, chatId);
        } else {
            Long meetingId = 1L;
            meeting.setMeetingId(meetingId);
            userToMeeting.setMeetingId(meetingId);
            sessionRepository.setMeetingRegisterFunctionId(meetingId, chatId);
        }

        if (userToMeetingRepository.findTopByOrderByIdDesc() != null) {
            Long Id = userToMeetingRepository.findTopByOrderByIdDesc();
            userToMeeting.setId(Id);
        } else {
            Long Id = 1L;
            userToMeeting.setId(Id);
        }

        meetingRepository.save(meeting);
        userToMeetingRepository.save(userToMeeting);

        log.info("Meeting saved: " + meeting);
        sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_TITLE, chatId);
        return NewMeetingCommandReceived(chatId);

    }

    private static String NewMeetingCommandReceived(long chatId) {
        log.info("Register meeting " + " " + chatId);
        return SET_MEETING_TITLE_TEXT;
    }


    public String continueRegistration(Update update) {

        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        return switch (sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionContext()) {
            case (SET_MEETING_TITLE) -> SetMeetingTitle(chatId, messageText);
            case (SET_MEETING_DESCRIPTION) -> SetMeetingDescription(chatId, messageText);
            case (SET_MEETING_PLACE) -> SetMeetingPlace(chatId, messageText);
            case (SET_MEETING_EVENT_DATE) -> SetMeetingEventDate(chatId, messageText);
            case (SET_MEETING_USER_LIMIT) -> SetMeetingUserLimit(chatId, messageText);
            case (SET_MEETING_ANIMAL_TYPE) -> SetMeetingAnimalType(chatId, messageText);
            case (SET_MEETING_BREED) -> SetMeetingBreed(chatId, messageText);
            default -> INDEV_TEXT;
        };
    }

    private boolean isValidTitle(String name) {
        String nameRegex = "^[^\\d$%^&*_=~`\"></|\\\\]{1,100}$";
        return name.matches(nameRegex);
    }

    private boolean isValidPlace(String name) {
        String nameRegex = "^[^\\d$%^&*_=~`\"></|\\\\]{1,100}$";
        return name.matches(nameRegex);
    }

    private boolean isValidDescription(String name) {
        String nameRegex = "^[^\\d$%^&*></|\\\\]{1,1000}$";
        return name.matches(nameRegex);
    }

    private boolean isValidName(String name) {
        String nameRegex = "^[^\\d!@#$%^&*()_+=~`:;\"><,./|\\\\]{1,40}$";
        return name.matches(nameRegex);
    }

    private String SetMeetingTitle(long chatId, String messageText) {
        if (isValidTitle(messageText)) {
            meetingRepository.setTitleByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_DESCRIPTION, chatId);
            return (SET_MEETING_DESCRIPTION_TEXT);
        } else {
            return INCORRECT_MEETING_TITLE;
        }
    }

    private String SetMeetingDescription(long chatId, String messageText) {
        if (isValidDescription(messageText)) {
            meetingRepository.setDescriptionByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_PLACE, chatId);
            return (SET_MEETING_PLACE_TEXT);
        }else {
            return INCORRECT_MEETING_DESCRIPTION;
        }
    }

    private String SetMeetingPlace(long chatId, String messageText) {
        if (isValidPlace(messageText)) {
            meetingRepository.setPlaceByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_EVENT_DATE, chatId);
            return (SET_MEETING_EVENT_DATE_TEXT);
        }else {
            return INCORRECT_MEETING_PLACE;
        }
    }

    private String SetMeetingEventDate(long chatId, String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            Date parsedDate = dateFormat.parse(dateString);
            Timestamp eventDate = new Timestamp(parsedDate.getTime());
            meetingRepository.setEventDateByOwnerIdAndMeetingId(eventDate, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_USER_LIMIT, chatId);
            return (SET_MEETING_USER_LIMIT_TEXT);
        } catch (ParseException e) {
            e.printStackTrace();
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_REGISTRATION, chatId);
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_EVENT_DATE, chatId);
            return (INCORRECT_DATE_TEXT);
        }
    }

    private String SetMeetingUserLimit(long chatId, String messageText) {
        int userLimit;
        try {
            userLimit = Integer.parseInt(messageText);
        } catch (NumberFormatException e) {
            return INCORRECT_NUMBER_ANS;
        }
        meetingRepository.setUserLimitByOwnerIdAndMeetingId(userLimit, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
        sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_ANIMAL_TYPE, chatId);
        return (SET_MEETING_ANIMAL_TYPE_TEXT);
    }

    private String SetMeetingAnimalType(long chatId, String messageText) {
        if (isValidName(messageText)) {
            meetingRepository.setAnimalTypeByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_BREED, chatId);
            return (SET_MEETING_BREED_TEXT);
        }else {
            return INCORRECT_PET_TYPE;
        }
    }

    private String SetMeetingBreed(long chatId, String messageText) {
        if (isValidName(messageText)) {
            meetingRepository.setBreedByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            sessionRepository.setMeetingRegisterFunctionId(0L, chatId);
            return (REGISTER_MEETING_END_TEXT);
        }else {
            return INCORRECT_PET_BREED;
        }
    }


}
