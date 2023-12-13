package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.Pet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryPet.*;

public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findByPetIdAndOwnerId(Long petId, Long ownerId);

    ArrayList<Pet> findAllByOwnerId(Long ownerId);

    ArrayList<Pet> findAll();

    @Transactional
    void deleteByOwnerId(Long ownerId);

    @Transactional
    void deleteByOwnerIdAndPetId(Long ownerId, Long petId);


    @Query(FIND_TOP_BY_ORDER_BY_OWNER_ID_DESC)
    Long findTopByOrderByOwnerIdDesc(@Param(PARAM_OWNER_ID) Long ownerId);

    @Query(FIND_TOP_BY_ORDER_DESC)
    Long findTopByOrderDesc();

    @Modifying
    @Transactional
    @Query(SET_PET_NAME_BY_OWNER_ID_AND_PET_ID)
    void setPetNameByOwnerIdAndPetId(String petname, Long ownerId, Long petId);

    @Modifying
    @Transactional
    @Query(SET_ANIMAL_TYPE_BY_OWNER_ID_AND_PET_ID)
    void setAnimalTypeByOwnerIdAndPetId(String animaltype, Long ownerId, Long petId);

    @Modifying
    @Transactional
    @Query(SET_PET_BREED_BY_OWNER_ID_AND_PET_ID)
    void setPetBreedByOwnerIdAndPetId(String petbreed, Long ownerId, Long petId);


}


