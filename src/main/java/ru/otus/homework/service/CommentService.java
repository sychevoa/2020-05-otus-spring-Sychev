package ru.otus.homework.service;

import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentService {
    Comment findCommentById(long id);

    String deleteCommentById(long id);

    String addComment(long bookId, String text);

    List<Comment> allComments();
}
