package com.example.project.entities;

import java.util.Date;

public class DetailLoanSlip {
    private Integer loanSlipId;
    private String bookName;
    private Integer quantity;
    private String borrowDate;
    private String returnDate;
    private String status;

    public DetailLoanSlip(Integer loanSlipId, String bookName, Integer quantity, String borrowDate, String returnDate, String status) {
        this.loanSlipId = loanSlipId;
        this.bookName = bookName;
        this.quantity = quantity;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status = status;
    }

    public Integer getLoanSlipId() {
        return loanSlipId;
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

    public String getStatus() {
        return status;
    }

    public void setLoanSlipId(Integer loanSlipId) {
        this.loanSlipId = loanSlipId;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
