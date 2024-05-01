package com.example.ReturnSlipService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Data
public class DetailReturnSlipDto {
    private Integer returnSlipId;
    private String bookName;
    private Integer quantity;
    private Date borrowDate;
    private Date returnDate;
}