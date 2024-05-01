package com.example.OrderSlipService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class DetailOrderSlipPK implements Serializable {

    @Column(name = "id_order_slip")
    private Integer orderSlipId;

    @Column(name = "id_book")
    private String bookId;
}

