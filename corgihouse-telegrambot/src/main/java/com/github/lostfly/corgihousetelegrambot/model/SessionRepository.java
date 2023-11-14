package com.github.lostfly.corgihousetelegrambot.model;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface SessionRepository extends CrudRepository<UserSession, Long> {

    UserSession findByChatId(Long chatId);

    @Modifying
    @Transactional
    @Query("update sessionDataTable u set u.globalFunctionContext = ?1 where u.chatId = ?2")
    void setGlobalContextByChatId(String globalContext, Long chatId);

    @Modifying
    @Transactional
    @Query("update sessionDataTable u set u.registerFunctionContext = ?1 where u.chatId = ?2")
    void setRegUserContextByChatId(String regUserContext, Long chatId);

    @Transactional
    void deleteByChatId(Long chatId);

    @Modifying
    @Transactional
    @Query("update sessionDataTable u set u.editFunctionContext = ?1 where u.chatId = ?2")
    void setEditUserContextByChatId(String EditUserContext, Long chatId);

    @Modifying
    @Transactional
    @Query("update sessionDataTable u set u.petRegisterFunctionContext = ?1 where u.chatId = ?2")
    void setPetRegisterFunctionContext(String PetRegisterFunctionContext, Long chatId);


}
