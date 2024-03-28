package com.zuy.BooksService.daos;

import com.zuy.BooksService.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDAO extends JpaRepository<Book, String> {

}
