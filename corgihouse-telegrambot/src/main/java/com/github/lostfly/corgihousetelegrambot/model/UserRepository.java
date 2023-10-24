package com.github.lostfly.corgihousetelegrambot.model;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Modifying
    @Transactional
    @Query("update usersDataTable u set u.firstName = ?1 where u.chatId = ?2")
    void setFirstNameByChatId(String firstname,Long chatId);

    @Modifying
    @Transactional
    @Query("update usersDataTable u set u.lastName = ?1 where u.chatId = ?2")
    void setLastNameByChatId(String firstname,Long chatId);

    @Modifying
    @Transactional
    @Query("update usersDataTable u set u.phoneNumber = ?1 where u.chatId = ?2")
    void setPhoneNumberByChatId(String firstname,Long chatId);

    User findByChatId(Long chatId);


}
