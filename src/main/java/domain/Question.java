package domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class Question {
    private final int id;
    private final String text;
    private List<String> possibleAnswers;

    public Question(int id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question is: " + text
                + (Objects.nonNull(possibleAnswers) ? "\nPossible answers: " + possibleAnswers : "");
    }
}
