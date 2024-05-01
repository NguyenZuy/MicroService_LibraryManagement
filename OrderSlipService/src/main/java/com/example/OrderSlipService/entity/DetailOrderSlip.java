package com.example.OrderSlipService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "detail_order_slip")
public class DetailOrderSlip {

    @EmbeddedId
    private DetailOrderSlipPK id;

    @ManyToOne
    @JoinColumn(name = "id_order_slip", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderSlip orderSlip;

    @Column(name = "quantity")
    private Integer quantity;
}
