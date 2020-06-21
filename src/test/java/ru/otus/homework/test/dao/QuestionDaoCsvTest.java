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

    private String questionsCsv = "questions_en.csv";
    private String answersCsv = "answers_en.csv";

    @Test
    @DisplayName("Правильное количество вопросов")
    public void correctQuestionsSize() {
        QuestionDao dao = new QuestionDaoCsv();
        assertEquals(dao.getAllQuestions(questionsCsv).size(), 5);
    }

    @Test
    @DisplayName("Правильное количество ответов")
    public void correctAnswersSize() {
        QuestionDao dao = new QuestionDaoCsv();
        assertEquals(dao.getAllAnswers(answersCsv).size(), 5);
    }

    @Test
    @DisplayName("Правильное содержание")
    public void correctOneQuestion() {
        QuestionDao dao = new QuestionDaoCsv();
        List<Question> allQuestions = dao.getAllQuestions(questionsCsv);

        assertTrue(allQuestions.get(0).getText().contains("Twitter"));
    }
}
