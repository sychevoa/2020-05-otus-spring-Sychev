package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Author;

public interface AuthorRepositoryMongo extends MongoRepository<Author, String> {
}
