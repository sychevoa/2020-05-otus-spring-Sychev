package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Genre;

import java.util.Optional;

public interface GenreRepositoryMongo extends MongoRepository<Genre, String> {
    Optional<Genre> findByDescription(String desciption);
}
