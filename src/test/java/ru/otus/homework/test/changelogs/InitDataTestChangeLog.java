package ru.otus.homework.test.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepositoryMongo;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
@RequiredArgsConstructor
public class InitDataTestChangeLog {

    private static List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "sychevoa", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "sychevoa", runAlways = true)
    public void initTestDb() {
        createAndAddToList("To Kill a Mockingbird", "Harper", "Lee", "novel", "some comment");
        createAndAddToList("Jane Eyre", "Charlotte", "Bronte", "novel", "another comment");
        createAndAddToList("Sherlock Holmes", "Sir Arthur", "Conan Doyle", "detective", "comment");
        createAndAddToList("Sapiens: A Brief History of Humankind", "Yuval", "Noah Harari", "non-fiction", "wonderful book");
        createAndAddToList("War and Peace", "Leo", "Tolstoy", "novel", "no comment");
        createAndAddToList("Anna Karenina", "Leo", "Tolstoy", "novel", "no comment again");
        createAndAddToList("Sherlock Holmes", "Another Sir Arthur", "Conan Doyle", "detective", "some bad comment");
    }

    @ChangeSet(order = "002", id = "saveBooks", author = "sychevoa", runAlways = true)
    public void initBooks(BookRepositoryMongo repository) {
        books.forEach(repository::save);
    }

    private void createAndAddToList(String title, String authorFirstName, String authorSecondName, String genreDesc, String commentText) {
        Author author = new Author(authorFirstName, authorSecondName);
        Genre genre = new Genre(genreDesc);
        Comment comment = new Comment(commentText);
        books.add(new Book(title, author, genre, List.of(comment)));
    }
}
