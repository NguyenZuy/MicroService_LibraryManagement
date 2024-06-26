package com.example.ImportSlipService.etity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class DetailImportSlipPK implements Serializable {

    private Integer id_import_slip;

    private String id_book;
}

