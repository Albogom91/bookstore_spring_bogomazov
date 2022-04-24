CREATE TABLE books (
	id BIGSERIAL PRIMARY KEY,
	isbn VARCHAR(50) UNIQUE NOT NULL,
	title VARCHAR(100) NOT NULL,
	author VARCHAR(100),
	deleted BOOLEAN DEFAULT false NOT NULL
);

INSERT INTO books (isbn, title, author)
VALUES ('1001', 'Pride and Prejudice', 'Jane Austen'),
	('1002', 'To Kill a Mockingbird', 'Harper Lee'),
	('1003', 'The Great Gatsby', 'F. Scott Fitzgerald'),
	('1004', 'One Hundred Years of Solitude', 'Gabriel García Márquez'),
	('1005', 'In Cold Blood', 'Truman Capote'),
	('1006', 'Wide Sargasso Sea', 'Jean Rhys'),
	('1007', 'Brave New World', 'Aldous Huxley'),
	('1008', 'I Capture The Castle', 'Dodie Smith'),
	('1009', 'Jane Eyre', 'Charlotte Bronte'),
	('1010', 'Crime and Punishment', 'Fyodor Dostoevsky'),
	('1011', 'The Secret History', 'Donna Tartt'),
	('1012', 'The Call of the Wild', 'Jack London'),
	('1013', 'The Brothers Karamazov', 'Fyodor Dostoevsky'),
	('1014', 'War and Peace', 'Leo Tolstoy'),
	('1015', 'Anna Karenina', 'Leo Tolstoy'),
	('1016', 'Nineteen Eighty-Four', 'George Orwell'),
	('1017', 'The Master and Margarita', 'Mikhail Bulgakov'),
	('1018', 'Frankenstein', 'Mary Shelley'),
	('1019', 'To the Lighthouse', 'Virginia Woolf'),
	('1020', 'Moby-Dick', 'Herman Melville');