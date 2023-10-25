package com.github.lostfly.corgihousetelegrambot.service;

import com.github.lostfly.corgihousetelegrambot.model.Pet;
import com.github.lostfly.corgihousetelegrambot.model.PetRepository;
import com.github.lostfly.corgihousetelegrambot.model.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

@Slf4j
@Component
public class PetRegistration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    public void registerPet(Message message) {
        if (petRepository.findById(message.getChatId()).isEmpty()){
            var chatId = message.getChatId();
            Pet pet = new Pet();

            pet.setOwnerId(chatId);
            pet.setPetBreed("Корги");
            pet.setAnimalType("Собака");
            pet.setPetName("Булочка");

            var petId = petRepository.count();
            System.out.println(petId);
            pet.setPetId(petId + 1);

            petRepository.save(pet);

            log.info("pet saved: " + pet);
            System.out.println("pet saved: " + pet);
        }

    }

}
