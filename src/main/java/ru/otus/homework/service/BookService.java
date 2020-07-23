package ru.otus.homework.service;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookService {
    long countAllBooks();

    String addBook();

    String getBookById(String id);

    String deleteBookByTitle(String title);

    String deleteBookById(String id);

    List<Book> getBookByTitle(String title);

    List<Book> getAllBooks();

    List<Book> getAllBooksByGenre(String id);
}
