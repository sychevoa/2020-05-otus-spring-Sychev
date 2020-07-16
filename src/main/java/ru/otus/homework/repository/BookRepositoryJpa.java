package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepositoryJpa extends JpaRepository<Book, Long> {

    List<Book> findAllByTitle(String title);

}
