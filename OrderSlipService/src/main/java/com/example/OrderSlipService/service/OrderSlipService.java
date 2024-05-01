package com.example.OrderSlipService.service;

import com.example.OrderSlipService.dao.OrderSlipDao;
import com.example.OrderSlipService.entity.OrderSlip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderSlipService {
    @Autowired
    OrderSlipDao orderSlipDao;

    public ResponseEntity<List<OrderSlip>> GetAllSlip() {
        try {
            return new ResponseEntity<>(orderSlipDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<OrderSlip>> GetSlipById(Integer id) {
        try {
            return new ResponseEntity<>(orderSlipDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<com.example.OrderSlipService.entity.OrderSlip>> addOrderSlip(com.example.OrderSlipService.entity.OrderSlip OrderSlip) {
        try {
            orderSlipDao.save(OrderSlip);
            List<com.example.OrderSlipService.entity.OrderSlip> slipsRs = orderSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<com.example.OrderSlipService.entity.OrderSlip>> deleteOrderSlip(com.example.OrderSlipService.entity.OrderSlip OrderSlip) {
        try {
            orderSlipDao.delete(OrderSlip);
            List<com.example.OrderSlipService.entity.OrderSlip> slipsRs = orderSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<com.example.OrderSlipService.entity.OrderSlip>> UpdateOrderSlip(com.example.OrderSlipService.entity.OrderSlip OrderSlip) {
        try {
            if (!orderSlipDao.existsById(OrderSlip.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            orderSlipDao.save(OrderSlip);
            List<com.example.OrderSlipService.entity.OrderSlip> slipsRs = orderSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
