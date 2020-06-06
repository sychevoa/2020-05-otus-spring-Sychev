package ru.otus.homework.dao;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.PersonData;
import ru.otus.homework.domain.Question;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QuestionDaoImpl implements QuestionDao {

    @Value("${source.questions}")
    private String questionsCsv;

    @Value("${source.answers}")
    private String answersCsv;

    @Value("${questions.toPass}")
    private int toPass;

    @Override
    public void runQuiz() {
        PersonData personData = askAndReturnPersonData();
        List<Question> allQuestions = getAllQuestions();
        int result = showQuestionsAndCalculateResult(allQuestions);

        System.out.println(personData + (result >= toPass ? ", you have passed the test!" : ", unfortunately you failed the test!"));
    }

    private PersonData askAndReturnPersonData() {
        System.out.println("What is you first name:");
        Scanner scanner = new Scanner(System.in);
        String firstName = scanner.nextLine();
        System.out.println("What is you last name:");
        String lastName = scanner.nextLine();

        return new PersonData(firstName, lastName);
    }

    private int showQuestionsAndCalculateResult(List<Question> allQuestions) {
        Scanner scanner = new Scanner(System.in);
        AtomicInteger countOfAnswers = new AtomicInteger();
        List<Answer> allAnswers = getAllAnswers();

        allQuestions.forEach(question -> {
            System.out.println(question);
            String userAnswer = scanner.nextLine();
            String correctAnswer = allAnswers.get(question.getId() - 1).getText();

            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                countOfAnswers.getAndIncrement();
            }
        });

        return countOfAnswers.get();
    }

    private List<Question> getAllQuestions() {
        Scanner scanner;
        List<Question> questions = new ArrayList<>();

        try {
            scanner = new Scanner(createFileFromResource(questionsCsv));

            while (scanner.hasNext()) {
                Question question = returnQuestionFromString(scanner.nextLine());
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questions;
    }

    private List<Answer> getAllAnswers() {
        Scanner scanner;
        List<Answer> answersList = new ArrayList<>();

        try {
            scanner = new Scanner(createFileFromResource(answersCsv));

            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] split = s.split(",");

                Answer answer = new Answer(Integer.parseInt(split[0]), split[1]);
                answersList.add(answer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answersList;
    }

    private File createFileFromResource(String fileName) throws IOException {
        File tempFile = File.createTempFile("-prefix", "-suffix");
        InputStream inputStream = new ClassPathResource(fileName).getInputStream();
        FileUtils.copyInputStreamToFile(inputStream, tempFile);

        return tempFile;
    }

    private Question returnQuestionFromString(String questionAsString) {
        String[] questionAsArray = questionAsString.split(",");

        int id = Integer.parseInt(questionAsArray[0]);
        String text = questionAsArray[1];

        Question question;
        if (questionAsArray.length > 2) {
            List<String> answers = List.of(Arrays.copyOfRange(questionAsArray, 2, questionAsArray.length));
            question = new Question(id, text, answers);
        } else {
            question = new Question(id, text);
        }
        return question;
    }
}
