DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    author varchar (250) NOT NULL,
    title varchar (250) NOT NULL,
    size int DEFAULT NULL
);