package ru.otus.homework.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Genre {
    private final Long id;
    private final String description;
}
