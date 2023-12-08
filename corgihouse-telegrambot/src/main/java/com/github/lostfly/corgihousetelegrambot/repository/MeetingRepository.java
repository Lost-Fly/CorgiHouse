package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.Meeting;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.ArrayList;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {

    Meeting findByMeetingIdAndOwnerId(Long meetingId, Long ownerId);

    Meeting findByMeetingId(Long meetingId);

    ArrayList<Meeting> findAllByOwnerId(Long ownerId);

    ArrayList<Meeting> findAllByOwnerIdNot(Long ownerId);

    ArrayList<Meeting> findAll();

    ArrayList<Meeting> findAllByAnimalType(String animalType);

    @Modifying
    @Transactional
    @Query("SELECT m FROM meetingDataTable m " +
            "JOIN userToMeetingDataTable utm ON m.meetingId = utm.meetingId " +
            "WHERE utm.chatId = :chatId AND utm.chatId != m.ownerId")
    ArrayList<Meeting> findMyAppliedMeetings(@Param("chatId") Long chatId);

    @Transactional
    void deleteAllByOwnerId(Long ownerId);

    @Transactional
    void deleteAllByMeetingId(Long meetingId);

    @Transactional
    void deleteByOwnerIdAndMeetingId(Long ownerId, Long meetingId);


    @Query("select max(meeting.meetingId) from meetingDataTable meeting")
    Long findTopByOrderByMeetingIdDesc();


    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.animalType = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setAnimalTypeByOwnerIdAndMeetingId(String animalType, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.breed = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setBreedByOwnerIdAndMeetingId(String breed, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.title = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setTitleByOwnerIdAndMeetingId(String title, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.description = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setDescriptionByOwnerIdAndMeetingId(String description, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.place = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setPlaceByOwnerIdAndMeetingId(String place, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.completed = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setCompletedByOwnerIdAndMeetingId(Boolean completed, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.fullFilled = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setFullFilledByOwnerIdAndMeetingId(Boolean fullFilled, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.userLimit = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setUserLimitByOwnerIdAndMeetingId(Integer userLimit, Long ownerId, Long meetingId);

    @Modifying
    @Transactional
    @Query("update meetingDataTable u set u.eventDate = ?1 where u.ownerId = ?2 and u.meetingId = ?3")
    void setEventDateByOwnerIdAndMeetingId(Timestamp eventDate, Long ownerId, Long meetingId);


}
