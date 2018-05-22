CREATE DATABASE "FreeLibraryDB"
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
CREATE TABLE libUser (
	id BIGSERIAL PRIMARY KEY,
    login text NOT NULL,
	firstName text,
	lastName text,
	passwordHash text,
	uRole text
);

INSERT INTO libUser (login, firstname, lastName, passwordHash, uRole) VALUES
('mainAdmin', 'Таисия', 'Дорошенкова', '$2a$10$qcjvvglHZa3ASDdPKBYd4ODz1HEKT3k7iev.pBLG.WsHMz1kzXWRS', 'admin');

SELECT * FROM libUser;

CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
	firstName text,
	secondName text,
	lastName text,
	century int
);

INSERT INTO author (firstname, secondName, lastName, century) VALUES
('Фёдор', 'Михайлович', 'Достоевский', 19);

SELECT * FROM author;

CREATE TABLE genre (
    name text PRIMARY KEY
);

INSERT INTO genre (name) VALUES
('classic'),
('adventures'),
('detective'),
('fantasy');

INSERT INTO genre (name) VALUES
('poetry'),
('religion'),
('philosophy'),
('science'),
('computers'),
('sport'),
('cookery'),
('other');

DELETE FROM genre;

SELECT * FROM genre;


CREATE TABLE category (
    name text PRIMARY KEY
);

INSERT INTO category (name) VALUES
('adults'),
('teenagers'),
('children');

DELETE FROM category;
SELECT * FROM category;


CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
	name text,
	authorID int REFERENCES author(id),
	genreID text REFERENCES genre(name),
	categoryID text REFERENCES category(name),
	popularity int,
	description text
);

SELECT * FROM book;

CREATE TABLE notification (
    id BIGSERIAL PRIMARY KEY,
	user int REFERENCES libUser(id),
	notice text,
	notifDate date
);

SELECT * FROM notification;

CREATE TABLE uncheckedBook (
    id BIGSERIAL PRIMARY KEY,
	user int REFERENCES libUser(id),
	bookname text,
	author text,
	description text
);

SELECT * FROM uncheckedBook;