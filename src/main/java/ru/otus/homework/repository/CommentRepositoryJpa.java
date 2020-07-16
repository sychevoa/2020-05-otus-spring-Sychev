package ru.otus.homework.repository;

import ru.otus.homework.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Optional<Comment> getCommentById(long id);

    List<Comment> getCommentsByBookId(long bookId);

    void deleteCommentById(long id);

    List<Comment> allComment();

    Comment addComment(Comment comment);
}
