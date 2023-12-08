package com.github.lostfly.corgihousetelegrambot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "petDataTable")
public class Pet {

    @Id
    private Long petId;

    private String petName;

    private String animalType;

    private String petBreed;

    private Long petImageId;

    private Long ownerId;


}
