package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T book);

    boolean searchItemById(Integer bookIdToSearch);

    boolean searchItemBySize(Integer bookSizeToSearch);

    boolean searchItemByAuthor(String bookAuthorToSearch);

    boolean searchItemByTitle(String bookTitleToSearch);

    boolean removeItemById(Integer bookIdToRemove);

    boolean removeItemByAuthor(String bookAuthorToRemove);

    boolean removeItemBySize(Integer bookSizeToRemove);

    boolean removeItemByTitle(String bookTitleToRemove);
}
