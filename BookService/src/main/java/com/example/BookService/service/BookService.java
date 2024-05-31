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
import java.util.Base64;
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

            byte[] imageBytes = Base64.getDecoder().decode(bookDto.getImage());
            book.setImage(imageBytes);
            book.setImage(imageBytes);

            book.setTitle(bookDto.getTitle());
            book.setAuthorName(bookDto.getAuthorName());
            book.setInventoryQuantity(bookDto.getInventoryQuantity());
            book.setSummary(bookDto.getSummary());
            book.setPublishDate(bookDto.getPublishDate());
            book.setAvailableQuantity(bookDto.getAvailableQuantity());
            book.setPublisher(publisher);
            book.setCategory(category);
            book.setStatus("Borrowable");
            bookDao.save(book);

            // Get all books
            List<Book> booksRs = bookDao.findAll();
            return new ResponseEntity<>(booksRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<BookDto> findBookById(String id) {
        try {
            Optional<Book> gettedBook = bookDao.findById(id);
            Book book;
            BookDto bookDto = new BookDto();
            if (gettedBook.isPresent()) {
               book = gettedBook.get();
                bookDto.setId(book.getId());

                // convert image byte[] to base64
                String base64String = Base64.getEncoder().encodeToString(book.getImage());
                bookDto.setImage(base64String);

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
                bookDto.setStatus(book.getStatus());
            } else {
                System.out.println("Book not found");
            }
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
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

    public ResponseEntity<List<BookDto>> getAllBooks() {
        try {
            List<Book> books = bookDao.findAll();
            List<BookDto> bookDtos = new ArrayList<>();
            for (Book book : books) {
                BookDto bookDto = new BookDto();
                bookDto.setId(book.getId());

                // convert image byte[] to base64
                String base64String = Base64.getEncoder().encodeToString(book.getImage());
                bookDto.setImage(base64String);

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
                bookDto.setStatus(book.getStatus());
                bookDtos.add(bookDto);
            }
            return new ResponseEntity<>(bookDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<BookDto> updateBook(BookDto bookDto) {
        try {
            Optional<Book> getBook = bookDao.findById(bookDto.getId());
            if (!getBook.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Find publisher and category by Id
            Publisher publisher = publisherDao.findOneByPublisherName(bookDto.getPublisherName());
            Category category = categoryDao.findOneByCategoryName(bookDto.getCategoryName());

            Book book= getBook.get();

            // Change new image if new image has been selected
            if (!bookDto.getImage().equals("")){
                byte[] imageBytes = Base64.getDecoder().decode(bookDto.getImage());
                book.setImage(imageBytes);
                book.setImage(imageBytes);
            }

            book.setTitle(bookDto.getTitle());
            book.setAuthorName(bookDto.getAuthorName());
            book.setInventoryQuantity(bookDto.getInventoryQuantity());
            book.setSummary(bookDto.getSummary());
            book.setPublishDate(bookDto.getPublishDate());
            book.setAvailableQuantity(bookDto.getAvailableQuantity());
            book.setPublisher(publisher);
            book.setCategory(category);
            book.setStatus(bookDto.getStatus());
            bookDao.save(book);
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
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

    public ResponseEntity<String> updateBookBorrowable(String id){
        try {
            if (!bookDao.existsById(id)) {
                return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
            }


            if (bookDao.findById(id).isPresent()){
                Book book = bookDao.findById(id).get();
                book.setStatus("Borrowable");
                bookDao.save(book);
            }


            return new ResponseEntity<>("Update Successful!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> updateBookNotBorrowable(String id) {
        try {
            if (!bookDao.existsById(id)) {
                return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
            }

            if (bookDao.findById(id).isPresent()){
                Book book = bookDao.findById(id).get();
                book.setStatus("Not Borrowable");
                bookDao.save(book);
            }

            return new ResponseEntity<>("Update Successful!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<BookDto>> getBorrowableBooks() {
        try {
            List<Book> books = bookDao.findByStatus("Borrowable");
            List<BookDto> bookDtos = new ArrayList<>();

            for (Book book : books) {
                BookDto bookDto = new BookDto();
                bookDto.setId(book.getId());

                // convert image byte[] to base64
                String base64String = Base64.getEncoder().encodeToString(book.getImage());
                bookDto.setImage(base64String);

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
                bookDto.setStatus(book.getStatus());
                bookDtos.add(bookDto);
            }

            return new ResponseEntity<>(bookDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    // Feign
    public ResponseEntity<List<BookDto>> getBooksForLoan() {
        // Only get book that are borrowable
        List<Book> books = bookDao.findByStatus("Borrowable");
        List<BookDto> bookDtos = new ArrayList<>();
        for (Book book : books) {
            BookDto bookDto = new BookDto();

            bookDto.setId(book.getId());

            String base64String = Base64.getEncoder().encodeToString(book.getImage());
            bookDto.setImage(base64String);

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

    public ResponseEntity<String> decreaseBookQuantity(String id, Integer newQuantity) {
        try {
            Optional<Book> bookOptional = bookDao.findById(id);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                System.out.println("Available Quantity: " + book.getAvailableQuantity());

                int finalQuantity = book.getAvailableQuantity() - newQuantity;
                System.out.println("Final Quantity: " + finalQuantity);

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

    public ResponseEntity<String> increaseBookQuantity(String id, Integer newQuantity) {
        try {
            Optional<Book> bookOptional = bookDao.findById(id);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                int finalQuantity = book.getAvailableQuantity() + newQuantity;
                if (finalQuantity > book.getInventoryQuantity()) {
                    return new ResponseEntity<>(
                            "Exceed the number of books", HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<String> updateBookQuantity(String id, Integer newQuantity) {
        try {
            System.out.println(newQuantity);
            Optional<Book> bookOptional = bookDao.findById(id);

            if (bookOptional.isPresent()) {
                Book book = bookOptional.get();
                book.setInventoryQuantity(book.getInventoryQuantity() + newQuantity);

                int finalAvailableQuantity = book.getAvailableQuantity() + newQuantity;
                if (finalAvailableQuantity > book.getInventoryQuantity()) {
                    return new ResponseEntity<>(
                            "Exceed the number of books", HttpStatus.BAD_REQUEST);
                }
                book.setAvailableQuantity(finalAvailableQuantity);
                System.out.println(book.getAvailableQuantity());
                System.out.println(book.getInventoryQuantity());

                bookDao.save(book);
                return new ResponseEntity<>("Update book quantity successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Book not found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Fail to update book quantity!", HttpStatus.INTERNAL_SERVER_ERROR);
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
