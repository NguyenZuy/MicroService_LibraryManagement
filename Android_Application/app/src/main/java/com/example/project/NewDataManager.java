package com.example.project;

import com.example.project.entities.NewBook;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewDataManager {
    private static NewDataManager instance;

    public static NewDataManager getInstance() {
        if (instance == null) {
            instance = new NewDataManager();
        }
        return instance;
    }

    public List<NewBook> books;
    public List<NewBook> booksSelect = new ArrayList<NewBook>();

//    public NewBook getBookByID(String id){
//        for (NewBook book : books) {
//            if(book.getId().equals(id)){
//                return book;
//            }
//        }
//        return null;
//    }
}
