package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;
import ru.otus.homework.repository.AuthorRepositoryJpa;
import ru.otus.homework.repository.BookRepositoryJpa;
import ru.otus.homework.repository.CommentRepositoryJpa;
import ru.otus.homework.repository.GenreRepositoryJpa;

import java.util.List;
import java.util.Optional;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceOnH2 implements BookService {

    private final BookRepositoryJpa bookRepo;
    private final AuthorRepositoryJpa authorRepo;
    private final GenreRepositoryJpa genreRepo;
    private final CommentRepositoryJpa commentRepo;
    private final IOService ioService;

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Count books", key = "count books")
    public long countAllBooks() {

        return bookRepo.count();
    }

    @Override
    @Transactional
    @ShellMethod(value = "Add book", key = "add book")
    public String addBook() {
        Book book = askAttributesCreateAndGetBook();
        Book addedBook = bookRepo.save(book);

        return "Book " + addedBook.getTitle() + " added";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find book by id", key = "get book")
    public String getBookById(long id) {
        Optional<Book> bookOptional = bookRepo.findById(id);

        if (bookOptional.isEmpty()) {
            ioService.out("Book not found");
            return null;
        }
        List<Comment> comments = commentRepo.findAllByBookId(id);

        return bookOptional.get() + ", comments: " + comments;
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete book", key = "delete book title")
    public String deleteBookByTitle(String title) {
        List<Book> booksByTitle = bookRepo.findAllByTitle(title);

        if (booksByTitle.isEmpty()) {
            return "Book not found";
        }

        int size = booksByTitle.size();

        if (size == 1) {
            bookRepo.deleteById(booksByTitle.get(0).getId());
        } else {
            ioService.out(String.format("Find several books: \n " +
                    "%s \n" +
                    "Please input id:", booksByTitle));
            long id = Long.parseLong(ioService.read());
            bookRepo.deleteById(id);
        }

        return "Book deleted :'(";
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete book", key = "delete book id")
    public String deleteBookById(long id) {
        if (!bookRepo.existsById(id)) {
            return "Book not found";
        }
        bookRepo.deleteById(id);

        return "Book deleted :'(";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get book by title", key = "get title")
    public List<Book> getBookByTitle(String title) {

        return bookRepo.findAllByTitle(title);
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "All books", key = "all books")
    public List<Book> getAllBooks() {

        return bookRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Get books by genre", key = "all genre")
    public List<Book> getAllBooksByGenre(String genre) {

        return bookRepo.findAllByGenre_Description(genre);
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
        authorRepo.save(author);
        book.setAuthor(author);

        Genre genre = new Genre();
        genre.setDescription(genreDesc);
        genreRepo.save(genre);
        book.setGenre(genre);

        Comment comment = new Comment();
        comment.setText(commentAsString);
        comment.setBook(book);
        commentRepo.save(comment);

        return book;
    }
}