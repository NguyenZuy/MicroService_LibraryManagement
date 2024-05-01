package com.example.ReturnSlipService.service;

import com.example.ReturnSlipService.dao.ReturnSlipDao;
import com.example.ReturnSlipService.entity.ReturnSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReturnSlipService {
    @Autowired
    ReturnSlipDao returnSlipDao;

    public ResponseEntity<List<ReturnSlip>> GetAllSlip() {
        try {
            return new ResponseEntity<>(returnSlipDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<ReturnSlip>> GetSlipById(Integer id) {
        try {
            return new ResponseEntity<>(returnSlipDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ReturnSlip>> GetAllSlipByEmail(String email) {
        try {
            return new ResponseEntity<>(returnSlipDao.findByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ReturnSlip>> GetAllSlipByPhone(String phoneNumber) {
        try {
            return new ResponseEntity<>(returnSlipDao.findByPhoneNumber(phoneNumber), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ReturnSlip>> addReturnSlip(ReturnSlip ReturnSlip) {
        try {
            returnSlipDao.save(ReturnSlip);
            List<ReturnSlip> slipsRs = returnSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ReturnSlip>> deleteReturnSlip(ReturnSlip ReturnSlip) {
        try {
            returnSlipDao.delete(ReturnSlip);
            List<ReturnSlip> slipsRs = returnSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<ReturnSlip>> UpdateReturnSlip(ReturnSlip ReturnSlip) {
        try {
            if (!returnSlipDao.existsById(ReturnSlip.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            returnSlipDao.save(ReturnSlip);
            List<ReturnSlip> slipsRs = returnSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
