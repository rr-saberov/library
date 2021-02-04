package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        logger.info("current repository size " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @GetMapping("/shelf/search")
    public String searchBook(Model model, Integer bookIdToSearch) {
        List<Book> filteredBooks = new ArrayList<>();
        bookService.searchBookById(bookIdToSearch);
        model.addAttribute("book", new Book());
        for (Book book : bookService.getAllBooks()) {
            if (book.getId().equals(bookIdToSearch)) {
                filteredBooks.add(book);
            }
        }
        model.addAttribute("filteredBooks", filteredBooks);
        return "search_form";
    }

    @GetMapping("/shelf/searchBySize")
    public String searchBookBySize(Model model, Integer bookSizeToSearch) {
        List<Book> searchedBook = new ArrayList<>();
        bookService.searchBookBySize(bookSizeToSearch);
        model.addAttribute(bookService.getAllBooks());
        model.addAttribute("book", new Book());

        List<Book> books = bookService.getAllBooks();
        for(Book book : books) {
            if (book.getSize().equals(bookSizeToSearch)) {
                searchedBook.add(book);
                return searchedBook.toString();
            }
        }
        return "search_form";
    }

    @GetMapping("/shelf/searchByAuthor")
    public String searchBookByAuthor(Model model, String bookAuthorToSearch) {
        List<Book> searchedBook = new ArrayList<>();
        bookService.searchBookByAuthor(bookAuthorToSearch);
        model.addAttribute(bookService.getAllBooks());
        model.addAttribute("book", new Book());

        List<Book> books = bookService.getAllBooks();
        for(Book book : books) {
            if (book.getAuthor().equals(bookAuthorToSearch)) {
                searchedBook.add(book);
                return searchedBook.toString();
            }
        }
        return "search_form";
    }

    @GetMapping("/shelf/searchByTitle")
    public String searchBookByTitle(Model model, String bookTitleToSearch) {
        List<Book> searchedBook = new ArrayList<>();
        bookService.searchBookByTitle(bookTitleToSearch);
        model.addAttribute(bookService.getAllBooks());
        model.addAttribute("book", new Book());

        List<Book> books = bookService.getAllBooks();
        for(Book book : books) {
            if (book.getTitle().equals(bookTitleToSearch)) {
                searchedBook.add(book);
                return searchedBook.toString();
            }
        }
        return "search_form";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        if (bookService.removeBookById(bookIdToRemove)) {
            return "redirect:/books/shelf";
        } else
            return "redirect:/books/shelf";
    }

    @PostMapping("/removeBySize")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByAuthor")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByTitle")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            return "redirect:/books/shelf";
        } else {
            return "redirect:/books/shelf";
        }
    }
}
