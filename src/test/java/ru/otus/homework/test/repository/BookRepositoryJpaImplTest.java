package ru.otus.homework.test.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepositoryJpa;
import ru.otus.homework.repository.BookRepositoryJpaImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Book jpa репозиторий должен: ")
@Import(BookRepositoryJpaImpl.class)
class BookRepositoryJpaImplTest {

    @Autowired
    private BookRepositoryJpa repo;

    @Autowired
    private TestEntityManager testManager;

    @Test
    @DisplayName("правильно считать количество книг в БД")
    void shouldReturnCorrectCountBooks() {
        long count = repo.countBooks();

        assertThat(count).isEqualTo(6);
    }

    @Test
    @DisplayName("добавлять новую книгу в БД")
    void shouldInsertBook() {
        Author author = new Author(10L, "John", "Connor");
        Genre genre = new Genre(10L, "fantastic");
        List<Comment> comments = List.of(new Comment(10L,"comment about book"));

        Book addedBook = new Book(7L, "How to cook Terminator",  author, genre, comments);
        repo.insertBook(addedBook);
        Book returnedBook = testManager.find(Book.class, 7L);

        assertThat(addedBook.getTitle()).isEqualTo(returnedBook.getTitle());
    }

    @Test
    @DisplayName("искать книгу по id")
    void shouldReturnExpectedBookById() {
        Optional<Book> actualBook = repo.getBookById(6L);
        Book expectedBook = testManager.find(Book.class, 6L);

        assertThat(actualBook).isPresent().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @Test
    @DisplayName("удалять книгу по id")
    void shouldDeleteBookById() {
        long countBeforeDelete = repo.countBooks();
        repo.deleteBookById(2L);
        long countAfterDelete = repo.countBooks();

        assertThat(countBeforeDelete).isEqualTo(countAfterDelete + 1);
    }

    @Test
    @DisplayName("удалять книгу по названию")
    void shouldDeleteBookByTitle() {
        String title = "War and Peace";
        Optional<Book> bookBeforeDelete = repo.getBookByTitle(title);
        repo.deleteBookByTitle(title);
        Optional<Book> bookAfterDelete = repo.getBookByTitle(title);

        assertThat(bookBeforeDelete).isPresent();
        assertThat(bookAfterDelete).isEmpty();
    }

    @Test
    @DisplayName("искать книгу по названию")
    void shouldReturnExpectedBookByTitle() {
        Optional<Book> optionalBook = repo.getBookByTitle("Jane Eyre");
        Author author = testManager.find(Author.class, 2L);

        assertThat(optionalBook).isPresent().get()
                .hasFieldOrPropertyWithValue("author", author);
    }

    @Test
    @DisplayName("возвращать все имеющиеся книги по жанру")
    void shouldReturnAllBooksByGenre() {
        List<Book> books = repo.getAllBooksByGenre("detective");
        Book detective = testManager.find(Book.class, 3L);

        assertThat(books).containsOnly(detective);
    }

    @Test
    @DisplayName("возвращать все имеющиеся книги")
    void shouldReturnAllBooks() {
        List<String> bookTitles = repo.getAllBooks()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        assertThat(bookTitles).contains("To Kill a Mockingbird", "Jane Eyre", "Sherlock Holmes",
                "Sapiens: A Brief History of Humankind", "War and Peace", "Anna Karenina");
    }
}