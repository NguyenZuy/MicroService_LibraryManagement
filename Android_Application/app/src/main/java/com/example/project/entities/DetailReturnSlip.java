package com.example.project.entities;

import java.util.Date;

public class DetailReturnSlip {
    private Integer returnSlipId;
    private String bookName;
    private Integer quantity;
    private String borrowDate;
    private String returnDate;


    public Integer getReturnSlipId() {
        return returnSlipId;
    }

    public String getBookName() {
        return bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnSlipId(Integer returnSlipId) {
        this.returnSlipId = returnSlipId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

}
