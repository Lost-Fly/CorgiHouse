package com.github.lostfly.corgihousetelegrambot.model;

import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;


public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findByPetIdAndOwnerId(Long petId, Long ownerId);

    ArrayList<Pet> findAllByOwnerId(Long ownerId);

    @Override
    default <S extends Pet> S save(S entity) {
        return null;
    }

    @Override
    default <S extends Pet> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    default Optional<Pet> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    default boolean existsById(Long aLong) {
        return false;
    }

    @Override
    default Iterable<Pet> findAll() {
        return null;
    }

    @Override
    default Iterable<Pet> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    default long count() {
        return 0;
    }

    @Override
    default void deleteById(Long aLong) {

    }

    @Override
    default void delete(Pet entity) {

    }

    @Override
    default void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    default void deleteAll(Iterable<? extends Pet> entities) {

    }

    @Override
    default void deleteAll() {

    }
}


