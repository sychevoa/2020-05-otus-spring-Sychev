package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.AuthorRepositoryMongo;
import ru.otus.homework.repository.CommentRepositoryMongo;
import ru.otus.homework.repository.GenreRepositoryMongo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateBookServiceConsole implements CreateBookService {

    private final AuthorRepositoryMongo authorRepo;
    private final GenreRepositoryMongo genreRepo;
    private final CommentRepositoryMongo commentRepo;
    private final IOService ioService;

    public Book askAttributesCreateAndGetBook() {
        Book book = new Book();

        ioService.out("Enter a book title");
        String title = ioService.read();
        ioService.out("Enter the first name of the author of book");
        String firstName = ioService.read();
        ioService.out("Enter the second name of the author of book");
        String secondName = ioService.read();
        ioService.out("Enter a book genre");
        String genreDesc = ioService.read();
        ioService.out("Enter a comment");
        String commentAsString = ioService.read();
        book.setTitle(title);

        book.setAuthor(checkIfExistsAndReturnAuthor(firstName, secondName));
        book.setGenre(checkIfExistsAndReturnGenre(genreDesc));

        Comment comment = new Comment();
        comment.setText(commentAsString);
        commentRepo.save(comment);
        book.setComments(List.of(comment));

        return book;
    }

    private Author checkIfExistsAndReturnAuthor(String firstName, String secondName) {
        Author newAuthor = new Author(firstName, secondName);
        Example<Author> authorExample = Example.of(newAuthor);

        return authorRepo
                .findOne(authorExample)
                .orElseGet(() -> authorRepo.save(newAuthor));
    }

    private Genre checkIfExistsAndReturnGenre(String genreDescription) {
        Genre newGenre = new Genre();
        newGenre.setDescription(genreDescription);
        Example<Genre> genreExample = Example.of(newGenre);

        return genreRepo
                .findOne(genreExample)
                .orElseGet(() -> genreRepo.save(newGenre));
    }
}