package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book>{

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        if (book.getAuthor() != null || book.getSize() != 0 || book.getTitle() != null) {
            book.setId(book.hashCode());
            logger.info("store new book " + book);
            repo.add(book);
        }
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        if (bookIdToRemove != null) {
            for (Book book : retreiveAll()) {
                if (book.getId().equals(bookIdToRemove)) {
                    logger.info("remove book completed: " + book);
                    return repo.remove(book);
                }
            }
        }
        return false;
    }

    @Override
    public boolean removeItemBySize(Integer bookSizeToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getSize().equals(bookSizeToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByAuthor(String bookAuthorToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getAuthor().equals(bookAuthorToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    @Override
    public boolean removeItemByTitle(String bookTitleToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getTitle().equals(bookTitleToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }
}
