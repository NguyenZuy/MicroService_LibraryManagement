package com.example.ImportSlipService.service;

import com.example.ImportSlipService.dao.ImportSlipDao;
import com.example.ImportSlipService.dto.ImportSlipDto;
import com.example.ImportSlipService.etity.ImportSlip;
import com.example.ImportSlipService.feign.SupplierServiceFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImportSlipService {
    @Autowired
    ImportSlipDao importSlipDao;

    @Autowired
    SupplierServiceFeignInterface supplierServiceFeignInterface;

    public ResponseEntity<List<ImportSlipDto>> GetAllSlip() {
        try {
            List<ImportSlip> importSlips = importSlipDao.findAll();
            List<ImportSlipDto> importSlipDtos = new ArrayList<>();
            for (var importSlip : importSlips){
                ImportSlipDto importSlipDto = new ImportSlipDto();
                importSlipDto.setId(importSlip.getId());
                importSlipDto.setImportDate(importSlip.getImportDate());
                importSlipDto.setStaffAccount(importSlip.getStaffAccount());
                importSlipDto.setSupplierName(GetSupplierNameById(importSlip.getIdSupplier()).getBody());
                importSlipDto.setIdOrderSlip(importSlipDto.getIdOrderSlip());
                importSlipDtos.add(importSlipDto);
            }
            return new ResponseEntity<>(importSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ImportSlipDto> GetSlipById(Integer id) {
        try {
            Optional<ImportSlip> importSlipDb = importSlipDao.findById(id);
            if (!importSlipDb.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            ImportSlip importSlip = importSlipDb.get();
            ImportSlipDto importSlipDto = new ImportSlipDto();
            importSlipDto.setId(importSlip.getId());
            importSlipDto.setImportDate(importSlip.getImportDate());
            importSlipDto.setStaffAccount(importSlip.getStaffAccount());
            importSlipDto.setSupplierName(GetSupplierNameById(importSlip.getIdSupplier()).getBody());
            importSlipDto.setIdOrderSlip(importSlipDto.getIdOrderSlip());

            return new ResponseEntity<>(importSlipDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ImportSlipDto> addImportSlip(ImportSlipDto importSlipDto) {
        try {
//            System.out.println(importSlipDto.getIdOrderSlip());

            ImportSlip importSlip = new ImportSlip();
            importSlip.setImportDate(importSlipDto.getImportDate());
            importSlip.setStaffAccount(importSlipDto.getStaffAccount());
            importSlip.setIdSupplier(GetSupplierIdByName(importSlipDto.getSupplierName()).getBody());
            importSlip.setIdOrderSlip(importSlipDto.getIdOrderSlip());
            ImportSlip importSlipTemp = importSlipDao.save(importSlip);

//            Integer generatedId = importSlipTemp.getId();

            importSlipDto.setId(importSlipTemp.getId());
            System.out.println(importSlipDto.getId());
            return new ResponseEntity<>(importSlipDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ImportSlip>> deleteImportSlip(ImportSlip ImportSlip) {
        try {
            importSlipDao.delete(ImportSlip);
            List<ImportSlip> slipsRs = importSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ImportSlip>> UpdateImportSlip(ImportSlip ImportSlip) {
        try {
            if (!importSlipDao.existsById(ImportSlip.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            importSlipDao.save(ImportSlip);
            List<ImportSlip> slipsRs = importSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> GetSupplierNameById(Integer id) {
        return new ResponseEntity<>(supplierServiceFeignInterface.getSupplierNameById(id).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<Integer> GetSupplierIdByName(String name) {
        return new ResponseEntity<>(supplierServiceFeignInterface.getIdSupplierByName(name).getBody(), HttpStatus.OK);
    }
}
