package com.github.lostfly.corgihousetelegrambot.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByChatId(Long chatId);

    @Override
    default <S extends User> S save(S entity) {
        return null;
    }

    @Override
    default <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    default Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    default boolean existsById(Long aLong) {
        return false;
    }

    @Override
    default Iterable<User> findAll() {
        return null;
    }

    @Override
    default Iterable<User> findAllById(Iterable<Long> longs) {
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
    default void delete(User entity) {

    }

    @Override
    default void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    default void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    default void deleteAll() {

    }
}
