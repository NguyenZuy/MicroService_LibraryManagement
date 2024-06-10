package com.example.project.entities;

public class DetailOrderSlip {
    private Integer orderSlipId;
    private String bookName;
    private Integer quantity;
    private String status;

    public Integer getOrderSlipId() {
        return orderSlipId;
    }

    public String getBookName() {
        return bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderSlipId(Integer orderSlipId) {
        this.orderSlipId = orderSlipId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
