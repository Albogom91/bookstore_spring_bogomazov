CREATE TABLE books (
	id BIGSERIAL PRIMARY KEY,
	isbn VARCHAR(50) UNIQUE NOT NULL,
	title VARCHAR(100) NOT NULL,
	author VARCHAR(100),
	price DECIMAL(7,2) DEFAULT 0.0 NOT NULL,
	cover_id BIGINT REFERENCES covers,
	deleted BOOLEAN DEFAULT false NOT NULL
);

CREATE TABLE covers (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(20)
);

CREATE TABLE users (
	id BIGSERIAL PRIMARY KEY,
	firstname VARCHAR(50) NOT NULL,
	lastname VARCHAR(50) NOT NULL,
	email VARCHAR(100) UNIQUE NOT NULL,
	userpassword VARCHAR(100) NOT NULL,
	role_id BIGINT REFERENCES roles,
	deleted BOOLEAN DEFAULT false NOT NULL
);

CREATE TABLE roles (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(20)
);

INSERT INTO covers (name)
VALUES ('SOFT'),
	('HARD'),
	('SPECIAL');

INSERT INTO books (isbn, title, author, price, cover_id)
VALUES ('1001', 'Pride and Prejudice', 'Jane Austen', '10.75', '1'),
	('1002', 'To Kill a Mockingbird', 'Harper Lee', '9.25', '2'),
	('1003', 'The Great Gatsby', 'F. Scott Fitzgerald', '11.55', '3'),
	('1004', 'One Hundred Years of Solitude', 'Gabriel García Márquez', '17.35', '3'),
	('1005', 'In Cold Blood', 'Truman Capote', '9.45', '2'),
	('1006', 'Wide Sargasso Sea', 'Jean Rhys', '5.75', '1'),
	('1007', 'Brave New World', 'Aldous Huxley', '19.25', '2'),
	('1008', 'I Capture The Castle', 'Dodie Smith', '7.75', '1'),
	('1009', 'Jane Eyre', 'Charlotte Bronte', '8.75', '1'),
	('1010', 'Crime and Punishment', 'Fyodor Dostoevsky', '18.55', '3'),
	('1011', 'The Secret History', 'Donna Tartt', '17.55', '3'),
	('1012', 'The Call of the Wild', 'Jack London', '14.25', '2'),
	('1013', 'The Brothers Karamazov', 'Fyodor Dostoevsky', '15.35', '2'),
	('1014', 'War and Peace', 'Leo Tolstoy', '9.15', '1'),
	('1015', 'Anna Karenina', 'Leo Tolstoy', '15.25', '2'),
	('1016', 'Nineteen Eighty-Four', 'George Orwell', '18.25', '2'),
	('1017', 'The Master and Margarita', 'Mikhail Bulgakov', '11.25', '2'),
	('1018', 'Frankenstein', 'Mary Shelley', '7.75', '1'),
	('1019', 'To the Lighthouse', 'Virginia Woolf', '13.95', '2'),
	('1020', 'Moby-Dick', 'Herman Melville', '19.95', '3');

INSERT INTO roles (name)
VALUES ('ADMIN'),
	('MANAGER'),
	('CUSTOMER');

INSERT INTO users (firstname, lastname, email, userpassword, role_id)
VALUES ('Alex', 'Baker', 'test1111@gmail.com', '1111', '1'),
	('John', 'Carpenter', 'test2222@gmail.com', '2222', '1'),
	('James', 'Smith', 'test3333@gmail.com', '3333', '3'),
	('John', 'Smith', 'test4444@gmail.com', '4444', '3'),
	('Michael', 'Carver', 'test5555@gmail.com', '5555', '2'),
	('William', 'Fisher', 'test6666@gmail.com', '6666', '3'),
	('George', 'Shepherd', 'test7777@gmail.com', '7777', '3'),
	('James', 'Hunter', 'test8888@gmail.com', '8888', '3'),
	('John', 'Baker', 'test9999@gmail.com', '9999', '2'),
	('Nicholas', 'Carver', 'test0000@gmail.com', '0000', '3');
