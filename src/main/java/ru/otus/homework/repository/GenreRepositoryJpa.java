package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Genre;

public interface GenreRepositoryJpa extends JpaRepository<Genre, Long> {
}
