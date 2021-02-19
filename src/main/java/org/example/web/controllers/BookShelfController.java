package org.example.web.controllers;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.example.app.exceptions.FileUploadException;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@Scope("singleton")
@RequestMapping(value = "books")
public class BookShelfController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;
    private List<File> fileList = new ArrayList<>();

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
        model.addAttribute("fileList", fileList);
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



    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsoluteFile() + File.separator + name);

        if (!file.isEmpty()) {
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(bytes);
                fileList.add(serverFile);
                logger.info("new file saved at: " + serverFile.getAbsolutePath());
            }
        } else {
            logger.info("file upload fail");
            throw new FileUploadException("file not found");
        }
        return "redirect:/books/shelf";
    }

    @GetMapping("/files/{file_name}")
    public void DownloadFile(@RequestParam(value = "file_name") String fileName, HttpServletResponse response) {
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(new File(fileName)))) {
            IOUtils.copy(stream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException exception) {
            logger.info("file download fail");
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

    @ExceptionHandler(FileUploadException.class)
    public String handlerError(Model model, FileUploadException exception) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/500";
    }
}
