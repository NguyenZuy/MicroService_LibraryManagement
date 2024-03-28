package com.zuy.BooksService.services;

import com.zuy.BooksService.daos.BookDAO;
import com.zuy.BooksService.entities.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookDAO bookDAO;

    public ResponseEntity<List<Book>> getAllQuestions() {
        try {
            List<Book> booksRs = bookDAO.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving books", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> addNewBook(Book book) {
        try {
            bookDAO.save(book);
            List<Book> booksRs = bookDAO.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while adding book", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> addNewBooks(List<Book> books) {
        try {
            bookDAO.saveAll(books);
            List<Book> booksRs = bookDAO.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while adding book", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> deleteBook(Book book) {
        try {
            bookDAO.delete(book);
            List<Book> booksRs = bookDAO.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while deleting book", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<Book>> updateBook(Book book) {
        try {
            if (!bookDAO.existsById(book.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            bookDAO.save(book);
            List<Book> booksRs = bookDAO.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("An error occurred while updating book", e);
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
