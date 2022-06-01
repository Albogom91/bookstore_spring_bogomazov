DROP TABLE IF EXISTS orderitems;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS books;

TRUNCATE books CASCADE;
TRUNCATE users CASCADE;
TRUNCATE orderitems CASCADE;
TRUNCATE orders CASCADE;

CREATE TABLE books (
	id BIGSERIAL PRIMARY KEY,
	isbn CHAR(17) UNIQUE NOT NULL,
	title VARCHAR(100) NOT NULL,
	author VARCHAR(100),
	price DECIMAL(7,2) DEFAULT 0.0 NOT NULL,
	cover_id BIGINT NOT NULL,
	deleted BOOLEAN DEFAULT false NOT NULL
);

CREATE TABLE users (
	id BIGSERIAL PRIMARY KEY,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	userpassword VARCHAR(100) NOT NULL,
	role_id BIGINT NOT NULL,
	deleted BOOLEAN DEFAULT false NOT NULL
);

INSERT INTO books (isbn, title, author, price, cover_id)
VALUES ('901-1-13-141401-0', 'Pride and Prejudice', 'Jane Austen', 10.75, 0),
	('902-2-13-141401-9', 'To Kill a Mockingbird', 'Harper Lee', 9.25, 1),
	('903-3-13-141401-8', 'The Great Gatsby', 'F. Scott Fitzgerald', 11.55, 2),
	('904-4-13-141401-7', 'One Hundred Years of Solitude', 'Gabriel Garcia Marquez', 17.35, 2),
	('905-5-13-141401-6', 'In Cold Blood', 'Truman Capote', 9.45, 1),
	('906-6-13-141401-5', 'Wide Sargasso Sea', 'Jean Rhys', 5.75, 0),
	('907-7-13-141401-4', 'Brave New World', 'Aldous Huxley', 19.25, 1),
	('908-8-13-141401-3', 'I Capture The Castle', 'Dodie Smith', 7.75, 0),
	('909-9-13-141401-2', 'Jane Eyre', 'Charlotte Bronte', 8.75, 0),
	('910-0-13-141401-1', 'Crime and Punishment', 'Fyodor Dostoevsky', 18.55, 2),
	('911-1-13-141401-0', 'The Secret History', 'Donna Tartt', 17.55, 2),
	('912-2-13-141401-9', 'The Call of the Wild', 'Jack London', 14.25, 1),
	('913-3-13-141401-8', 'The Brothers Karamazov', 'Fyodor Dostoevsky', 15.35, 1),
	('914-4-13-141401-7', 'War and Peace', 'Leo Tolstoy', 9.15, 0),
	('915-5-13-141401-6', 'Anna Karenina', 'Leo Tolstoy', 15.25, 1),
	('916-6-13-141401-5', 'Nineteen Eighty-Four', 'George Orwell', 18.25, 1),
	('917-7-13-141401-4', 'The Master and Margarita', 'Mikhail Bulgakov', 11.25, 1),
	('918-8-13-141401-3', 'Frankenstein', 'Mary Shelley', 7.75, 0),
	('919-9-13-141401-2', 'To the Lighthouse', 'Virginia Woolf', 13.95, 1),
	('920-0-13-141401-1', 'Moby-Dick', 'Herman Melville', 19.95, 2);

INSERT INTO users (firstname, lastname, email, userpassword, role_id)
VALUES ('Alex', 'Baker', 'test1111@gmail.com', '1111', 0),
	('John', 'Carpenter', 'test2222@gmail.com', '2222', 1),
	('James', 'Smith', 'test3333@gmail.com', '3333', 2),
	('John', 'Smith', 'test4444@gmail.com', '4444', 2),
	('Michael', 'Carver', 'test5555@gmail.com', '5555', 1),
	('William', 'Fisher', 'test6666@gmail.com', '6666', 2),
	('George', 'Shepherd', 'test7777@gmail.com', '7777', 2),
	('James', 'Hunter', 'test8888@gmail.com', '8888', 2),
	('John', 'Baker', 'test9999@gmail.com', '9999', 1),
	('Nicholas', 'Carver', 'test0000@gmail.com', '0000', 2);

CREATE TABLE orders (
	id BIGSERIAL PRIMARY KEY,
	user_id BIGINT REFERENCES users,
	totalcost DECIMAL(7,2) DEFAULT 0.0 NOT NULL,
	datetime VARCHAR(50) NOT NULL,
	status_id BIGINT NOT NULL
);

INSERT INTO orders (user_id, totalcost, datetime, status_id)
VALUES (3, 10.75, '2022-05-04 11:30:15', 0),
	(4, 20.00, '2022-04-04 10:30:05', 1),
	(6, 11.55, '2022-03-04 10:30:25', 2),
	(7, 28.90, '2022-02-04 10:30:30', 0),
	(8, 9.45, '2022-01-04 11:30:45', 1),
	(10, 5.75, '2022-05-10 11:30:50', 2);

CREATE TABLE orderitems (
	id BIGSERIAL PRIMARY KEY,
	order_id BIGINT REFERENCES orders,
	book_id BIGINT REFERENCES books,
	quantity INT NOT NULL,
	price DECIMAL(7,2) DEFAULT 0.0 NOT NULL
);

INSERT INTO orderitems (order_id, book_id, quantity, price)
VALUES (1, 1, 1, 10.75),
	(2, 1, 1, 10.75),
	(2, 2, 1, 9.25),
	(3, 3, 1, 11.55),
	(4, 3, 1, 11.55),
	(4, 4, 1, 17.35),
	(5, 5, 1, 9.45),
	(6, 6, 1, 5.75);

DELETE FROM orderitems WHERE id BETWEEN 9 AND 1000;
DELETE FROM orders WHERE id BETWEEN 7 AND 1000;
DELETE FROM books WHERE id BETWEEN 21 AND 1000;
DELETE FROM users WHERE id BETWEEN 11 AND 1000;