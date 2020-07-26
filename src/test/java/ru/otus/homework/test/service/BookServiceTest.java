package ru.otus.homework.test.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.service.BookService;
import ru.otus.homework.service.CreateBookServiceConsole;
import ru.otus.homework.service.IOService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@DataMongoTest
@ComponentScan({"ru.otus.homework.service", "ru.otus.homework.repository"})
@DisplayName("Book сервис должен: ")
public class BookServiceTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BookService bookservice;

    @MockBean
    private CreateBookServiceConsole createBookServiceMock;

    @MockBean
    private IOService ioServiceMock;

    @DisplayName("правильно добавлять новую книгу")
    @Test
    void correctAddBook() {
        when(createBookServiceMock.askAttributesCreateAndGetBook()).thenReturn(new Book("New book", new Author("No named", "author"),
                new Genre("genre"), List.of(new Comment("comment"))));

        assertThat(bookservice.addBook()).contains("New book");
    }

    @DisplayName("правильно удалять книгу по названию (если нашлась только одна)")
    @Test
    void correctDeleteOneBookByTitle() {
        verifyNoInteractions(ioServiceMock);
        assertThat(bookservice.deleteBookByTitle("War and Peace")).contains("War and Peace");
    }

    @DisplayName("правильно удалять книгу по названию (если нашлось больше одной)")
    @Test
    void correctDeleteSeveralBookByTitle() {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is("Sherlock Holmes"));
        List<Book> books = mongoTemplate.find(query, Book.class);

        when(ioServiceMock.read()).thenReturn(books.get(0).getId());
        assertThat(bookservice.deleteBookByTitle("Sherlock Holmes")).contains("Sherlock Holmes");
    }
}
