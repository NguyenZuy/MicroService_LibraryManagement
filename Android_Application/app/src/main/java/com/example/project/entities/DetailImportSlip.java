package com.example.project.entities;

public class DetailImportSlip {
    private Integer importSlipId;
    private String bookName;
    private Integer quantity;

    public String getBookName() {
        return bookName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getImportSlipId() {
        return importSlipId;
    }

    public void setImportSlipId(Integer importSlipId) {
        this.importSlipId = importSlipId;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
