package ru.otus.homework.test.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Domain")
public class DomainTest {

    @Test
    @DisplayName("Корректно создается конструктором")
    public void shouldHaveCorrectConstructor() {
        Question question = new Question(1, "Some question", List.of("answer one", "answer two"));

        assertEquals(1, question.getId());
        assertEquals("Some question", question.getText());
    }
}
