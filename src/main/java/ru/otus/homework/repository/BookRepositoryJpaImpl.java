package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Book> getBookById(long id) {

        return Optional.ofNullable(manager.find(Book.class, id));
    }

    @Override
    public int deleteBookById(long id) {
        Query query = manager.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }


    @Override
    public Optional<Book> getBookByTitle(String title) {
        TypedQuery<Book> query = manager.createQuery("select b from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public int deleteBookByTitle(String title) {
        Query query = manager.createQuery("delete from Book b where b.title = :title");
        query.setParameter("title", title);

        return query.executeUpdate();
    }

    @Override
    public Book insertBook(Book book) {
        if (book.getId() != null && book.getId() <= 0) {
            manager.persist(book);
            return book;
        } else {
            return manager.merge(book);
        }
    }

    @Override
    public List<Book> getAllBooksByGenre(String genreAsString) {
        TypedQuery<Book> query = manager.createQuery("select b from Book b " +
                "join b.genre g where g.description = :genre", Book.class);
        query.setParameter("genre", genreAsString);

        return query.getResultList();
    }

    @Override
    public long countBooks() {
        TypedQuery<Long> query = manager.createQuery("select count (b) from Book b", Long.class);

        return query.getSingleResult();
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> query = manager.createQuery("select b from Book b", Book.class);

        return query.getResultList();
    }
}
