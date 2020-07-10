package ru.otus.homework.repository;

import ru.otus.homework.exception.BookNotFoundException;
import ru.otus.homework.model.Book;

import java.util.List;

public interface BookRepositoryJpa {

    Book addBook(Book newBook);

    Book getBookById(long id);

    void deleteBookById(long id) throws BookNotFoundException;

    List<Book> getBookByTitle(String title);

    long countBooks();

    List<Book> getAllBooks();

}
