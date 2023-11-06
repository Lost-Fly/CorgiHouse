package com.github.lostfly.corgihousetelegrambot.model;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findByPetIdAndOwnerId(Long petId, Long ownerId);

    ArrayList<Pet> findAllByOwnerId(Long ownerId);

}


