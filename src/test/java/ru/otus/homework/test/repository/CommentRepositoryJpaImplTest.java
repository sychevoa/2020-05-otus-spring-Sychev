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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Comment jpa репозиторий должен: ")
@Import(CommentRepositoryJpaImpl.class)
public class CommentRepositoryJpaImplTest {

    @Autowired
    private CommentRepositoryJpa repo;

    @Autowired
    private TestEntityManager testManager;

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
        String commentText = "new comment about book";
        long bookId = 1L;

        repo.addComment(bookId, commentText);
        Book returnedBook = testManager.find(Book.class, bookId);

        assertThat(returnedBook.getComments())
                .filteredOn("text", commentText)
                .hasSize(1);
    }

    @Test
    @DisplayName("возвращать правильное количество комментариев из БД")
    void shouldReturnCorrectCountComment() {

        assertThat(repo.allComment()).hasSize(9);
    }

}
