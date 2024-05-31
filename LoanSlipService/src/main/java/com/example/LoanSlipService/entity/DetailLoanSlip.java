package com.example.LoanSlipService.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@Data
@Entity
@Table(name = "detail_loan_slip")
public class DetailLoanSlip {

    @EmbeddedId
    private DetailLoanSlipPK detailLoanSlipPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_loan_slip")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private LoanSlip loanSlip;

    @Column(name = "id_book", insertable = false, updatable = false)
    private String bookId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "borrow_date")
    private Date borrowDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "status")
    private String status;
}
