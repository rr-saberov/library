package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String searchBook(Model model,Integer bookIdToSearch) {
        bookService.searchBookById(bookIdToSearch);
        model.addAttribute("");
        return "search_form";
    }

    @GetMapping("/shelf/searchBySize")
    public String searchBookBySize(Integer bookSizeToSearch) {
        bookService.searchBookBySize(bookSizeToSearch);
        return "search_form";
    }

    @GetMapping("/shelf/searchByAuthor")
    public String searchBookByAuthor(String bookAuthorToSearch) {
        bookService.searchBookByAuthor(bookAuthorToSearch);
        return "search_form";
    }

    @GetMapping("/shelf/searchByTitle")
    public String searchBookByTitle(String bookTitleToSearch) {
        bookService.searchBookByAuthor(bookTitleToSearch);
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
