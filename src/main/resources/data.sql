insert into authors (`first_name`, `second_name`) values ('Leo', 'Tolstoy');
insert into authors (`first_name`, `second_name`) values ('Charlotte', 'Bronte');
insert into authors (`first_name`, `second_name`) values ('Sir Arthur', 'Conan Doyle');
insert into authors (`first_name`, `second_name`) values ('Yuval', 'Noah Harari');
insert into authors (`first_name`, `second_name`) values ('Harper', 'Lee');

insert into genres (`description`) values ('novel');
insert into genres (`description`) values ('detective');
insert into genres (`description`) values ('non-fiction');

insert into books (`title`, author_id, genre_id) values ('To Kill a Mockingbird', 5, 1);
insert into books (`title`, author_id, genre_id) values ('Jane Eyre', 2, 1);
insert into books (`title`, author_id, genre_id) values ('Sherlock Holmes', 3, 2);
insert into books (`title`, author_id, genre_id) values ('Sapiens: A Brief History of Humankind', 4, 3);
insert into books (`title`, author_id, genre_id) values ('War and Peace', 1, 1);
insert into books (`title`, author_id, genre_id) values ('Anna Karenina', 1, 1);