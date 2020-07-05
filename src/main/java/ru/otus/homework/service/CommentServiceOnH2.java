package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.CommentRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
@ShellComponent
@RequiredArgsConstructor
public class CommentServiceOnH2 implements CommentService {

    private final CommentRepositoryJpa repo;
    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by id", key = "get comment")
    public Comment findCommentById(long id) {
        Optional<Comment> commentById = repo.getCommentById(id);

        if (commentById.isEmpty()) {
            ioService.out("Comment not found");
            return null;
        }
        return commentById.get();
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete comment by id", key = "delete comment")
    public String deleteCommentById(long id) {

        return repo.deleteCommentById(id) == 1 ? "Comment deleted" : "Comment not found";
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add comment", key = "add comment")
    public String addComment(long bookId, String text) {
        Optional<Comment> optionalComment = repo.addComment(bookId, text);

        if (optionalComment.isEmpty()) {
            return "book to add comment not found";
        } else
            return String.format("Comment with id: %s was added", optionalComment.get().getId());
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get all comments", key = "all comments")
    public List<Comment> allComments() {

        return repo.allComment();
    }
}
