package ru.otus.homework.test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Genre:")
public class GenreTest {

    private final static String GENRE = "some genre";

    @Test
    @DisplayName("корректно создается конструктором")
    public void shouldHaveCorrectConstructor() {
        Genre genre = new Genre(1L, GENRE);

        assertThat(genre).hasFieldOrPropertyWithValue("description", GENRE);
    }
}
