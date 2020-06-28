package ru.otus.homework.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Book {
    private Long id;
    private String title;
    private String authorFirstName;
    private String authorSecondName;
    private String genre;

    @Override
    public String toString() {
        return "Book: " + title +
                ", author: " + authorFirstName + " " + authorSecondName +
                ", genre: " + genre;
    }
}
