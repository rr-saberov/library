package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book) {
        if (!book.getTitle().equals("") || !book.getAuthor().equals(""))
            bookRepo.store(book);
    }

    public List<Book> searchBookById(Integer bookIdToSearch) {
        return bookRepo.searchItemById(bookIdToSearch);
    }

    public List<Book> searchBookBySize(Integer bookSizeToSearch) {
        return bookRepo.searchItemBySize(bookSizeToSearch);
    }

    public List<Book> searchBookByAuthor(String bookAuthorToSearch) {
        return bookRepo.searchItemByAuthor(bookAuthorToSearch);
    }

    public List<Book> searchBookByTitle(String bookTitleToSearch) {
        return bookRepo.searchItemByTitle(bookTitleToSearch);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookBySize(Integer bookSizeToRemove) {
        return bookRepo.removeItemBySize(bookSizeToRemove);
    }

    public boolean removeBookByAuthor(String bookAuthorToRemove) {
        return bookRepo.removeItemByAuthor(bookAuthorToRemove);
    }

    public boolean removeBookByTitle(String bookTitleToRemove) {
        return bookRepo.removeItemByTitle(bookTitleToRemove);
    }
}
