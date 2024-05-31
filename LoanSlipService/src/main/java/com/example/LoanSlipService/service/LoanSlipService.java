package com.example.LoanSlipService.service;

import com.example.LoanSlipService.dao.LoanSlipDao;
import com.example.LoanSlipService.entity.LoanSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanSlipService {
    @Autowired
    LoanSlipDao loanSlipDao;

    public ResponseEntity<List<LoanSlip>> GetAllSlip() {
        try {
            return new ResponseEntity<>(loanSlipDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<LoanSlip>> GetSlipById(Integer id) {
        try {
            return new ResponseEntity<>(loanSlipDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<LoanSlip>> GetAllSlipByEmail(String email) {
        try {
            return new ResponseEntity<>(loanSlipDao.findByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<LoanSlip>> GetAllSlipByPhone(String phoneNumber) {
        try {
            return new ResponseEntity<>(loanSlipDao.findByPhoneNumber(phoneNumber), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<LoanSlip> addLoanSlip(LoanSlip loanSlip) {
        try {
            loanSlipDao.save(loanSlip);
            return new ResponseEntity<>(loanSlip, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<LoanSlip>> deleteLoanSlip(LoanSlip loanSlip) {
        try {
            loanSlipDao.delete(loanSlip);
            List<LoanSlip> slipsRs = loanSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<LoanSlip>> UpdateLoanSlip(LoanSlip loanSlip) {
        try {
            if (!loanSlipDao.existsById(loanSlip.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            loanSlipDao.save(loanSlip);
            List<LoanSlip> slipsRs = loanSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
