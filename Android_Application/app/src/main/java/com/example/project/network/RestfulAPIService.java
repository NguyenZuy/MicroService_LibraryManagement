package com.example.project.network;

import com.example.project.entities.Book;
import com.example.project.entities.NewBook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestfulAPIService {
    @GET("book/getAll")
    Call<List<NewBook>> getAllBooks();
}
