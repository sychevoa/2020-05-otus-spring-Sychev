package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.config.AppConfig;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.PersonData;
import ru.otus.homework.domain.Question;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class QuestionServiceCsv implements QuestionService {

    @Value("${source.questions}")
    private String questionsCsv;

    @Value("${source.answers}")
    private String answersCsv;

    @Value("${locale}")
    private String locale;

    private final QuestionDao questionDao;
    private final AppConfig appConfig;
    private final MessageSource messageSource;

    @Override
    public void runQuiz() {
        PersonData personData = askAndReturnPersonData();
        List<Question> allQuestions = questionDao.getAllQuestions(questionsCsv);
        int result = showQuestionsAndCalculateResult(allQuestions);

        System.out.println(getFinalPhrase(result, personData));
    }

    private PersonData askAndReturnPersonData() {
        String askForFirstName = messageSource.getMessage("person.fist-name", null, new Locale(locale, locale));
        System.out.println(askForFirstName);
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();

        String askForSecondName = messageSource.getMessage("person.second-name", null, new Locale(locale, locale));
        System.out.println(askForSecondName);
        String lastName = scanner.nextLine();

        return new PersonData(firstName, lastName);
    }

    private int showQuestionsAndCalculateResult(List<Question> allQuestions) {
        Scanner scanner = new Scanner(System.in);
        AtomicInteger countOfAnswers = new AtomicInteger();

        List<Answer> allAnswers = questionDao.getAllAnswers(answersCsv);

        allQuestions.forEach(question -> {
            String questionTitle = messageSource.getMessage("question.title", null, new Locale(locale, locale));
            String answerTitle = messageSource.getMessage("answer.title", null, new Locale(locale, locale));
            System.out.println(String.format("%s %s \n%s %s", questionTitle, question.getText(), answerTitle, question.getPossibleAnswers()));

            String userAnswer = scanner.nextLine();
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
