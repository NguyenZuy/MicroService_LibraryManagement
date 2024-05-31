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

    @GetMapping("findById/{id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable String id) {
        return bookService.findBookById(id);
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<Book>> findBookByTitle(@PathVariable String title) {
        return bookService.findBookByTitle(title);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("getBorrowable")
    public ResponseEntity<List<BookDto>> getBorrowableBooks() {
        return bookService.getBorrowableBooks();
    }

    @PutMapping("update")
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto book) {
        return bookService.updateBook(book);
    }

    @PutMapping("setBookBorrowable/{id}")
    public ResponseEntity<String> updateBookBorrowable(@PathVariable String id) {
        return bookService.updateBookBorrowable(id);
    }

    @PutMapping("setBookNotBorrowable/{id}")
    public ResponseEntity<String> updateBookNotBorrowable(@PathVariable String id) {
        return bookService.updateBookNotBorrowable(id);
    }

    @DeleteMapping
    public ResponseEntity<List<Book>> deleteBook(@PathVariable Book book) {
        return bookService.deleteBook(book);
    }

    // Feign
    @GetMapping("getBooksForSlip")
    public ResponseEntity<List<BookDto>> getAllBooksForSlip() {
        return bookService.getBooksForLoan();
    }

    @GetMapping("getIdBookByTitle/{title}")
    public ResponseEntity<String> getIdBookByName(@PathVariable String title) {
        return bookService.getIdBookByTitle(title);
    }

    @PutMapping("decreaseBookQuantity/{id}/{quantity}")
    public ResponseEntity<String> decreaseBookQuantity(@PathVariable String id, @PathVariable Integer quantity) {
        return bookService.decreaseBookQuantity(id, quantity);
    }

    @PutMapping("increaseBookQuantity/{id}/{quantity}")
    public ResponseEntity<String> increaseBookQuantity(@PathVariable String id, @PathVariable Integer quantity) {
        return bookService.increaseBookQuantity(id, quantity);
    }

    @PutMapping("importNewBook/{id}/{quantity}")
    public ResponseEntity<String> updateBookQuantity(@PathVariable String id, @PathVariable Integer quantity) {
        return bookService.updateBookQuantity(id, quantity);
    }

    @GetMapping("getBookTitleById/{id}")
    public ResponseEntity<String> getBookTitleById(@PathVariable String id) {
        return bookService.getBookTitleById(id);
    }
}
