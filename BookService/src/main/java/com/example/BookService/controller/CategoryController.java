package com.example.BookService.controller;

import com.example.BookService.entity.Category;
import com.example.BookService.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("add")
    public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping
    public ResponseEntity<List<Category>> deleteCategory(@RequestBody Category category) {
        return categoryService.deleteCategory(category);
    }

    @PutMapping
    public ResponseEntity<List<Category>> updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Category>> getAllCategories() {
        return categoryService.getAllCategories();
    }
}