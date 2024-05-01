package com.example.BookService.service;


import com.example.BookService.dto.BookDto;
import com.example.BookService.dao.BookDao;
import com.example.BookService.dao.CategoryDao;
import com.example.BookService.dao.PublisherDao;
import com.example.BookService.entity.Book;
import com.example.BookService.entity.Category;
import com.example.BookService.entity.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private PublisherDao publisherDao;
    @Autowired
    private CategoryDao categoryDao;

    public ResponseEntity<List<Book>> addBook(BookDto bookDto) {
        try {
            // Find publisher and category by Id
            Publisher publisher = publisherDao.findOneByPublisherName(bookDto.getPublisherName());
            Category category = categoryDao.findOneByCategoryName(bookDto.getCategoryName());

            // Add to book entity
            Book book = new Book();
            book.setId(bookDto.getId());
            book.setImage(bookDto.getImage());
            book.setTitle(bookDto.getTitle());
            book.setAuthorName(bookDto.getAuthorName());
            book.setInventoryQuantity(bookDto.getInventoryQuantity());
            book.setSummary(bookDto.getSummary());
            book.setPublishDate(bookDto.getPublishDate());
            book.setAvailableQuantity(bookDto.getAvailableQuantity());
            book.setPublisher(publisher);
            book.setCategory(category);
            bookDao.save(book);

            // Get all books
            List<Book> booksRs = bookDao.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<Book>> findBookById(String id) {
        try {
            return new ResponseEntity<>(bookDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Book>> findBookByTitle(String title) {
        try {
            return new ResponseEntity<>(bookDao.findAllByTitle(title), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Book>> getAllBooks() {
        try {
            List<Book> books = bookDao.findAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Book>> updateBook(Book book) {
        try {
            if (!bookDao.existsById(book.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            bookDao.save(book);
            List<Book> books = bookDao.findAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Book>> deleteBook(Book book) {
        try {
            bookDao.delete(book);
            List<Book> books = bookDao.findAll();
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Feign
    public ResponseEntity<List<BookDto>> getBooksForLoan() {
        List<Book> books = bookDao.findAll();
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = new BookDto();

            bookDto.setId(book.getId());
            bookDto.setImage(book.getImage());
            bookDto.setTitle(book.getTitle());
            bookDto.setAuthorName(book.getAuthorName());
            bookDto.setInventoryQuantity(book.getInventoryQuantity());
            bookDto.setSummary(book.getSummary());
            bookDto.setPublishDate(book.getPublishDate());
            bookDto.setAvailableQuantity(book.getAvailableQuantity());

            if (book.getPublisher() != null) {
                bookDto.setPublisherName(book.getPublisher().getPublisherName());
            }
            if (book.getCategory() != null) {
                bookDto.setCategoryName(book.getCategory().getCategoryName());
            }
            bookDtos.add(bookDto);
        }
        return new ResponseEntity<>(bookDtos, HttpStatus.OK);
    }

    public ResponseEntity<String> getIdBookByTitle(String title) {
        try {
            Book book = bookDao.findByTitle(title);
            return new ResponseEntity<>(book.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Fail to get Id!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateBookQuantity(String id, Integer newQuantity) {
        try {
            Optional<Book> bookOptional = bookDao.findById(id);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                int finalQuantity = book.getAvailableQuantity() - newQuantity;
                if (finalQuantity < 0) {
                    return new ResponseEntity<>(
                            "The number of books is not enough", HttpStatus.BAD_REQUEST);
                }
                book.setAvailableQuantity(finalQuantity);

                bookDao.save(book);
                return new ResponseEntity<>("Update book available quantity successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Fail to update book available quantity!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> getBookTitleById(String id) {
        try {
            Book book = bookDao.findById(id).orElse(null);
            if (book != null) {
                return new ResponseEntity<>(book.getTitle(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Fail to get book name!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
