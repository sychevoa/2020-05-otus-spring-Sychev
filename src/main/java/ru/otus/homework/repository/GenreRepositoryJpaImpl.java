package ru.otus.homework.repository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional
public class GenreRepositoryJpaImpl implements GenreRepositoryJpa {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Genre getGenreById(long id) {

        return manager.find(Genre.class, id);
    }
}
