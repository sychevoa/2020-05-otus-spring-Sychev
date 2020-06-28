package ru.otus.homework.service;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookService {
    int countAllBooks();

    String insertBook();

    String deleteBookByTitle(String title);

    Book findBookByTitle(String title);

    List<Book> getAllBooks();

    List<Book> getAllBooksByGenre(String genre);
}
