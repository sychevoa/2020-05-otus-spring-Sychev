package ru.otus.homework.repository;

import ru.otus.homework.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepositoryJpa {

    Book insertBook(Book newBook);

    Optional<Book> getBookById(long id);

    int deleteBookById(long id);

    Optional<Book> getBookByTitle(String title);

    int deleteBookByTitle(String title);

    List<Book> getAllBooksByGenre(String genre);

    long countBooks();

    List<Book> getAllBooks();

}
