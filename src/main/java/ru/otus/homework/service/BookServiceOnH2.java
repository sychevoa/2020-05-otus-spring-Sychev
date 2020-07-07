package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.exception.BookNotFoundException;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.BookRepositoryJpa;
import ru.otus.homework.repository.CommentRepositoryJpa;

import java.util.List;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceOnH2 implements BookService {

    private final BookRepositoryJpa bookRepo;
    private final CommentRepositoryJpa commentRepo;
    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Count books", key = "count")
    public long countAllBooks() {

        return bookRepo.countBooks();
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add book", key = "add")
    public String addBook() {
        Book book = askAttributesCreateAndGetBook();
        Book addedBook = bookRepo.addBook(book);

        return "Book " + addedBook.getTitle() + " added";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find book by id", key = "get book")
    public String getBookById(long id) {
        Book bookById = bookRepo.getBookById(id);

        if (bookById == null) {
            ioService.out("Book not found");
            return null;
        }
        List<Comment> comments = commentRepo.getCommentsByBookId(id);

        return bookById + ", comments: " + comments;
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete book", key = "delete book title")
    public String deleteBookByTitle(String title) {
        List<Book> booksByTitle = bookRepo.getBookByTitle(title);

        if (booksByTitle.isEmpty()) {
            return "Book not found";
        }

        int size = booksByTitle.size();

        if (size == 1) {
            bookRepo.deleteBookById(booksByTitle.get(0).getId());
        } else {
            ioService.out(String.format("Find several books: \n " +
                    "%s \n" +
                    "Please input id:", booksByTitle));
            long id = Long.parseLong(ioService.read());
            bookRepo.deleteBookById(id);
        }

        return "Book deleted :'(";
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete book", key = "delete book id")
    public String deleteBookById(long id) {
        try {
            bookRepo.deleteBookById(id);
        } catch (BookNotFoundException e) {
            return e.getMessage();
        }
        return "Book deleted :'(";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get book by title", key = "get title")
    public List<Book> getBookByTitle(String title) {

        return bookRepo.getBookByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "All books", key = "all books")
    public List<Book> getAllBooks() {

        return bookRepo.getAllBooks();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get books by genre", key = "all genre")
    public List<Book> getAllBooksByGenre(String genre) {

        return bookRepo.getAllBooksByGenre(genre);
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