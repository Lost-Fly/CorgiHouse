package com.github.lostfly.corgihousetelegrambot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "userToMeetingDataTable")
public class UserToMeeting {

    @Id
    private Long id;
    private Long chatId;
    private Long meetingId;

}
