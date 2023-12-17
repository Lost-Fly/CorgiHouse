package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryMeeting.*;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {

    Meeting findByMeetingIdAndOwnerId(Long meetingId, Long ownerId);

    Meeting findByMeetingId(Long meetingId);

    ArrayList<Meeting> findAllByOwnerId(Long ownerId);

    ArrayList<Meeting> findAllByOwnerIdNot(Long ownerId);

    ArrayList<Meeting> findAll();

    ArrayList<Meeting> findAllByAnimalType(String animalType);

    @Modifying
    @Transactional
    @Query(FIND_MY_APPLIED_MEETINGS)
    ArrayList<Meeting> findMyAppliedMeetings(@Param(PARAM_CHAT_ID) Long chatId);


    @Modifying
    @Transactional
    @Query(FIND_MY_NOT_APPLIED_MEETINGS)
    ArrayList<Meeting> findMyNotAppliedMeetings(@Param(PARAM_CHAT_ID) Long chatId);

    @Transactional
    void deleteAllByOwnerId(Long ownerId);

    @Transactional
    void deleteAllByMeetingId(Long meetingId);

    @Transactional
    void deleteByOwnerIdAndMeetingId(Long ownerId, Long meetingId);


    @Query(FIND_TOP_BY_ORDER_BY_MEETING_ID_DESC)
    Long findTopByOrderByMeetingIdDesc();


    @Modifying
    @Transactional
    @Query(SET_ANIMAL_TYPE_BY_OWNER_ID_AND_MEETING_ID)
    void setAnimalTypeByOwnerIdAndMeetingId(String animalType, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_BREED_BY_OWNER_ID_AND_MEETING_ID)
    void setBreedByOwnerIdAndMeetingId(String breed, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_TITLE_BY_OWNER_ID_AND_MEETING_ID)
    void setTitleByOwnerIdAndMeetingId(String title, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_DESCRIPTION_BY_OWNER_ID_AND_MEETING_ID)
    void setDescriptionByOwnerIdAndMeetingId(String description, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_PLACE_BY_OWNER_ID_AND_MEETING_ID)
    void setPlaceByOwnerIdAndMeetingId(String place, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_COMPLETED_BY_OWNER_ID_AND_MEETING_ID)
    void setCompletedByOwnerIdAndMeetingId(Boolean completed, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_FULFILLED_BY_OWNER_ID_AND_MEETING_ID)
    void setFullFilledByOwnerIdAndMeetingId(Boolean fullFilled, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_USER_LIMIT_BY_OWNER_ID_AND_MEETING_ID)
    void setUserLimitByOwnerIdAndMeetingId(Integer userLimit, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query(SET_EVENT_DATE_BY_OWNER_ID_AND_MEETING_ID)
    void setEventDateByOwnerIdAndMeetingId(Timestamp eventDate, Long ownerId, Long meetingId);


}
