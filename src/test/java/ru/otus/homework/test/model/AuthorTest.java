package ru.otus.homework.test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Author:")
public class AuthorTest {

    private final static String AUTHOR_FIRST_NAME = "Jane";
    private final static String AUTHOR_SECOND_NAME = "Austen";

    @Test
    @DisplayName("корректно создается конструктором")
    public void shouldHaveCorrectConstructor() {
        Author author = new Author(1L, AUTHOR_FIRST_NAME, AUTHOR_SECOND_NAME);

        assertThat(author).hasFieldOrPropertyWithValue("firstName", AUTHOR_FIRST_NAME)
                .hasFieldOrPropertyWithValue("secondName", AUTHOR_SECOND_NAME);
    }
}
