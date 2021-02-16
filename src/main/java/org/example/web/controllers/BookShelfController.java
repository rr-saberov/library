package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@Scope("singleton")
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
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model)  {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    @GetMapping("/shelf/search")
    public String searchBook(Model model, Book book) {
        model.addAttribute("book", new Book());
        model.addAttribute("filteredBooks", bookService.searchBookById(book.getId()));
        return "search_form";
    }

    @GetMapping("/shelf/searchBySize")
    public String searchBookBySize(Model model, Book book) {
        model.addAttribute("book", new Book());
        model.addAttribute("filteredBooks", bookService.searchBookBySize(book.getSize()));
        return "search_form";
    }

    @GetMapping("/shelf/searchByAuthor")
    public String searchBookByAuthor(Model model, Book book) {
        model.addAttribute("book", new Book());
        model.addAttribute("filteredBooks", bookService.searchBookByAuthor(book.getAuthor()));
        return "search_form";
    }

    @GetMapping("/shelf/searchByTitle")
    public String searchBookByTitle(Model model, Book book) {
        model.addAttribute("book", new Book());
        model.addAttribute("filteredBooks",bookService.searchBookByTitle(book.getTitle()));
        return "search_form";
    }

    @PostMapping("/remove")
    public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeBySize")
    public String removeBookBySize(@Valid BookSizeToRemove bookSizeToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookBySize(bookSizeToRemove.getSize());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByAuthor")
    public String removeBookByAuthor(@Valid BookAuthorToRemove bookAuthorToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookByAuthor(bookAuthorToRemove.getAuthor());
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/removeByTitle")
    public String removeBookByTitle(@Valid BookTitleToRemove bookTitleToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookByTitle(bookTitleToRemove.getTitle());
            return "redirect:/books/shelf";
        }
    }
}
