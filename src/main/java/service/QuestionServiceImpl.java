package service;

import domain.Question;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final String fileName;

    @Override
    public List<Question> getAllQuestionsFromCsv() {
        Scanner scanner;
        List<Question> questions = new ArrayList<>();

        try {
            scanner = new Scanner(createFileFromResource(fileName));

            while (scanner.hasNext()) {
                Question question = returnQuestionFromString(scanner.nextLine());
                questions.add(question);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
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
