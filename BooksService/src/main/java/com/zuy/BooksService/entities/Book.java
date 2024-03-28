package com.zuy.BooksService.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    private String id;
    private String title;
    private String authorName;
    private String genre;
    private String publisher;
    private String publishDate;
    private int quantity;
    private int availableQuantity;
}
