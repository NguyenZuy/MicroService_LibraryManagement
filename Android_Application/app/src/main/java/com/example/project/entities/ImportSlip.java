package com.example.project.entities;

import java.util.Date;

public class ImportSlip {
    private Integer id;
    private String importDate;
    private String staffAccount;
    private String supplierName;
    private Integer idOrderSlip;

    public Integer getId() {
        return id;
    }

    public String getImportDate() {
        return importDate;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public Integer getIdOrderSlip() {
        return idOrderSlip;
    }

    public String getStaffAccount() {
        return staffAccount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void setIdOrderSlip(Integer idOrderSlip) {
        this.idOrderSlip = idOrderSlip;
    }

    public void setStaffAccount(String staffAccount) {
        this.staffAccount = staffAccount;
    }
}
