package com.github.lostfly.corgihousetelegrambot.service.regFuncs;

import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import com.github.lostfly.corgihousetelegrambot.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.PetFuncsConstants.INCORRECT_NUMBER_ANS;
import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.NEW_MEETING_CREATED_LOG;
import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.NEW_MEETING_SAVED_LOG;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.MeetingRegConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.INCORRECT_PET_BREED;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.INCORRECT_PET_TYPE;
import static com.github.lostfly.corgihousetelegrambot.constants.regexConstants.regexConstants.*;

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
            userToMeeting.setId(Id + 1L);
        } else {
            Long Id = 1L;
            userToMeeting.setId(Id);
        }

        meetingRepository.save(meeting);
        userToMeetingRepository.save(userToMeeting);

        log.info(NEW_MEETING_SAVED_LOG + meeting);
        sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_TITLE, chatId);
        return NewMeetingCommandReceived(chatId);

    }

    private static String NewMeetingCommandReceived(long chatId) {
        log.info(NEW_MEETING_CREATED_LOG + chatId);
        return SET_MEETING_TITLE_TEXT;
    }


    public String continueRegistration(Update update) {

        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        return switch (sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionContext()) {
            case (SET_MEETING_TITLE) -> setMeetingTitle(chatId, messageText);
            case (SET_MEETING_DESCRIPTION) -> setMeetingDescription(chatId, messageText);
            case (SET_MEETING_PLACE) -> setMeetingPlace(chatId, messageText);
            case (SET_MEETING_EVENT_DATE) -> setMeetingEventDate(chatId, messageText);
            case (SET_MEETING_USER_LIMIT) -> setMeetingUserLimit(chatId, messageText);
            case (SET_MEETING_ANIMAL_TYPE) -> setMeetingAnimalType(chatId, messageText);
            case (SET_MEETING_BREED) -> setMeetingBreed(chatId, messageText);
            default -> INDEV_TEXT;
        };
    }

    public static boolean containsForbiddenWords(String text) {
        Pattern pattern = Pattern.compile(FORBIDDEN_WORDS_REGEX);
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }
    private boolean isValidTitle(String name) { return !containsForbiddenWords(name.toLowerCase()); }
    private boolean isValidPlace(String name) {
        return  !name.toLowerCase().matches(FORBIDDEN_WORDS_REGEX) && name.toLowerCase().matches(PLACE_REGEX);
    }

    private boolean isValidDescription(String name) {
        return !name.toLowerCase().matches(FORBIDDEN_WORDS_REGEX);
    }

    public static boolean isValidName(String name) {
        return !containsForbiddenWords(name.toLowerCase());
    }

    private String setMeetingTitle(long chatId, String messageText) {
        if (isValidTitle(messageText)) {
            meetingRepository.setTitleByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_DESCRIPTION, chatId);
            return SET_MEETING_DESCRIPTION_TEXT;
        } else {
            return INCORRECT_MEETING_TITLE;
        }
    }

    private String setMeetingDescription(long chatId, String messageText) {
        if (isValidDescription(messageText)) {
            meetingRepository.setDescriptionByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_PLACE, chatId);
            return (SET_MEETING_PLACE_TEXT);
        }else {
            return INCORRECT_MEETING_DESCRIPTION;
        }
    }

    private String setMeetingPlace(long chatId, String messageText) {
        if (isValidPlace(messageText)) {
            meetingRepository.setPlaceByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_EVENT_DATE, chatId);
            return (SET_MEETING_EVENT_DATE_TEXT);
        }else {
            return INCORRECT_MEETING_PLACE;
        }
    }

    private String setMeetingEventDate(long chatId, String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STYLE);
        Date currentDate = new Date();
        dateFormat.setLenient(false);

        try {
            Date parsedDate = dateFormat.parse(dateString);

            if (parsedDate.before(currentDate)) {
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_REGISTRATION, chatId);
                sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_EVENT_DATE, chatId);
                return INCORRECT_DATE_TEXT;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            if (month > 12 || day > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_REGISTRATION, chatId);
                sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_EVENT_DATE, chatId);
                return INCORRECT_DATE_TEXT;
            }

            Timestamp eventDate = new Timestamp(parsedDate.getTime());
            meetingRepository.setEventDateByOwnerIdAndMeetingId(eventDate, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_USER_LIMIT, chatId);
            return SET_MEETING_USER_LIMIT_TEXT;
        } catch (ParseException e) {
            e.printStackTrace();
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_MEETING_REGISTRATION, chatId);
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_EVENT_DATE, chatId);
            return INCORRECT_DATE_TEXT;
        }
    }


    private String setMeetingUserLimit(long chatId, String messageText) {
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

    private String setMeetingAnimalType(long chatId, String messageText) {
        if (isValidName(messageText)) {
            meetingRepository.setAnimalTypeByOwnerIdAndMeetingId(messageText, chatId, sessionRepository.findByChatId(chatId).getMeetingRegisterFunctionId());
            sessionRepository.setMeetingRegisterFunctionContext(SET_MEETING_BREED, chatId);
            return (SET_MEETING_BREED_TEXT);
        }else {
            return INCORRECT_PET_TYPE;
        }
    }

    private String setMeetingBreed(long chatId, String messageText) {
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
