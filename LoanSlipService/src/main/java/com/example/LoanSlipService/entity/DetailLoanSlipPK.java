package com.example.LoanSlipService.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class DetailLoanSlipPK implements Serializable {
    private Integer id_loan_slip;
    private String id_book;
}
