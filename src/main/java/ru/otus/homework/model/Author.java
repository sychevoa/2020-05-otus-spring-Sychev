package ru.otus.homework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Author {
    private final Long id;
    private final String firstName;
    private final String secondName;
}
