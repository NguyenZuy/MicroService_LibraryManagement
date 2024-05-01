package com.example.BookService.service;

import com.example.BookService.dao.CategoryDao;
import com.example.BookService.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public ResponseEntity<List<Category>> addCategory(Category category) {
        try {
            categoryDao.save(category);
            List<Category> categories = categoryDao.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Category>> deleteCategory(Category category) {
        try {
            categoryDao.delete(category);
            List<Category> categories = categoryDao.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Category>> updateCategory(Category category) {
        try {
            if (!categoryDao.existsById(category.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            categoryDao.save(category);
            List<Category> categories = categoryDao.findAll();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Category>> getAllCategories() {
        try {
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
