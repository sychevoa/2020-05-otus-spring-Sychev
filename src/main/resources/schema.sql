DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS books;

create table authors(
    id bigserial,
    first_name varchar(255),
    second_name varchar(255),
    primary key (id)
);

create table genres(
    id bigserial,
    description varchar(255),
    primary key (id)
);

create table books(
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id),
    genre_id bigint references genres (id),
    primary key (id)
);

create table comments(
    id bigserial,
    text varchar(255),
    book_id bigint references books(id) on delete cascade,
    primary key (id)
);