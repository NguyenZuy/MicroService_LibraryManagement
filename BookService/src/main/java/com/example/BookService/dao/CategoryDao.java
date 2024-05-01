package com.example.BookService.dao;

import com.example.BookService.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Integer> {
    Category findOneByCategoryName(String categoryName);
}
