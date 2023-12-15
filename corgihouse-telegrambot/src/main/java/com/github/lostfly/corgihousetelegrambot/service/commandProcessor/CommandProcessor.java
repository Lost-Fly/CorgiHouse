package com.github.lostfly.corgihousetelegrambot.service.commandProcessor;

import com.github.lostfly.corgihousetelegrambot.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.UserFuncsConstants.EDIT_CHOISE;
import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.CommandListConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.KeyboardMenusConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.keyboardsConstants.ListMenusConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.ERROR_GLOBAL_CONTEXT_OCCURRED;
import static com.github.lostfly.corgihousetelegrambot.constants.logsConstants.LogsConstants.ERROR_OCCURRED;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.CANCEL_OPERATION;
import static com.github.lostfly.corgihousetelegrambot.constants.regConstants.PetRegConstants.REGISTER_PET_PHOTO;

@Slf4j
@Component
public class CommandProcessor {
    private Long chatId = 0L;
    private Long messageId = 0L;
    private Update update = new Update();
    private final TelegramBot bot;

    public CommandProcessor(TelegramBot bot) {
        this.bot = bot;
    }

    public void processCallBackCommand(String callBackData, Long chatId, Long messageId, Update update) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.update = update;

        Map<String, Runnable> commandMap = callBackCommandMap();

        Runnable defaultAction = () -> bot.sendMessage(chatId, INDEV_TEXT, bot.keyboardMenus.mainKeyboard());

        commandMap.getOrDefault(callBackData, defaultAction).run();
    }

    private Map<String, Runnable> callBackCommandMap() {
        Map<String, Runnable> commandMap = new HashMap<>();

        commandMap.put(DELETE_PROFILE_QUESTION, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.deleteProfileQuestion(), bot.listMenus.profileDeleteKeyboard()));
        commandMap.put(DELETE_PROFILE_CONFIRM, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.deleteProfile(chatId)));
        commandMap.put(DELETE_PROFILE_DENY, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.showProfile(chatId), bot.listMenus.profileButtonKeyboard()));
        commandMap.put(EDIT_PROFILE, () ->
                bot.sendEditMessage(chatId, messageId, EDIT_CHOISE, bot.listMenus.profileEditKeyboard()));
        commandMap.put(EDIT_PROFILE_NAME, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.editProfile(chatId, EDIT_PROFILE_NAME)));
        commandMap.put(EDIT_PROFILE_LASTNAME, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.editProfile(chatId, EDIT_PROFILE_LASTNAME)));
        commandMap.put(EDIT_PROFILE_PHONE_NUMBER, () ->
                bot.sendEditMessage(chatId, messageId, bot.userFuncs.editProfile(chatId, EDIT_PROFILE_PHONE_NUMBER)));
        commandMap.put(PET_QUESTION_CONFIRM, () ->
                bot.sendEditMessage(chatId, messageId, bot.petRegistration.initializeRegistration(update)));
        commandMap.put(PET_ADD, () ->
                bot.sendEditMessage(chatId, messageId, bot.petRegistration.initializeRegistration(update)));
        commandMap.put(PET_DELETE_SELECTION, () ->
                bot.sendMessage(chatId, bot.petsFuncs.deleteAnimalSelection(chatId)));
        commandMap.put(PET_QUESTION_DENY, () ->
                bot.sendEditMessage(chatId, messageId, CANCEL_OPERATION));
        commandMap.put(MEETING_ADD, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingRegistration.initializeRegistration(update)));
        commandMap.put(CREATED_MEETINGS_FULL_INFO_SELECT, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.fullInfoMeetingSelection(chatId)));
        commandMap.put(APPLIED_MEETINGS_FULL_INFO_SELECT, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.fullInfoMeetingSelection(chatId)));
        commandMap.put(MEETING_DELETE_BUTTON_SELECT, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.deleteMeetingSelection(chatId)));
        commandMap.put(MEETING_EDIT_BUTTON_SELECT, () ->
                bot.sendMessage(chatId, bot.meetingFuncs.editMeetingSelectionNumber(chatId)));
        commandMap.put(EDIT_MEETING_BUTTON_TITLE, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_TITLE)));
        commandMap.put(EDIT_MEETING_BUTTON_PLACE, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_PLACE)));
        commandMap.put(EDIT_MEETING_BUTTON_ANIMALTYPE, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_ANIMALTYPE)));
        commandMap.put(EDIT_MEETING_BUTTON_BREED, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_BREED)));
        commandMap.put(EDIT_MEETING_BUTTON_DATE, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_DATE)));
        commandMap.put(EDIT_MEETING_BUTTON_DESCRIPTION, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_DESCRIPTION)));
        commandMap.put(EDIT_MEETING_BUTTON_LIMIT, () ->
                bot.sendEditMessage(chatId, messageId, bot.meetingFuncs.editMeeting(chatId, EDIT_MEETING_BUTTON_LIMIT)));
        commandMap.put(REGISTRATION, () ->
                bot.sendMessage(chatId, bot.userRegistration.initializeRegistration(update)));

        return commandMap;
    }

    public void processTextCommandDefaultContext(String messageText, Long chatId, Long messageId, Update update) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.update = update;

        Map<String, Runnable> commandMap = textCommandDefaultContextMap();

        Runnable defaultAction = () -> bot.sendMessage(chatId, INDEV_TEXT, bot.keyboardMenus.mainKeyboard());

        commandMap.getOrDefault(messageText, defaultAction).run();
    }

    private Map<String, Runnable> textCommandDefaultContextMap() {
        Map<String, Runnable> commandMap = new HashMap<>();

        commandMap.put(ADMIN_SEND_BROADCAST, () -> {
            if (adminsIdList.contains(chatId)) {
                bot.sessionRepository.setGlobalContextByChatId(GLOBAL_ADMIN_BROADCAST, chatId);
                bot.sendMessage(chatId, ADMIN_INPUT_BROADCAST_MESSAGE);
            } else {
                bot.sendMessage(chatId, NOT_AN_ADMIN);
            }
        });
        commandMap.put(START_MENU_COMMAND, () ->
                bot.sendMessage(chatId, bot.generalFuncs.startCommandReceived(chatId, update.getMessage().getChat().getFirstName()), bot.keyboardMenus.mainKeyboard()));
        commandMap.put(HELP_MENU_COMMAND, () ->
                bot.sendMessage(chatId, HELP_TEXT, bot.keyboardMenus.mainKeyboard()));
        commandMap.put(REGISTER_MENU_COMMAND, () ->
                bot.sendMessage(chatId, bot.userRegistration.initializeRegistration(update)));
        commandMap.put(PETS, () ->
                bot.sendMessage(chatId, bot.petsFuncs.showPets(chatId)));
        commandMap.put(MY_MEETINGS, () -> {
            bot.sendMessage(chatId, bot.meetingFuncs.changeToMyMeetings(chatId));
            bot.sendMessage(chatId, bot.meetingFuncs.showMyMeetings(chatId));
        });
        commandMap.put(MY_MEETINGS_CREATED, () ->
                bot.sendMessage(chatId, bot.meetingFuncs.showCreatedMeetings(chatId)));
        commandMap.put(MY_MEETINGS_APPLIED, () ->
                bot.sendMessage(chatId, bot.meetingFuncs.showAppliedMeetings(chatId)));
        commandMap.put(SEARCH_MEETINGS, () -> {
            bot.sendMessage(chatId, bot.searchMeetings.searchMeetings(chatId));
            try {
                bot.sendPhoto(chatId, bot.searchMeetings.showRandomPet(), CORGI_PHOTO_TO_YOU);
            } catch (TelegramApiException | IOException e) {
                log.error(ERROR_OCCURRED + e.getMessage());
            }
        });
        commandMap.put(BACK, () ->
                bot.sendMessage(chatId, bot.meetingFuncs.changeToMainMenu(chatId)));
        commandMap.put(PROFILE, () -> {
            bot.sendMessage(chatId, bot.userFuncs.checkExistingProfile(chatId));
            bot.sendMessage(chatId, bot.userFuncs.showProfile(chatId), bot.listMenus.profileButtonKeyboard());
        });

        return commandMap;
    }

    public void processGlobalContextCommand(String globalFunctionContext, String messageText, Long _messageId, Long _chatId, Update _update) {
        this.chatId = _chatId;
        this.messageId = _messageId;
        this.update = _update;

        Map<String, Runnable> commandMap = globalContextCommandMap(messageText);

        Runnable defaultAction = () -> {
            bot.sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            log.error(ERROR_GLOBAL_CONTEXT_OCCURRED + update.getMessage().getText());
            bot.sendMessage(chatId, INDEV_TEXT, bot.keyboardMenus.mainKeyboard());
        };

        commandMap.getOrDefault(globalFunctionContext, defaultAction).run();
    }

    private Map<String, Runnable> globalContextCommandMap(String messageText) {
        Map<String, Runnable> commandMap = new HashMap<>();

        commandMap.put(GLOBAL_ADMIN_BROADCAST, () -> bot.adminBroadcastNews(messageText, chatId));
        commandMap.put(GLOBAL_CONTEXT_USER_REGISTRATION, () -> bot.sendMessage(chatId, bot.userRegistration.continueRegistration(update)));
        commandMap.put(GLOBAL_CONTEXT_PET_REGISTRATION, () -> {
            if (update.getMessage().hasPhoto() && Objects.equals(bot.sessionRepository.findByChatId(chatId).getPetRegisterFunctionContext(), REGISTER_PET_PHOTO)) {
                bot.downloadImage(update, bot.petRepository.findTopByOrderByOwnerIdDesc(chatId));
            }
            bot.sendMessage(chatId, bot.petRegistration.continueRegistration(update));
        });
        commandMap.put(GLOBAL_CONTEXT_USER_EDIT, () -> bot.sendMessage(chatId, bot.userFuncs.editProfileAction(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_PET_DELETE, () -> bot.sendMessage(chatId, bot.petsFuncs.deleteAnimal(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_FULL_MEETING_INFO, () -> bot.sendMessage(chatId, bot.meetingFuncs.fullInfoMeetingByNumber(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_MEETING_DELETE, () -> bot.sendMessage(chatId, bot.meetingFuncs.deleteMeeting(chatId, messageText)));
        commandMap.put(GLOBAL_CONTEXT_MEETING_REGISTRATION, () -> bot.sendMessage(chatId, bot.meetingRegistration.continueRegistration(update)));
        commandMap.put(GLOBAL_CONTEXT_MEETING_EDIT, () -> bot.sendMessage(chatId, bot.meetingFuncs.functionEditMeeting(chatId, messageText)));

        return commandMap;
    }


}
