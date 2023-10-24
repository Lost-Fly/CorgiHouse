package com.github.lostfly.corgihousetelegrambot.model;

import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;


public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findByPetIdAndOwnerId(Long petId, Long ownerId);

    ArrayList<Pet> findAllByOwnerId(Long ownerId);



}


