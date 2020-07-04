package ru.otus.homework.test.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.dao.BookDaoEmbeddedDB;
import ru.otus.homework.model.Book;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("Класс dao должен: ")
@Import(BookDaoEmbeddedDB.class)
class BookDaoEmbeddedDBTest {

    @Autowired
    private BookDao dao;

    @Test
    @DisplayName("правильно считать количество книг в БД")
    void shouldReturnCorrectCountBooks() {
        int count = dao.countBooks();

        assertThat(count).isEqualTo(6);
    }

    @Test
    @DisplayName("добавлять новую книгу в БД")
    void shouldInsertBook() {
        Book addedBook = new Book(7L, "How to cook Terminator", "John", "Connor", "fantastic");
        dao.insertBook(addedBook);

        assertThat(dao.getBookById(7L)).isEqualTo(addedBook);
    }

    @DisplayName("искать книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        Book book = dao.getBookById(6L);

        assertThat(book).hasFieldOrPropertyWithValue("title", "Anna Karenina");
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldDeleteBookById() {
        int countBeforeDelete = dao.countBooks();
        dao.deleteBookById(2L);
        int countAfterDelete = dao.countBooks();

        assertThat(countBeforeDelete).isEqualTo(countAfterDelete + 1);
    }

    @DisplayName("искать книгу по названию")
    @Test
    void shouldReturnExpectedBookByTitle() {
        Book book = dao.getBookByTitle("Jane Eyre");

        assertThat(book).hasFieldOrPropertyWithValue("id", 2L);
    }

    @DisplayName("возвращать все имеющиеся книги по жанру")
    @Test
    void shouldReturnAllBooksByGenre() {
        List<Book> books = dao.getAllBooksByGenre("detective");
        Book detective = new Book(3L, "Sherlock Holmes", "Sir Arthur", "Conan Doyle", "detective");

        assertThat(books).containsOnly(detective);
    }

    @DisplayName("возвращать все имеющиеся книги")
    @Test
    void shouldReturnAllBooks() {
        List<String> bookTitles = dao.getAllBooks()
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());

        assertThat(bookTitles).contains("To Kill a Mockingbird", "Jane Eyre", "Sherlock Holmes",
                "Sapiens: A Brief History of Humankind", "War and Peace", "Anna Karenina");
    }
}