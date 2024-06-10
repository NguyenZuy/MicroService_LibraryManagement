package com.example.project.entities;

import java.util.List;

import lombok.Data;

@Data
public class Publisher {

    private Integer id;

    private String publisherName;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public Integer getId() {
        return id;
    }

    public String getPublisherName() {
        return publisherName;
    }
}