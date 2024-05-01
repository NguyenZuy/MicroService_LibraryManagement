package com.example.ImportSlipService.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDto {
    private String id;
    private byte[] image;
    private String title;
    private String authorName;
    private Integer inventoryQuantity;
    private String summary;
    private String publishDate;
    private Integer availableQuantity;
    private String publisherName;
    private String categoryName;
}
