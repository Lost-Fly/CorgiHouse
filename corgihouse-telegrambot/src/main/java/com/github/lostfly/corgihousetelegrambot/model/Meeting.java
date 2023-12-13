package com.github.lostfly.corgihousetelegrambot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryMeeting.ENTITY_NAME_MEETING;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_MEETING)
public class Meeting {

    @Id
    private Long meetingId;

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

}
