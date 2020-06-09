package ru.otus.homework.test.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.dao.QuestionDaoCsv;
import ru.otus.homework.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Класс QuestionDao")
public class QuestionDaoCsvTest {

    private QuestionDao dao = new QuestionDaoCsv();

    private String questionsCsv = "questions.csv";
    private String answersCsv = "answers.csv";

    @Test
    @DisplayName("Правильное количество вопросов")
    public void correctQuestionsSize() {
        assertEquals(dao.getAllQuestions(questionsCsv).size(), 5);
    }

    @Test
    @DisplayName("Правильное количество ответов")
    public void correctAnswersSize() {
        assertEquals(dao.getAllAnswers(answersCsv).size(), 5);
    }

    @Test
    @DisplayName("Правильное количество вопросов")
    public void correctOneQuestion() {
        List<Question> allQuestions = dao.getAllQuestions(questionsCsv);

        assertTrue(allQuestions.get(0).getText().contains("Twitter"));
    }
}
