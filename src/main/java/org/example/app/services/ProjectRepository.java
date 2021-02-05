package org.example.app.services;

import org.example.web.dto.Book;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean searchItemById(Integer bookIdToSearch);

    boolean searchItemBySize(Integer bookSizeToSearch);

    List<Book> searchItemByAuthor(String bookAuthorToSearch);

    boolean searchItemByTitle(String bookTitleToSearch);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByAuthor(String bookAuthorToRemove);

    boolean removeItemBySize(Integer bookSizeToRemove);

    boolean removeItemByTitle(String bookTitleToRemove);
}
