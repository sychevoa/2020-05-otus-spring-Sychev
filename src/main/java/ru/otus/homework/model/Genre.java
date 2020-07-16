package ru.otus.homework.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @OneToMany(targetEntity = Book.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private List<Book> books;

    @Override
    public String toString() {
        return description;
    }
}
