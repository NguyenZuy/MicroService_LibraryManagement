package com.example.ImportSlipService.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailImportSlipDto {
    private Integer importSlipId;
    private String bookName;
    private Integer quantity;
}
