package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Author.class)
    private Author author;

    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Genre.class)
    private Genre genre;

    @Override
    public String toString() {
        return "Book(" + id + "): " + title +
                ", author: " + author +
                ", genre: " + genre;
    }
}
