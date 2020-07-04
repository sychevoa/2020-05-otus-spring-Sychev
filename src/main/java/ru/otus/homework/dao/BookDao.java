package ru.otus.homework.dao;

import ru.otus.homework.model.Book;

import java.util.List;

public interface BookDao {
    Book getBookById(long id);

    int deleteBookById(long id);

    Book getBookByTitle(String title);

    int deleteBookByTitle(String title);

    int insertBook(Book newBook);

    List<Book> getAllBooksByGenre(String genre);

    int countBooks();

    List<Book> getAllBooks();
}
