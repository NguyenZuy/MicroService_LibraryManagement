package com.example.LoanSlipService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class DetailLoanSlipDto {
    private Integer loanSlipId;
    private String bookName;
    private Integer quantity;
    private Date borrowDate;
    private Date returnDate;
}