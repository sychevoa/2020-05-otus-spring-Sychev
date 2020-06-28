package ru.otus.homework.test.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoCsv;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Класс QuestionDao вычитывает")
public class QuestionDaoCsvTest {

    @Autowired
    QuestionDao dao;

    private final String questionsCsv = "questions.csv";
    private final String answersCsv = "answers.csv";

    @Test
    @DisplayName("правильное количество вопросов")
    public void correctQuestionsSize() {
        assertEquals(dao.getAllQuestions(questionsCsv).size(), 5);
    }

    @Test
    @DisplayName("правильное количество ответов")
    public void correctAnswersSize() {
        assertEquals(dao.getAllAnswers(answersCsv).size(), 5);
    }

    @Test
    @DisplayName("правильное содержание")
    public void correctOneQuestion() {
        List<Question> allQuestions = dao.getAllQuestions(questionsCsv);

        assertTrue(allQuestions.get(0).getText().contains("Twitter"));
    }
}
