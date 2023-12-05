package com.github.lostfly.corgihousetelegrambot.service.modelsConnectedFuncs;

import com.github.lostfly.corgihousetelegrambot.keyboardMenus.KeyboardMenus;
import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import com.github.lostfly.corgihousetelegrambot.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.MeetingFuncsConstants.*;


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
//        ArrayList<UserToMeeting> my_meetings_applied_list = userToMeetingRepository.findAllByChatId(chatId);
//
//        ArrayList<Meeting> my_meetings_applied = new ArrayList<>();
//
//        for (UserToMeeting utmObject : my_meetings_applied_list) {
//            if (!Objects.equals(meetingRepository.findByMeetingId(utmObject.getMeetingId()).getOwnerId(), chatId)) {
//                my_meetings_applied.add(meetingRepository.findByMeetingId(utmObject.getMeetingId()));
//            }
//        }
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

    public String showMainMeetingInfo(Meeting meeting){
        String created_meeting_item = "ID события: " + meeting.getMeetingId() + "\n" +
                "Название: " + meeting.getTitle() + "\n" +
                "Дата: " + meeting.getEventDate() + "\n" +
                "Тип животного: " + meeting.getAnimalType() + "\n" +
                "Порода: " + meeting.getBreed() + "\n\n";
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

                String created_meeting_item = "ID события: " + meeting.getMeetingId() + "\n" +
                        "Название: " + meeting.getTitle() + "\n" +
                        "Дата: " + meeting.getEventDate() + "\n" +
                        "Тип животного: " + meeting.getAnimalType() + "\n" +
                        "Порода: " + meeting.getBreed() + "\n\n";

                applied_meetings_list.append(created_meeting_item);
            }

            message.setText(applied_meetings_list.toString());
            message.setChatId(chatId);
            message.setReplyMarkup(listMenus.appliedMeetingKeyboard());
        }

        return message;

    }


}


