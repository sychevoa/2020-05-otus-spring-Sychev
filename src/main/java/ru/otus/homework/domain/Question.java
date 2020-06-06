package ru.otus.homework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Question {
    private final int id;
    private final String text;
    private final List<String> possibleAnswers;

    @Override
    public String toString() {
        return "Question is: " + text
                + "\nPossible answers: " + possibleAnswers;
    }
}
