package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PetRegistration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public void savePetToDB(long chatId) {
        if (petRepository.findById(chatId).isEmpty()) {

            Pet pet = new Pet();

            pet.setOwnerId(chatId);
            pet.setPetBreed("Корги");
            pet.setAnimalType("Собака");
            pet.setPetName("Булочка");

            var petId = petRepository.count();
            pet.setPetId(petId + 1);

            petRepository.save(pet);

            log.info("pet saved: " + pet);
        }
    }


    public String registerPet() {
        return "Раздел регистрации животных";
    }

}
