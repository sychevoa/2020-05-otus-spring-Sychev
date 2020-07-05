package ru.otus.homework.repository;

import ru.otus.homework.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryJpa {
    Optional<Comment> getCommentById(long id);

    int deleteCommentById(long id);

    List<Comment> allComment();

    Optional<Comment> addComment(long bookId, String text);
}
