package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Comment;
import ru.otus.homework.repository.BookRepositoryJpa;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Optional;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceOnH2 implements BookService {

    private final BookRepositoryJpa repo;
    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Count books", key = "count")
    public long countAllBooks() {

        return repo.countBooks();
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add book", key = "add")
    public String insertBook() {
        Book book = askAttributesCreateAndGetBook();
        Book addedBook = repo.insertBook(book);

        return "Book " + addedBook.getTitle() + " added";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find book by id", key = "get book")
    public Book findBookById(long id) {
        Optional<Book> bookById = repo.getBookById(id);

        if (bookById.isEmpty()) {
            ioService.out("Book not found");
            return null;
        }
        return bookById.get();
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete book", key = "delete book")
    public String deleteBookByTitle(String title) {

        return repo.deleteBookByTitle(title) == 1 ? "Book deleted :'(" : "Book not found";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get book by title", key = "get title")
    public Book findBookByTitle(String title) {
        Optional<Book> bookByTitle = repo.getBookByTitle(title);

        if (bookByTitle.isEmpty()) {
            ioService.out("Book not found");
            return null;
        }
        return bookByTitle.get();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "All books", key = "all books")
    public List<Book> getAllBooks() {

        return repo.getAllBooks();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get books by genre", key = "all genre")
    public List<Book> getAllBooksByGenre(String genre) {

        return repo.getAllBooksByGenre(genre);
    }

    private Book askAttributesCreateAndGetBook() {
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

        Author author = new Author();
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        book.setAuthor(author);

        Genre genre = new Genre();
        genre.setDescription(genreDesc);
        book.setGenre(genre);

        Comment comment = new Comment();
        comment.setText(commentAsString);

        return book;
    }
}