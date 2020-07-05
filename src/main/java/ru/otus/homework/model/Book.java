package ru.otus.homework.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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
    @ManyToOne(targetEntity = Author.class, cascade = CascadeType.ALL)
    private Author author;

    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.ALL)
    private Genre genre;

    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Book: " + title +
                ", author: " + author +
                ", genre: " + genre +
                ", comment: " + comments;
    }
}
