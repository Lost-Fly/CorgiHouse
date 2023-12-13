package com.github.lostfly.corgihousetelegrambot.service.modelsConnectedFuncs;

import com.github.lostfly.corgihousetelegrambot.listMenus.ListMenus;
import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.repository.PetRepository;
import com.github.lostfly.corgihousetelegrambot.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.*;
import static com.github.lostfly.corgihousetelegrambot.constants.funcsConstants.PetFuncsConstants.*;

@Slf4j
@Component
public class PetsFuncs {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ListMenus listMenus;
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserFuncs userFuncs;

    public SendMessage showPets(Long chatId) {

        if (userFuncs.checkExistingProfile(chatId) != null){
            return userFuncs.checkExistingProfile(chatId);
        }


        ArrayList<Pet> pets = petRepository.findAllByOwnerId(chatId);

        if (pets.isEmpty()) {
            SendMessage message = new SendMessage();
            message.setText(NO_PETS_TEXT);
            message.setReplyMarkup(listMenus.petQuestionButtonKeyboard());
            return message;
        } else {
            StringBuilder pet_info_message = new StringBuilder();

            for (Pet pet : pets) {
                pet_info_message.append(PET_ID_FOR_MSG).append(pet.getPetId()).append("\n")
                        .append(PET_NAME_FOR_MSG).append(pet.getPetName()).append("\n")
                        .append(PET_TYPE_FOR_MSG).append(pet.getAnimalType()).append("\n")
                        .append(PET_BREED_FOR_MSG).append(pet.getPetBreed()).append("\n")
                        .append(PET_OWNER_ID_FOR_MSG).append(pet.getOwnerId()).append("\n\n");
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
