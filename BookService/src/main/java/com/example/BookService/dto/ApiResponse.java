package com.example.BookService.dto;

import lombok.Data;

@Data
public class ApiResponse<T> {
    int status;
    String message;
    T data;
}
