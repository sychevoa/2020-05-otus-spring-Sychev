package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.BookRepositoryMongo;
import ru.otus.homework.repository.CommentRepositoryMongo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@ShellComponent
@RequiredArgsConstructor
public class CommentServiceMongo implements CommentService {

    private final BookRepositoryMongo bookRepo;
    private final CommentRepositoryMongo commentRepo;
    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find comment by id", key = "get comment id")
    public Comment getCommentById(String id) {
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
    public String deleteCommentById(String id) {
        if (!commentRepo.existsById(id)) {
            return "Comment not found";
        }
        commentRepo.deleteById(id);

        return "Comment deleted";
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add comment", key = "add comment")
    public String addComment(String bookId, String text) {
        Optional<Book> bookOptional = bookRepo.findById(bookId);

        if (bookOptional.isEmpty()) {
            return "book to add comment not found";
        }

        Comment comment = new Comment();
        comment.setText(text);

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
    public List<Comment> getCommentsByBookId(String bookId) {
        Optional<Book> book = bookRepo.findById(bookId);

        if (book.isEmpty()) {
            ioService.out("Book not found");
            return Collections.emptyList();
        }

        return book.get().getComments();
    }
}
