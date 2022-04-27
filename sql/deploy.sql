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

INSERT INTO covers (name)
VALUES ('SOFT'),
	('HARD'),
	('SPECIAL')

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
