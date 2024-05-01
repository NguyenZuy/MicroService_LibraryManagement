package com.example.OrderSlipService.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SupplierDto {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
