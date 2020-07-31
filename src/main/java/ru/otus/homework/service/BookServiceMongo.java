package ru.otus.homework.service;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.homework.model.Book;
import ru.otus.homework.repository.BookRepositoryMongo;

import java.util.List;
import java.util.Optional;

@Service
@ShellComponent
@RequiredArgsConstructor
public class BookServiceMongo implements BookService {

    private final BookRepositoryMongo bookRepo;
    private final CreateBookServiceConsole createBookService;
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
        Book book = createBookService.askAttributesCreateAndGetBook();
        Book addedBook = bookRepo.save(book);

        return "Book " + addedBook.getTitle() + " added";
    }

    @Override
    @Transactional(readOnly = true)
    @ShellMethod(value = "Find book by id", key = "get book")
    public String getBookById(String id) {
        Optional<Book> bookOptional = bookRepo.findById(id);

        if (bookOptional.isEmpty()) {
            ioService.out("Book not found");
            return null;
        }
        return bookOptional.get().toString();
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
            String id = ioService.read();

            if (!bookRepo.existsById(id)) {
                return "Book not found";
            }
            bookRepo.deleteById(id);
        }

        return "Book" + title + "deleted :'(";
    }

    @Override
    @Transactional
    @ShellMethod(value = "Delete book", key = "delete book id")
    public String deleteBookById(String id) {
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
    public List<Book> getAllBooksByGenre(String description) {

        return bookRepo.findAllByGenre_Description(description);
    }
}