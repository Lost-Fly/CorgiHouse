package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.github.lostfly.corgihousetelegrambot.constants.GlobalConstants.NO_PETS_TEXT;

@Slf4j
@Component
public class PetsFuncs {

    @Autowired
    private PetRepository petRepository;

    public String showPets(long chatId) {

        ArrayList<Pet> pets = petRepository.findAllByOwnerId(chatId);

        if (pets.isEmpty()) {
            return NO_PETS_TEXT;
        } else {
            StringBuilder pet_info_message = new StringBuilder();

            for (Pet pet : pets) {
                pet_info_message.append("ID животного: ").append(pet.getPetId()).append("\n")
                        .append("Имя животного: ").append(pet.getPetName()).append("\n")
                        .append("Тип животного: ").append(pet.getAnimalType()).append("\n")
                        .append("Порода животного: ").append(pet.getPetBreed()).append("\n")
                        .append("ID владельца: ").append(pet.getOwnerId()).append("\n\n");
            }

            return pet_info_message.toString();
        }
    }

}
