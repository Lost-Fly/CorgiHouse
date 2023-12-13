package com.github.lostfly.corgihousetelegrambot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryUserToMeeting.ENTITY_NAME_USER_TO_MEETING;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_USER_TO_MEETING)
public class UserToMeeting {

    @Id
    private Long id;
    private Long chatId;
    private Long meetingId;

}
