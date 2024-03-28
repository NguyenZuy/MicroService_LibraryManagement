package com.zuy.BooksService.controllers;

import com.zuy.BooksService.entities.Book;
import com.zuy.BooksService.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping("allBooks")
    public ResponseEntity<List<Book>> getAllQuestions(){
        return bookService.getAllQuestions();
    }

    @PostMapping("add")
    public ResponseEntity<List<Book>> addNewBook(@RequestBody Book book){
        return bookService.addNewBook(book);
    }

    @PostMapping("addMultiple")
    public ResponseEntity<List<Book>> addNewBooks(@RequestBody List<Book> books){
        return bookService.addNewBooks(books);
    }

    @PutMapping("update")
    public ResponseEntity<List<Book>> updateBook(@RequestBody Book book){
        return bookService.updateBook(book);
    }

    @DeleteMapping("delete")
    public ResponseEntity<List<Book>> deleteBook(@RequestBody Book book){
        return bookService.deleteBook(book);
    }
}
