package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.PersonData;
import ru.otus.homework.domain.Question;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

@ShellComponent
@Service
public class QuestionServiceCsv implements QuestionService {

    private final String questionsCsv;
    private final String answersCsv;
    private final String locale;

    private final QuestionDao questionDao;
    private final AppConfig appConfig;
    private final MessageSource messageSource;
    private final PersonService personService;
    private final IOService ioService;

    public QuestionServiceCsv(@Value("${source.questions}") String questionsCsv, @Value("${source.answers}") String answersCsv,
                              @Value("${locale}") String locale, QuestionDao questionDao, AppConfig appConfig, MessageSource messageSource,
                              PersonService personService, IOService ioService) {
        this.questionsCsv = questionsCsv;
        this.answersCsv = answersCsv;
        this.locale = locale;
        this.questionDao = questionDao;
        this.appConfig = appConfig;
        this.messageSource = messageSource;
        this.personService = personService;
        this.ioService = ioService;
    }

    @ShellMethod("runQuiz")
    @Override
    public void runQuiz() {
        PersonData personData = personService.askAndReturnPersonData();
        List<Question> allQuestions = questionDao.getAllQuestions(questionsCsv);
        int result = showQuestionsAndCalculateResult(allQuestions);

        ioService.out(getFinalPhrase(result, personData));
    }

    private int showQuestionsAndCalculateResult(List<Question> allQuestions) {
        AtomicInteger countOfAnswers = new AtomicInteger();
        List<Answer> allAnswers = questionDao.getAllAnswers(answersCsv);

        allQuestions.forEach(question -> {
            String questionTitle = messageSource.getMessage("question.title", null, new Locale(locale, locale));
            String answerTitle = messageSource.getMessage("answer.title", null, new Locale(locale, locale));
            ioService.out(String.format("%s %s \n%s %s", questionTitle, question.getText(), answerTitle, question.getPossibleAnswers()));

            String userAnswer = ioService.readTextFromConsole();
            String correctAnswer = allAnswers.get(question.getId() - 1).getText();

            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                countOfAnswers.getAndIncrement();
            }
        });

        return countOfAnswers.get();
    }

    private String getFinalPhrase(int result, PersonData personData) {
        String finalPhrase;
        if (result >= appConfig.getToPass()) {
            finalPhrase = messageSource.getMessage("quiz.result-passed", new String[]{personData.toString()}, new Locale(locale, locale));
        } else {
            finalPhrase = messageSource.getMessage("quiz.result-failed", new String[]{personData.toString()}, new Locale(locale, locale));
        }
        return finalPhrase;
    }
}
