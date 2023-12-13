package com.github.lostfly.corgihousetelegrambot.repository;


import com.github.lostfly.corgihousetelegrambot.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryUser.*;

public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(SET_FIRST_NAME_BY_CHAT_ID)
    void setFirstNameByChatId(String firstname, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_LAST_NAME_BY_CHAT_ID)
    void setLastNameByChatId(String firstname, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_PHONE_NUMBER_BY_CHAT_ID)
    void setPhoneNumberByChatId(String firstname, Long chatId);


    User findByChatId(Long chatId);

    @Transactional
    void deleteByChatId(Long chatId);

    ArrayList<User> findAll();

}
