package ru.otus.homework.test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Comment:")
public class CommentTest {

    private final static String COMMENT = "some comment";
    private final static String BOOK_TITLE = "Pride and Prejudice";

    @Test
    @DisplayName("корректно создается конструктором")
    public void shouldHaveCorrectConstructor() {
        Author author = new Author(1L, "Jane", "Austen");
        Genre genre = new Genre(1L, "novel", List.of(new Book()));

        Book book = new Book(1L, BOOK_TITLE, author, genre);
        Comment comment = new Comment(1L, COMMENT, book);

        assertThat(comment).hasFieldOrPropertyWithValue("text", COMMENT);
    }
}
