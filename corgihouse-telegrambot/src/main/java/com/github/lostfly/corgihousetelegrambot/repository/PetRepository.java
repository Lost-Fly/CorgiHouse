package com.github.lostfly.corgihousetelegrambot.repository;

import com.github.lostfly.corgihousetelegrambot.model.Pet;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findByPetIdAndOwnerId(Long petId, Long ownerId);

    ArrayList<Pet> findAllByOwnerId(Long ownerId);

    ArrayList<Pet> findAll();

    @Transactional
    void deleteByOwnerId(Long ownerId);

    @Transactional
    void deleteByOwnerIdAndPetId(Long ownerId, Long petId);


    @Query("select max(pet.petId) from petDataTable pet where pet.ownerId = :ownerId")
    Long findTopByOrderByOwnerIdDesc(@Param("ownerId") Long ownerId);

    @Query("select max(pet.petId) from petDataTable pet")
    Long findTopByOrderDesc();
    @Modifying
    @Transactional
    @Query("update petDataTable u set u.petName = ?1 where u.ownerId = ?2 and u.petId = ?3")
    void setPetNameByOwnerIdAndPetId(String petname, Long ownerId, Long petId);

    @Modifying
    @Transactional
    @Query("update petDataTable u set u.animalType = ?1 where u.ownerId = ?2 and u.petId = ?3")
    void setAnimalTypeByOwnerIdAndPetId(String animaltype, Long ownerId, Long petId);

    @Modifying
    @Transactional
    @Query("update petDataTable u set u.petBreed = ?1 where u.ownerId = ?2 and u.petId = ?3")
    void setPetBreedByOwnerIdAndPetId(String petbreed, Long ownerId, Long petId);


}


