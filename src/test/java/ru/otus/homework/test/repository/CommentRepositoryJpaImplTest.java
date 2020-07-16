package ru.otus.homework.test.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.CommentRepositoryJpa;
import ru.otus.homework.repository.CommentRepositoryJpaImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Comment jpa репозиторий должен: ")
@Import(CommentRepositoryJpaImpl.class)
public class CommentRepositoryJpaImplTest {

    @Autowired
    private CommentRepositoryJpa repo;

    @Autowired
    private TestEntityManager testManager;

    private final static String COMMENT = "new comment about book";
    private final static long BOOK_ID = 1L;

    @Test
    @DisplayName("искать комментарий по id")
    void shouldReturnExpectedCommentById() {
        Optional<Comment> actualComment = repo.getCommentById(5L);
        Comment expectedComment = testManager.find(Comment.class, 5L);

        assertThat(actualComment).isPresent().get()
                .isEqualToComparingFieldByField(expectedComment);
    }

    @Test
    @DisplayName("удалять комментарий по id")
    void shouldDeleteCommentById() {
        long countBeforeDelete = repo.allComment().size();
        repo.deleteCommentById(2L);
        long countAfterDelete = repo.allComment().size();

        assertThat(countBeforeDelete).isEqualTo(countAfterDelete + 1);
    }

    @Test
    @DisplayName("добавлять новый комментарий к книге")
    void shouldInsertComment() {
        Comment comment = new Comment();
        Book book = testManager.find(Book.class, BOOK_ID);
        comment.setText(COMMENT);
        comment.setBook(book);

        Comment returnedComment = repo.addComment(comment);

        assertThat(returnedComment.getBook())
                .isEqualToComparingFieldByField(book);
    }

    @Test
    @DisplayName("возвращать правильное количество комментариев из БД")
    void shouldReturnCorrectCountComment() {

        assertThat(repo.allComment()).hasSize(9);
    }

    @Test
    @DisplayName("возвращать возвращать комментарии по id книги")
    void shouldReturnExpectedCommentByBookId() {
        Book book = testManager.find(Book.class, BOOK_ID);

        List<Comment> commentsByBookId = repo.getCommentsByBookId(book.getId());
        List<String> collect = commentsByBookId
                .stream()
                .map(Comment::getText)
                .collect(Collectors.toList());

        assertThat(collect).hasSize(2).containsOnly("some cool comment", "some bad comment");
    }
}
