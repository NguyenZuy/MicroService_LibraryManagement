package com.example.BookService.dao;


import com.example.BookService.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDao extends JpaRepository<Book, String> {
    List<Book> findAllByTitle(String title);

    Book findByTitle(String title);
}
