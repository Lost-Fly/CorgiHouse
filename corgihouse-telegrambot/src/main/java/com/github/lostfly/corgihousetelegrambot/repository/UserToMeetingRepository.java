package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserToMeetingRepository extends CrudRepository<UserToMeeting, Long> {

    UserToMeeting findByChatIdAndMeetingId(Long chatId, Long meetingId);

    ArrayList<UserToMeeting> findAllByChatId(Long chatId);

    ArrayList<UserToMeeting> findAllByMeetingId(Long meetingId);

    @Query("select max(element.id) from userToMeetingDataTable element")
    Long findTopByOrderByIdDesc();

    @Transactional
    void deleteAllByChatId(Long chatId);

    @Transactional
    void deleteByChatIdAndMeetingId(Long chatId, Long meetingId);

    @Transactional
    void deleteAllByMeetingId(Long meetingId);

}
