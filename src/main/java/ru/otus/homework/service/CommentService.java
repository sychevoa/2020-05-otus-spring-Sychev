package ru.otus.homework.service;

import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentService {
    Comment getCommentById(long id);

    String deleteCommentById(long id);

    String addComment(long bookId, String text);

    List<Comment> getAllComments();

    List<Comment> getCommentsByBookId(long bookId);
}
