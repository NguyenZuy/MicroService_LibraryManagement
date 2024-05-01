package com.example.ReturnSlipService.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class DetailReturnSlipPK implements Serializable {
    private Integer id_return_slip;
    private String id_book;
}
