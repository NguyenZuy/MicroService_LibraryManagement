package com.example.project;

import com.example.project.entities.Book;
import com.example.project.entities.Category;
import com.example.project.entities.DataResponse;
import com.example.project.entities.Receipt;
import com.example.project.entities.User;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DataManager {
    private static DataManager instance;

    public String username;
    private Book[] books = new Book[0];
    public Book[] booksFilter = new Book[0];
    public Receipt[] receipts = new Receipt[0];
    public Receipt[] ReceiptsFillter = new Receipt[0];

    public Category[] categories = new Category[0];


    public List<Book> booksSelect = new ArrayList<>();
    public User user;

    public List<Receipt> receiptslect = new ArrayList<>();
    private DataManager() {

    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Book getBookByID(String id) {
        for (Book book : books) {
            if (book.id.equals(id)) {
                return book;
            }
        }
        return null;
    }


    public void UpdateData(DataResponse dataResponse){
        this.books = dataResponse.getBooks();
        this.receipts = dataResponse.getReceipts();
    }

    public Book[] getBooks() {
        if(booksFilter != null) return booksFilter;
        return books;
    }

    public Category[] getCategories(){
        return categories;
    }
//
    public void addBookSelect(Book book){
        booksSelect.add(book);
    }

    public Receipt[] getReceipts() {
        if(ReceiptsFillter != null) return ReceiptsFillter;
        return receipts;
    }

    public List<Book> getBooksSelect() {
        return booksSelect;
    }

    public void getUser(String data) {
        // Khởi tạo một đối tượng Gson
        Gson gson = new Gson();

        // Chuyển đổi chuỗi JSON thành một đối tượng User
        this.user = gson.fromJson(data, User.class);
    }

    public User getUser() {
        return this.user;
    }
}
