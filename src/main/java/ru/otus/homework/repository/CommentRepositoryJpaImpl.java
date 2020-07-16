package ru.otus.homework.repository;

import org.springframework.stereotype.Repository;
import ru.otus.homework.exception.CommentNotFoundException;
import ru.otus.homework.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public List<Comment> getCommentsByBookId(long bookId) {
        TypedQuery<Comment> query = manager.createQuery("select c from Comment c " +
                "where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);

        return query.getResultList();
    }

    @Override
    public void deleteCommentById(long id) {
        Comment comment = manager.find(Comment.class, id);

        if (comment == null)
            throw new CommentNotFoundException("Comment not found");

        manager.remove(comment);
    }

    @Override
    public List<Comment> allComment() {
        TypedQuery<Comment> query = manager.createQuery("select c from Comment c", Comment.class);

        return query.getResultList();
    }

    @Override
    public Comment addComment(Comment comment) {
        if (comment.getId() != null && comment.getId() <= 0) {
            manager.persist(comment);
            return comment;
        } else {
            return manager.merge(comment);
        }
    }
}
