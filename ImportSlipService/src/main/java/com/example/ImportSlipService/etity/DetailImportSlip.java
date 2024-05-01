package com.example.ImportSlipService.etity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "detail_import_slip")
public class DetailImportSlip {

    @EmbeddedId
    private DetailImportSlipPK id;

    @ManyToOne
    @JoinColumn(name = "id_import_slip", referencedColumnName = "id", insertable = false, updatable = false)
    private ImportSlip importSlip;

    @Column(name = "quantity")
    private Integer quantity;
}
