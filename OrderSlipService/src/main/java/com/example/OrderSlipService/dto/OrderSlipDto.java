package com.example.OrderSlipService.dto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class OrderSlipDto {
    private Integer id;
    private Date orderDate;
    private String staffAccount;
    private String supplierName;
}
