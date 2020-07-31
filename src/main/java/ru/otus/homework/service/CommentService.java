package ru.otus.homework.service;

import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentService {
    Comment getCommentById(String id);

    String deleteCommentById(String id);

    String addComment(String bookId, String text);

    List<Comment> getAllComments();

    List<Comment> getCommentsByBookId(String bookId);
}
