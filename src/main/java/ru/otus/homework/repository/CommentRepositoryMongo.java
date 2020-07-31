package ru.otus.homework.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.homework.model.Comment;

import java.util.List;

public interface CommentRepositoryMongo extends MongoRepository<Comment, String> {
}
