package ru.otus.homework.test.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс Comment:")
public class CommentTest {

    private final static String COMMENT = "some comment";

    @Test
    @DisplayName("корректно создается конструктором")
    public void shouldHaveCorrectConstructor() {
        Comment comment = new Comment(1L, COMMENT);

        assertThat(comment).hasFieldOrPropertyWithValue("text", COMMENT);
    }
}
