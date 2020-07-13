package ru.otus.homework.repository;


import ru.otus.homework.model.Genre;

public interface GenreRepositoryJpa {

    Genre getGenreById(long id);
}
