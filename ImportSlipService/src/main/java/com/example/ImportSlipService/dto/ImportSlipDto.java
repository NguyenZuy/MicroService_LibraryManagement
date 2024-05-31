package com.example.ImportSlipService.dto;

import com.example.ImportSlipService.etity.DetailImportSlip;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
@Setter
@Getter
@Data
public class ImportSlipDto {
    private Integer id;
    private Date importDate;
    private String staffAccount;
    private String supplierName;
    private Integer idOrderSlip;
}
