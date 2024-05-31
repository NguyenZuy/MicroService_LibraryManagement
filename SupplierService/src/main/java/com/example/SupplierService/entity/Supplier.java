package com.example.SupplierService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_id_seq")
    @SequenceGenerator(name = "supplier_id_seq", sequenceName = "supplier_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;

    @Column(name = "supplier_name", unique = true)
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;
}
