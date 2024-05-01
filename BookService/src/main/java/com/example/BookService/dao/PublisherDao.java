package com.example.BookService.dao;


import com.example.BookService.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherDao extends JpaRepository<Publisher, Integer> {
    List<Publisher> findByPublisherName(String name);

    Publisher findOneByPublisherName(String name);
}
