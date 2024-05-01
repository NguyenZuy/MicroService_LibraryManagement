package com.example.OrderSlipService.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "order_slip")
public class OrderSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "staff_account")
    private String staffAccount;

    @Column(name = "id_supplier")
    private Integer idSupplier;

    @OneToMany(mappedBy = "orderSlip", cascade = CascadeType.ALL)
    private Set<DetailOrderSlip> detailOrderSlips;
}
