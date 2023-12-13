package com.github.lostfly.corgihousetelegrambot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.github.lostfly.corgihousetelegrambot.constants.queryConstants.QueryPet.ENTITY_NAME_PET;

@Getter
@Setter
@ToString
@Entity(name = ENTITY_NAME_PET)
public class Pet {

    @Id
    private Long petId;

    private String petName;

    private String animalType;

    private String petBreed;

    private String petImageId;

    private Long ownerId;


}
