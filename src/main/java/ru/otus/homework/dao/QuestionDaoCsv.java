package ru.otus.homework.dao;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Answer;
import ru.otus.homework.domain.Question;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
public class QuestionDaoCsv implements QuestionDao {

    @Override
    public List<Question> getAllQuestions(String pathToResource) {
        List<Question> questions = new ArrayList<>();

        try (Stream<String> lines = Files.lines(createFileFromResource(pathToResource).toPath(), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                Question question = returnQuestionFromString(line);
                questions.add(question);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public List<Answer> getAllAnswers(String pathToResource) {
        List<Answer> answersList = new ArrayList<>();

        try (Stream<String> lines = Files.lines(createFileFromResource(pathToResource).toPath(), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                String[] split = line.split(",");
                Answer answer = new Answer(Integer.parseInt(split[0]), split[1]);
                answersList.add(answer);
            });
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
        List<String> answers = List.of(Arrays.copyOfRange(questionAsArray, 2, questionAsArray.length));
        question = new Question(id, text, answers);

        return question;
    }
}
