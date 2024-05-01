package com.example.OrderSlipService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class DetailOrderSlipDto {
    private Integer orderSlipId;
    private String bookName;
    private Integer quantity;
}