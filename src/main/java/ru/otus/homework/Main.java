package ru.otus.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.homework.service.QuestionService;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        var context = SpringApplication.run(Main.class);
        QuestionService service = context.getBean(QuestionService.class);
        service.runQuiz();
    }
}
