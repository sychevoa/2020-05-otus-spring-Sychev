package ru.otus.homework.test.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepositoryJpa;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Book jpa репозиторий должен: ")
class BookRepositoryJpaImplTest {

    @Autowired
    private BookRepositoryJpa repo;

    @Autowired
    private TestEntityManager testManager;

    @Test
    @DisplayName("правильно считать количество книг в БД")
    void shouldReturnCorrectCountBooks() {
        long count = repo.count();

        assertThat(count).isEqualTo(6);
    }

    @Test
    @DisplayName("добавлять новую книгу в БД")
    void shouldInsertBook() {
        Author author = new Author();
        author.setFirstName("John");
        author.setSecondName("Connor");
        testManager.persist(author);

        Genre genre = new Genre();
        genre.setDescription("fantastic");
        testManager.persist(genre);

        Book addedBook = new Book();
        addedBook.setTitle("How to cook Terminator");
        addedBook.setAuthor(author);
        addedBook.setGenre(genre);

        Book book = repo.save(addedBook);
        Book returnedBook = testManager.find(Book.class, book.getId());

        assertThat(addedBook.getTitle()).isEqualTo(returnedBook.getTitle());
    }

    @Test
    @DisplayName("искать книгу по id")
    void shouldReturnExpectedBookById() {
        Optional<Book> byId = repo.findById(6L);
        Book expectedBook = testManager.find(Book.class, 6L);

        assertThat(byId).isNotNull().get()
                .isEqualToComparingFieldByField(expectedBook);
    }

    @Test
    @DisplayName("удалять книгу по id")
    void shouldDeleteBookById() {
        long countBeforeDelete = repo.count();
        repo.deleteById(2L);
        long countAfterDelete = repo.count();

        assertThat(countBeforeDelete).isEqualTo(countAfterDelete + 1);
    }

    @Test
    @DisplayName("искать книгу по названию")
    void shouldReturnExpectedBookByTitle() {
        List<Book> books = repo.findAllByTitle("Jane Eyre");
        Author author = testManager.find(Author.class, 2L);

        assertThat(books.get(0)).hasFieldOrPropertyWithValue("author", author);
    }

    @Test
    @DisplayName("возвращать все имеющиеся книги")
    void shouldReturnAllBooks() {
        List<String> bookTitles = repo.findAll()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        assertThat(bookTitles).contains("To Kill a Mockingbird", "Jane Eyre", "Sherlock Holmes",
                "Sapiens: A Brief History of Humankind", "War and Peace", "Anna Karenina");
    }
}