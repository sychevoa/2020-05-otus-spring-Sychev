package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.BookRepositoryJpa;
import ru.otus.homework.repository.CommentRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
@ShellComponent
@RequiredArgsConstructor
public class CommentServiceOnH2 implements CommentService {

    private final BookRepositoryJpa bookRepo;
    private final CommentRepositoryJpa commentRepo;
    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by id", key = "get comment id")
    public Comment getCommentById(long id) {
        Optional<Comment> commentById = commentRepo.findById(id);

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
        if (!commentRepo.existsById(id)) {
            return "Comment not found";
        }
        commentRepo.deleteById(id);

        return "Comment deleted";
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add comment", key = "add comment")
    public String addComment(long bookId, String text) {
        Optional<Book> bookOptional = bookRepo.findById(bookId);

        if (bookOptional.isEmpty()) {
            return "book to add comment not found";
        }

        Comment comment = new Comment();
        comment.setText(text);
        comment.setBook(bookOptional.get());

        Comment commentSaved = commentRepo.save(comment);
        return String.format("Comment with id: %s was added", commentSaved.getId());
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get all comments", key = "all comments")
    public List<Comment> getAllComments() {

        return commentRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get comment by bookId", key = "get comments bookId")
    public List<Comment> getCommentsByBookId(long bookId) {
        List<Comment> comments = commentRepo.findAllByBookId(bookId);

        if (comments.isEmpty()) {
            ioService.out("Comments not found");
            return null;
        }

        return comments;
    }
}
