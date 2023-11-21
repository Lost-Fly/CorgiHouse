package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.PetFuncsConstants.*;

@Slf4j
@Component
public class PetsFuncs {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ListMenus listMenus;
    @Autowired
    private PetRepository petRepository;

    public SendMessage showPets(Long chatId) {


        ArrayList<Pet> pets = petRepository.findAllByOwnerId(chatId);

        if (pets.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setText(NO_PETS_TEXT);
            message.setReplyMarkup(listMenus.petQuestionButtonKeyboard());
            return message;
        } else {
            StringBuilder pet_info_message = new StringBuilder();

            for (Pet pet : pets) {
                pet_info_message.append("ID животного: ").append(pet.getPetId()).append("\n")
                        .append("Имя животного: ").append(pet.getPetName()).append("\n")
                        .append("Тип животного: ").append(pet.getAnimalType()).append("\n")
                        .append("Порода животного: ").append(pet.getPetBreed()).append("\n")
                        .append("ID владельца: ").append(pet.getOwnerId()).append("\n\n");
            }

            SendMessage message = new SendMessage();
            message.setText(pet_info_message.toString());
            message.setReplyMarkup(listMenus.petAddButtonKeyboard());

            return message;

        }

    }

    public String deleteAnimalSelection(Long chatId) {
        sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_PET_DELETE, chatId);
        return DELETE_ANIMAL_SELECT_TEXT;
    }

    public String deleteAnimal(Long chatId, String getFromMsg) {
        Long petId;
        try {
            petId = Long.parseLong(getFromMsg);
        } catch (NumberFormatException e) {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_NUMBER_ANS;
        }

        if (petRepository.findByPetIdAndOwnerId(petId, chatId) != null) {
            petRepository.deleteByOwnerIdAndPetId(chatId, petId);
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return DELETE_ANIMAL_TEXT;
        } else {
            sessionRepository.setGlobalContextByChatId(GLOBAL_CONTEXT_DEFAULT, chatId);
            return INCORRECT_PET_NUMBER_ANS;
        }
    }


}
