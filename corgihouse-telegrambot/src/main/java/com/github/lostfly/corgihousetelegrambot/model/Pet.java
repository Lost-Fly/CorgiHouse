package com.github.lostfly.corgihousetelegrambot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "petDataTable")
public class Pet {

    @Id
    private Long petId;

    private String petName;

    private String animalType;

    private String petBreed;

    private String petImageId;

    private Long ownerId;


    public String getPetImageId() {
        return petImageId;
    }

    public void setPetImageId(String petImageId) {
        this.petImageId = petImageId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }


    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }


    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", petName='" + petName + '\'' +
                ", animalType='" + animalType + '\'' +
                ", petBreed='" + petBreed + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", image='" + "no_show" + '\'' +
                '}';
    }

}
