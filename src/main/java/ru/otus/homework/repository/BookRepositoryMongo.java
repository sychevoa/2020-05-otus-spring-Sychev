package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepositoryMongo extends MongoRepository<Book, String> {

    List<Book> findAllByTitle(String title);

    List<Book> findAllByGenre_Description(String title);

}
