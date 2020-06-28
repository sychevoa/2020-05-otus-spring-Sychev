package ru.otus.homework.test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.PersonData;
import ru.otus.homework.service.IOService;
import ru.otus.homework.service.PersonService;
import ru.otus.homework.service.QuestionServiceCsv;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("Класс QuestionServiceCsv")
@SpringBootTest()
public class QuestionServiceCsvTest {

    @MockBean
    private QuestionDao questionDao;

    @MockBean
    private AppConfig appConfig;

    @MockBean
    private MessageSource messageSource;

    @MockBean
    private PersonService personService;

    @MockBean
    private IOService ioService;

    @Autowired
    private QuestionServiceCsv questionServiceCsv;

    private final String questionsCsv = "questions.csv";

    @BeforeEach
    void setUp() {
        given(messageSource.getMessage(any(), any(), any())).willReturn("some text");
        given(personService.askAndReturnPersonData()).willReturn(new PersonData("Oleg", "Sychev"));
    }

    @Test
    @DisplayName("должен один раз запустить PersonService")
    public void shouldInvokePersonService(){
        questionServiceCsv.runQuiz();

        verify(personService, times(1)).askAndReturnPersonData();
    }

    @Test
    @DisplayName("должен два раза запустить questionDao")
    public void shouldInvokeQuestionDaoTwice(){
        questionServiceCsv.runQuiz();

        verify(questionDao, times(1)).getAllQuestions(any());
        verify(questionDao, times(1)).getAllAnswers(any());
    }

    @Test
    @DisplayName("должен один раз запустить ioService")
    public void shouldInvokeIOService(){
        questionServiceCsv.runQuiz();

        verify(ioService, times(1)).out(any());
    }
}
