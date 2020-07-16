package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.exception.BookNotFoundException;
import ru.otus.homework.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BookRepositoryJpaImpl implements BookRepositoryJpa {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Book getBookById(long id) {

        return manager.find(Book.class, id);
    }

    @Override
    public void deleteBookById(long id) {
        Book book = manager.find(Book.class, id);

        if (book == null)
            throw new BookNotFoundException("Book not found");

        manager.remove(book);
    }

    @Override
    public List<Book> getBookByTitle(String title) {
        TypedQuery<Book> query = manager.createQuery("select b from Book b where b.title = :title", Book.class);
        query.setParameter("title", title);

        return query.getResultList();
    }

    @Override
    public Book addBook(Book book) {
        if (book.getId() != null && book.getId() <= 0) {
            manager.persist(book);
            return book;
        } else {
            return manager.merge(book);
        }
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
