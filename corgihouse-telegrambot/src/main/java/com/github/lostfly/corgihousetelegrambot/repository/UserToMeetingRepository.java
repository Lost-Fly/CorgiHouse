package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.UserToMeeting;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryUserToMeeting.FIND_TOP_BY_ORDER_BY_ID_DESC;

public interface UserToMeetingRepository extends CrudRepository<UserToMeeting, Long> {

    UserToMeeting findByChatIdAndMeetingId(Long chatId, Long meetingId);

    ArrayList<UserToMeeting> findAllByChatId(Long chatId);

    ArrayList<UserToMeeting> findAllByMeetingId(Long meetingId);

    @Transactional
    Long countByMeetingId(Long meetingId);

    @Query(FIND_TOP_BY_ORDER_BY_ID_DESC)
    Long findTopByOrderByIdDesc();

    @Transactional
    void deleteAllByChatId(Long chatId);

    @Transactional
    void deleteByChatIdAndMeetingId(Long chatId, Long meetingId);

    @Transactional
    void deleteAllByMeetingId(Long meetingId);

}
