package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import ru.otus.homework.dao.BookDao;
import ru.otus.homework.model.Book;

import java.util.List;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceOnH2 implements BookService {

    private final BookDao dao;
    private final IOService ioService;

    @Override
    @ShellMethod(value = "Count books", key = "count")
    public int countAllBooks() {

        return dao.countBooks();
    }

    @Override
    @ShellMethod(value = "Add book", key = "add")
    public String insertBook() {
        Book book = askAttributesCreateAndGetBook();

        return dao.insertBook(book) == 1 ? "Book added!" : "An error has occurred";
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
        String genre = ioService.read();
        book.setTitle(title);
        book.setAuthorFirstName(firstName);
        book.setAuthorSecondName(secondName);
        book.setGenre(genre);

        return book;
    }

    @Override
    @ShellMethod(value = "Delete book", key = "delete")
    public String deleteBookByTitle(String title) {

        return dao.deleteBookByTitle(title) == 1 ? "Book deleted :'(" : "Book not found";
    }

    @Override
    @ShellMethod(value = "Get book by title", key = "get title")
    public Book findBookByTitle(String title) {
        Book book = dao.getBookByTitle(title);

        if (book == null) {
            ioService.out("Book not found");
        }
        return book;
    }

    @Override
    @ShellMethod(value = "All books", key = "all")
    public List<Book> getAllBooks() {

        return dao.getAllBooks();
    }

    @Override
    @ShellMethod(value = "Get books by genre", key = "all genres")
    public List<Book> getAllBooksByGenre(String genre) {

        return dao.getAllBooksByGenre(genre);
    }
}
