package com.example.ImportSlipService.etity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "import_slip")
public class ImportSlip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "import_date")
    private Date importDate;

    @Column(name = "staff_account")
    private String staffAccount;

    @Column(name = "id_supplier")
    private Integer idSupplier;

    @Column(name = "id_order_slip")
    private Integer idOrderSlip;

    @OneToMany(mappedBy = "importSlip", cascade = CascadeType.ALL)
    private Set<DetailImportSlip> detailImportSlips;
}
