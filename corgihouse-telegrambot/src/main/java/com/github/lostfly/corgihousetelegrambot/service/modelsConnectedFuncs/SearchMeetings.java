package com.github.lostfly.corgihousetelegrambot.service.modelsConnectedFuncs;

import com.github.lostfly.corgihousetelegrambot.keyboardMenus.KeyboardMenus;
import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import com.github.lostfly.corgihousetelegrambot.repository.MeetingRepository;
import com.github.lostfly.corgihousetelegrambot.repository.PetRepository;
import com.github.lostfly.corgihousetelegrambot.repository.SessionRepository;
import com.github.lostfly.corgihousetelegrambot.repository.UserToMeetingRepository;
import com.github.lostfly.corgihousetelegrambot.service.generalFuncs.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Random;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.MeetingFuncsConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.ShowMeetingsConstants.NO_AT_ALL_MEETINGS_TEXT;

@Slf4j
@Component
public class SearchMeetings {

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
    private MeetingFuncs meetingFuncs;
    @Autowired
    private FileService fileService;
    @Autowired
    private PetRepository petRepository;

    public String showAllMeetingInfo(Meeting meeting) {
        String fullFilledStatus = meeting.getFullFilled() ? YES_FOR_MSG : NO_FOR_MSG;
        String completedStatus = meeting.getCompleted() ? YES_FOR_MSG : NO_FOR_MSG;
        String created_meeting_item = MEETING_ID_FOR_MSG + meeting.getMeetingId() + "\n\n" +
                MEETING_NAME_FOR_MSG + meeting.getTitle() + "\n\n" +
                MEETING_DATE_FOR_MSG + meeting.getEventDate() + "\n\n" +
                MEETING_PET_TYPE_FOR_MSG + meeting.getAnimalType() + "\n\n" +
                MEETING_PET_BREED_FOR_MSG + meeting.getBreed() + "\n\n" +
                MEETING_DESCRIPTION_FOR_MSG + meeting.getDescription() + "\n\n" +
                MEETING_PLACE_FOR_MSG + meeting.getPlace() + "\n\n" +
                MEETING_MAX_PARTICIPANTS_AMOUNT_FOR_MSG + meeting.getUserLimit() + "\n\n" +
                MEETING_FULFILLED_FOR_MSG + fullFilledStatus + "\n\n" +
                MEETING_HAS_ENDED_FOR_MSG + completedStatus + "\n\n";
        return created_meeting_item;
    }

    private boolean shouldReturnNull() {
        Random random = new Random();
        return random.nextInt(10) == 0;
    }

    public File showRandomPet() throws IOException {
        if (!shouldReturnNull()) {
            return null;
        }

        Random random = new Random();

        return fileService.giveCorgiPhotoByFilePath((long) random.nextInt(10));
    }

    public SendMessage searchMeetings(long chatId) {

        if (userFuncs.checkExistingProfile(chatId) != null) {
            return userFuncs.checkExistingProfile(chatId);
        }

        ArrayList<Meeting> all_meetings_for_search = meetingRepository.findMyNotAppliedMeetings(chatId);
        long numberSearchMeetingId=sessionRepository.findByChatId(chatId).getNumberSearchMeeting()+1L;
        SendMessage message = new SendMessage();

        if (all_meetings_for_search.isEmpty()) {
            message.setText(NO_AT_ALL_MEETINGS_TEXT);
            message.setReplyMarkup(listMenus.meetingKeyboard());
        } else {
            if (numberSearchMeetingId >= all_meetings_for_search.size()){
                numberSearchMeetingId=0L;
                sessionRepository.setNumberSearchMeetingByChatId(chatId,numberSearchMeetingId);
            }

            Meeting meeting = all_meetings_for_search.get(Math.toIntExact(numberSearchMeetingId));
            sessionRepository.setNumberSearchMeetingByChatId(chatId,numberSearchMeetingId);
            message.setText(showAllMeetingInfo(meeting));
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.searchMeetingKeyboard());
        }
        return message;

    }
    public SendMessage likeMeeting (long chatId) {
        UserToMeeting userToMeeting=new UserToMeeting();
        if (userToMeetingRepository.findTopByOrderByIdDesc() != null) {
            Long Id = userToMeetingRepository.findTopByOrderByIdDesc();
            userToMeeting.setId(Id + 1L);
        } else {
            Long Id = 1L;
            userToMeeting.setId(Id);
        }

        long numberSearchMeetingId=sessionRepository.findByChatId(chatId).getNumberSearchMeeting()+1L;
        ArrayList<Meeting> all_meetings_for_search = meetingRepository.findMyNotAppliedMeetings(chatId);
        Meeting meeting = all_meetings_for_search.get(Math.toIntExact(numberSearchMeetingId));
        sessionRepository.setNumberSearchMeetingByChatId(chatId,numberSearchMeetingId);
        userToMeeting.setMeetingId(meeting.getMeetingId());
        userToMeeting.setChatId(chatId);
        userToMeetingRepository.save(userToMeeting);

        return searchMeetings(chatId);

    }


}
