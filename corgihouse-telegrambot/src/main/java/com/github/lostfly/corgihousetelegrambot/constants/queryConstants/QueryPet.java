package com.github.lostfly.corgihousetelegrambot.constants.queryConstants;

public class QueryPet {
    // SQL QUERY FOR PET MODEL
    public static final String ENTITY_NAME_PET = "petDataTable";
    public static final String FIND_TOP_BY_ORDER_BY_OWNER_ID_DESC = "select max(pet.petId) from petDataTable pet where pet.ownerId = :ownerId";
    public static final String PARAM_OWNER_ID = "ownerId";
    public static final String FIND_TOP_BY_ORDER_DESC = "select max(pet.petId) from petDataTable pet";
    public static final String SET_PET_NAME_BY_OWNER_ID_AND_PET_ID = "update petDataTable u set u.petName = ?1 where u.ownerId = ?2 and u.petId = ?3";
    public static final String SET_ANIMAL_TYPE_BY_OWNER_ID_AND_PET_ID = "update petDataTable u set u.animalType = ?1 where u.ownerId = ?2 and u.petId = ?3";
    public static final String SET_PET_BREED_BY_OWNER_ID_AND_PET_ID = "update petDataTable u set u.petBreed = ?1 where u.ownerId = ?2 and u.petId = ?3";

}
