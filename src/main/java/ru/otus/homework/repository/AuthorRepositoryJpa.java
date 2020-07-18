package ru.otus.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.homework.model.Author;

public interface AuthorRepositoryJpa extends JpaRepository<Author, Long> {
}
