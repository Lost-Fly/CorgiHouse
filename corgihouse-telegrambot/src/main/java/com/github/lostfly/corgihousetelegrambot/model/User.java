package com.github.lostfly.corgihousetelegrambot.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryUser.ENTITY_NAME_USER;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_USER)
public class User {

    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private String phoneNumber;

    private Timestamp registeredAt;

}
