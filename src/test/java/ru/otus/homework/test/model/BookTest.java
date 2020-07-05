package ru.otus.homework.test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Book:")
public class BookTest {

    private final static String BOOK_TITLE = "Pride and Prejudice";

    @Test
    @DisplayName("корректно создается конструктором")
    public void shouldHaveCorrectConstructor() {
        Author author = new Author(1L, "Jane", "Austen");
        Genre genre = new Genre(1L, "novel");
        Comment comment = new Comment(1L, "text");

        Book book = new Book(1L, BOOK_TITLE, author, genre, List.of(comment));

        assertThat(book).hasFieldOrPropertyWithValue("title", BOOK_TITLE);
    }
}
