package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepositoryJpaImpl implements CommentRepositoryJpa {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Comment> getCommentById(long id) {

        return Optional.ofNullable(manager.find(Comment.class, id));
    }

    @Override
    public int deleteCommentById(long id) {
        Query query = manager.createQuery("delete from Comment c where c.id = :id");
        query.setParameter("id", id);

        return query.executeUpdate();
    }

    @Override
    public List<Comment> allComment() {
        TypedQuery<Comment> query = manager.createQuery("select c from Comment c", Comment.class);

        return query.getResultList();
    }

    @Override
    public Optional<Comment> addComment(long bookId, String text) {
        Book book;
        book = manager.find(Book.class, bookId);

        if (book == null) {
            return Optional.empty();
        }

        List<Comment> comments = book.getComments();
        Comment comment = new Comment();
        comment.setText(text);

        manager.persist(comment);
        comments.add(comment);
        book.setComments(comments);

        manager.merge(book);
        return Optional.of(comment);
    }
}
