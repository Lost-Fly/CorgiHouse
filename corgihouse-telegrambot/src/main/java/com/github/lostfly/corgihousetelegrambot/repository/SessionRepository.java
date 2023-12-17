package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.UserSession;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QuerySession.*;

public interface SessionRepository extends CrudRepository<UserSession, Long> {

    UserSession findByChatId(Long chatId);


    @Modifying
    @Transactional
    @Query(SET_GLOBAL_CONTEXT_BY_CHAT_ID)
    void setGlobalContextByChatId(String globalContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_REG_USER_CONTEXT_BY_CHAT_ID)
    void setRegUserContextByChatId(String regUserContext, Long chatId);

    @Transactional
    void deleteByChatId(Long chatId);

    @Modifying
    @Transactional
    @Query(SET_EDIT_USER_CONTEXT_BY_CHAT_ID)
    void setEditUserContextByChatId(String EditUserContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_PET_REGISTER_FUNCTION_CONTEXT)
    void setPetRegisterFunctionContext(String PetRegisterFunctionContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_MEETING_REGISTER_FUNCTION_CONTEXT)
    void setMeetingRegisterFunctionContext(String MeetingRegisterFunctionContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_MEETING_REGISTER_FUNCTION_ID)
    void setMeetingRegisterFunctionId(Long MeetingRegisterFunctionId, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_EDIT_MEETING_FUNCTION_CONTEXT_BY_CHAT_ID)
    void setEditMeetingFunctionContextByChatId(String EditMeetingContext, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_NUMBER_EDIT_MEETING_BY_CHAT_ID)
    void setNumberEditMeetingByChatId(Long NumberEditMeeting, Long chatId);

    @Modifying
    @Transactional
    @Query(SET_NUMBER_SEARCH_MEETING_BY_CHAT_ID)
    void setNumberSearchMeetingByChatId(Long NumberEditMeeting, Long chatId);



}
