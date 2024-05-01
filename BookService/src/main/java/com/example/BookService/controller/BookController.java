package com.example.BookService.controller;


import com.example.BookService.dto.BookDto;
import com.example.BookService.entity.Book;
import com.example.BookService.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    Environment environment;

    @PostMapping("add")
    public ResponseEntity<List<Book>> addBook(@RequestBody BookDto bookDto) {
        return bookService.addBook(bookDto);
    }

    @GetMapping("findId/{id}")
    public ResponseEntity<Optional<Book>> findBookById(@PathVariable String id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<Book>> findBookByTitle(@PathVariable String title) {
        return bookService.findBookByTitle(title);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Book>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PutMapping
    public ResponseEntity<List<Book>> updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }

    @DeleteMapping
    public ResponseEntity<List<Book>> deleteBook(@PathVariable Book book) {
        return bookService.deleteBook(book);
    }

    // Feign
    @GetMapping("getBooksForSlip")
    public ResponseEntity<List<BookDto>> getAllBooksForSlip() {
        System.out.println(environment.getProperty("local.server.port"));
        return bookService.getBooksForLoan();
    }

    @GetMapping("getIdBookByTitle/{title}")
    public ResponseEntity<String> getIdBookByName(@PathVariable String title) {
        return bookService.getIdBookByTitle(title);
    }

    @PutMapping("updateBookQuantity/{id}/{quantity}")
    public ResponseEntity<String> updateBookQuantity(@PathVariable String id, @PathVariable Integer quantity) {
        return bookService.updateBookQuantity(id, quantity);
    }

    @GetMapping("getBookTitleById/{id}")
    public ResponseEntity<String> getBookTitleById(@PathVariable String id) {
        return bookService.getBookTitleById(id);
    }
}
