package com.example.BookService.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "inventory_quantity")
    private Integer inventoryQuantity;

    @Column(name = "summary")
    private String summary;

    @Column(name = "publish_date")
    private String publishDate;

    @Column(name = "available_quantity")
    private Integer availableQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_publisher")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Publisher publisher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Category category;

    @Column(name = "status")
    private String status;
}
