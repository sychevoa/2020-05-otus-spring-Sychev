package ru.otus.homework.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PersonData {
    private final String firstName;
    private final String lastName;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
