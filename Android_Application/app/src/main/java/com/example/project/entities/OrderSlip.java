package com.example.project.entities;

import java.util.Date;

public class OrderSlip {
    private Integer id;
    private String orderDate;
    private String supplierName;

    public Integer getId() {
        return id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
