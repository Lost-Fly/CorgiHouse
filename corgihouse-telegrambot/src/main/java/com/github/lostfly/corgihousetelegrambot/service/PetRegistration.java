package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Timestamp;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.GLOBAL_CONTEXT_DEFAULT;
import static com.github.lostfly.corgihousetelegrambot.constants.UserRegConstants.*;

@Slf4j
@Component
public class PetRegistration {

    public static final String SET_PET_NAME_TEXT = "Введите кличку:";
    public static final String SET_PET_NAME = "set_pet_name";
    public static final String REGISTER_PET_ENDED_TEXT = "Регистрация завершена";
    public static final String REGISTER_PET_TYPE_ANIMAL_TEXT = "Введите тип животного:";
    public static final String REGISTER_PET_TYPE_ANIMAL = "register_pet_type_animal";
    public static final String REGISTER_PET_BREED_TEXT = "Введите пароду животного:";
    public static final String REGISTER_PET_BREED = "register_pet_breed";
    public static final String REGISTER_PET_PHOTO_TEXT = "Отправьте фото животного:";
    public static final String REGISTER_PET_PHOTO ="register_pet_photo";
    public static final String CANCEL_OPERATION = "Операция отменена:";




    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;


    @Autowired
    private SessionRepository sessionRepository;


    public String initializeRegistration(Update update) {

        var chatId = update.getCallbackQuery().getMessage().getChatId();



        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_PET_REGISTRATION, chatId);

        Pet pet = new Pet();

        pet.setOwnerId(chatId);

        System.out.println(petRepository.findTopByOrderByOwnerIdDesc(chatId));
        if (petRepository.findTopByOrderByOwnerIdDesc(chatId) != null) {
            pet.setPetId(petRepository.findTopByOrderByOwnerIdDesc(chatId) + 1);
        }
        else {
            Long petId = 1L;
            pet.setPetId(petId);
        }


        petRepository.save(pet);

        log.info("pet saved: " + pet);
        sessionRepository.setPetRegisterFunctionContext(SET_PET_NAME,chatId);
        return NewPetCommandReceived(chatId);

    }

    private static String NewPetCommandReceived(long chatId) {
        log.info("Register pet " + " " + chatId);
        return SET_PET_NAME_TEXT;
    }


    public String continueRegistration(Update update) {

        System.out.println("continue3 reg");
        var chatId = update.getMessage().getChatId();
        var messageText = update.getMessage().getText();

        return switch (sessionRepository.findByChatId(chatId).getPetRegisterFunctionContext()) {
            case (SET_PET_NAME) -> SetPetName(chatId, messageText);
            case (REGISTER_PET_TYPE_ANIMAL) -> SetPetType(chatId, messageText);
            case (REGISTER_PET_BREED) -> SetPetBreed(chatId, messageText);
            case (REGISTER_PET_PHOTO) -> SetPetPhoto(chatId,update);
            default -> INDEV_TEXT;
        };
    }


    private String SetPetBreed(long chatId, String messageText) {
        System.out.println("continue3 SetPetBreed");
        petRepository.setPetBreedByOwnerIdAndPetId(messageText,chatId,petRepository.findTopByOrderByOwnerIdDesc(chatId));
        sessionRepository.setPetRegisterFunctionContext(REGISTER_PET_PHOTO, chatId);
        return (REGISTER_PET_PHOTO_TEXT);
    }

    private String SetPetPhoto(Long chatId,Update update) {
        System.out.println("continue3 SetPetPhoto");
        //petRepository.setPetBreedByOwnerIdAndPetId(messageText,chatId,petRepository.findTopByOrderByOwnerIdDesc(chatId));
        sessionRepository.setPetRegisterFunctionContext(REGISTER_CONTEXT_DEFAULT, chatId);
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
        return (REGISTER_PET_ENDED_TEXT);
    }

    private String SetPetType(long chatId, String messageText) {
        System.out.println("continue3 SetPetType");
        petRepository.setAnimalTypeByOwnerIdAndPetId(messageText,chatId, petRepository.findTopByOrderByOwnerIdDesc(chatId));
        sessionRepository.setPetRegisterFunctionContext(REGISTER_PET_BREED, chatId);
        return (REGISTER_PET_BREED_TEXT);
    }

    private String SetPetName(long chatId, String messageText) {
        System.out.println("continue3 SetPetName");
        petRepository.setPetNameByOwnerIdAndPetId(messageText,chatId,petRepository.findTopByOrderByOwnerIdDesc(chatId));
        sessionRepository.setPetRegisterFunctionContext(REGISTER_PET_TYPE_ANIMAL, chatId);
        return (REGISTER_PET_TYPE_ANIMAL_TEXT);
    }



}

