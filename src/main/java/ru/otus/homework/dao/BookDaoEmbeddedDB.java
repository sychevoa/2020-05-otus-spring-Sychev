package ru.otus.homework.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.homework.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class BookDaoEmbeddedDB implements BookDao {

    private final BookMapper bookMapper = new BookMapper();
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Book getBookById(long id) {
        String query = "SELECT books.id, books.title, authors.first_name, authors.second_name, genres.description " +
                "FROM BOOKS JOIN genres ON books.genre_id = genres.id JOIN authors ON books.author_id = authors.id where books.id = :id";

        return jdbcTemplate.queryForObject(query, Map.of("id", id), bookMapper);
    }

    @Override
    public int deleteBookById(long id) {
        String query = "delete from books where id = :id";

        return jdbcTemplate.update(query, Map.of("id", id));
    }

    @Override
    public Book getBookByTitle(String title) {
        String query = "SELECT books.id, books.title, authors.first_name, authors.second_name, genres.description " +
                "FROM BOOKS JOIN genres ON books.genre_id = genres.id JOIN authors ON books.author_id = authors.id where books.title = :title";

        Book book;
        try {
            book = jdbcTemplate.queryForObject(query, Map.of("title", title), bookMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return book;
    }

    @Override
    public int deleteBookByTitle(String title) {
        String query = "delete from books where title = :title";

        return jdbcTemplate.update(query, Map.of("title", title));
    }

    @Override
    public int insertBook(Book book) {
        Long authorId = getAuthorId(book);
        Long genreId = getGenreId(book);

        String query = "insert into books (`title`, author_id, genre_id) values (:title, :authorId, :genreId)";

        return jdbcTemplate.update(query, Map.of("title", book.getTitle(), "authorId", authorId, "genreId", genreId));
    }

    private Long getGenreId(Book book) {
        String getGenre = "select id from genres where upper(description) = :genre";
        Long genreId;

        String bookGenre = book.getGenre();

        try {
            genreId = jdbcTemplate.queryForObject(getGenre, Map.of("genre", bookGenre.toUpperCase()), Long.class);
        } catch (EmptyResultDataAccessException e) {
            genreId = insertNewGenreAndReturnId(bookGenre);
        }

        return genreId;
    }

    private Long insertNewGenreAndReturnId(String bookGenre) {
        String insertGenre = "insert into genres (`description`) values (:genre)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("genre", bookGenre);

        jdbcTemplate.update(insertGenre, parameters, keyHolder, new String[]{"ID"});

        return keyHolder.getKey().longValue();
    }

    private Long getAuthorId(Book book) {
        String getAuthors = "select id from authors where upper(first_name) = :firstName AND upper(second_name) = :secondName";
        Long authorId;

        String firstName = book.getAuthorFirstName();
        String secondName = book.getAuthorSecondName();

        try {
            authorId = jdbcTemplate.queryForObject(getAuthors, Map.of("firstName", firstName.toUpperCase(),
                    "secondName", secondName.toUpperCase()), Long.class);
        } catch (EmptyResultDataAccessException e) {
            authorId = insertNewAuthorAndGetId(firstName, secondName);
        }

        return authorId;
    }

    private Long insertNewAuthorAndGetId(String firstName, String secondName) {
        String insertAuthor = "insert into authors (`first_name`, `second_name`) values (:firstName, :secondName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("firstName", firstName)
                .addValue("secondName", secondName);

        jdbcTemplate.update(insertAuthor, parameters, keyHolder, new String[]{"ID"});

        return keyHolder.getKey().longValue();
    }

    @Override
    public List<Book> getAllBooksByGenre(String genre) {
        String query = "SELECT books.id, books.title, authors.first_name, authors.second_name, genres.description " +
                "FROM BOOKS JOIN genres ON books.genre_id = genres.id JOIN authors ON books.author_id = authors.id where genres.description = :genre";

        return jdbcTemplate.query(query, Map.of("genre", genre), bookMapper);
    }

    @Override
    public int countBooks() {
        String query = "select count(*) from books";

        return jdbcTemplate.queryForObject(query, Map.of(), Integer.class);
    }

    @Override
    public List<Book> getAllBooks() {
        String query = "SELECT books.id, books.title, authors.first_name, authors.second_name, genres.description " +
                "FROM BOOKS JOIN genres ON books.genre_id = genres.id JOIN authors ON books.author_id = authors.id";

        return jdbcTemplate.query(query, Map.of(), bookMapper);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            String authorFirstName = resultSet.getString("authors.first_name");
            String authorSecondName = resultSet.getString("authors.second_name");
            String genre = resultSet.getString("genres.description");

            return new Book(id, title, authorFirstName, authorSecondName, genre);
        }
    }
}
