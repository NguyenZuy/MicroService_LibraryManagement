package com.example.ReturnSlipService.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Data
@Entity
@Table(name = "return_slip")
public class ReturnSlip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "customer_account")
    private String customerAccount;

    @Column(name = "staff_account")
    private String staffAccount;

    @Column(name = "loan_id")
    private Integer loanId;

    @OneToMany(mappedBy = "returnSlip", fetch = FetchType.LAZY)
    private List<DetailReturnSlip> detailReturnSlips;
}
