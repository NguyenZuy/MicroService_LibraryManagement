package com.example.ImportSlipService.service;

import com.example.ImportSlipService.dao.ImportSlipDao;
import com.example.ImportSlipService.etity.ImportSlip;
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

    public ResponseEntity<List<ImportSlip>> GetAllSlip() {
        try {
            return new ResponseEntity<>(importSlipDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<ImportSlip>> GetSlipById(Integer id) {
        try {
            return new ResponseEntity<>(importSlipDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ImportSlip>> addImportSlip(ImportSlip ImportSlip) {
        try {
            importSlipDao.save(ImportSlip);
            List<ImportSlip> slipsRs = importSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
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
}
