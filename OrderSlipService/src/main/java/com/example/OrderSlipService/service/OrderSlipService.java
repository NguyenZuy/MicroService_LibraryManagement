package com.example.OrderSlipService.service;

import com.example.OrderSlipService.dao.OrderSlipDao;
import com.example.OrderSlipService.dto.OrderSlipDto;
import com.example.OrderSlipService.entity.OrderSlip;
import com.example.OrderSlipService.feign.SupplierServiceFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderSlipService {
    @Autowired
    OrderSlipDao orderSlipDao;

    @Autowired
    SupplierServiceFeignInterface supplierServiceFeignInterface;

    public ResponseEntity<List<OrderSlipDto>> GetAllSlip() {
        try {
            List<OrderSlip> orderSlips = orderSlipDao.findAll();

            List<OrderSlipDto> orderSlipDtos = new ArrayList<>();
            for (OrderSlip orderSlip : orderSlips){
                OrderSlipDto orderSlipDto = new OrderSlipDto();
                orderSlipDto.setId(orderSlip.getId());
                orderSlipDto.setOrderDate(orderSlip.getOrderDate());
                orderSlipDto.setStaffAccount(orderSlip.getStaffAccount());
                orderSlipDto.setSupplierName(GetSupplierNameById(orderSlip.getIdSupplier()).getBody());

                orderSlipDtos.add(orderSlipDto);
            }

            return new ResponseEntity<>(orderSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<OrderSlipDto> GetSlipById(Integer id) {
        try {
            Optional<OrderSlip> orderSlipDb = orderSlipDao.findById(id);

            if (!orderSlipDb.isPresent()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            OrderSlip orderSlip = orderSlipDb.get();
            OrderSlipDto orderSlipDto = new OrderSlipDto();
            orderSlipDto.setId(orderSlip.getId());
            orderSlipDto.setOrderDate(orderSlip.getOrderDate());
            orderSlipDto.setStaffAccount(orderSlip.getStaffAccount());
            orderSlipDto.setSupplierName(GetSupplierNameById(orderSlip.getIdSupplier()).getBody());

            return new ResponseEntity<>(orderSlipDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<OrderSlipDto> addOrderSlip(OrderSlipDto orderSlipDto) {
        try {
            OrderSlip orderSlip = new OrderSlip();
            orderSlip.setId(orderSlipDto.getId());
            orderSlip.setOrderDate(orderSlipDto.getOrderDate());
            orderSlip.setStaffAccount(orderSlipDto.getStaffAccount());
            orderSlip.setIdSupplier(GetSupplierIdByName(orderSlipDto.getSupplierName()).getBody());

            orderSlipDao.save(orderSlip);
            orderSlipDto.setId(orderSlip.getId());
            return new ResponseEntity<>(orderSlipDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    public ResponseEntity<String> GetSupplierNameById(int id) {
        return new ResponseEntity<>(supplierServiceFeignInterface.getSupplierNameById(id).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<Integer> GetSupplierIdByName(String name) {
        return new ResponseEntity<>(supplierServiceFeignInterface.getIdSupplierByName(name).getBody(), HttpStatus.OK);
    }
}
