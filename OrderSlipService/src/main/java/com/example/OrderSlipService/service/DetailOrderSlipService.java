package com.example.OrderSlipService.service;


import com.example.OrderSlipService.dao.DetailOrderSlipDao;
import com.example.OrderSlipService.dao.OrderSlipDao;
import com.example.OrderSlipService.dto.BookDto;
import com.example.OrderSlipService.dto.DetailOrderSlipDto;
import com.example.OrderSlipService.dto.SupplierDto;
import com.example.OrderSlipService.entity.DetailOrderSlip;
import com.example.OrderSlipService.entity.DetailOrderSlipPK;
import com.example.OrderSlipService.entity.OrderSlip;
import com.example.OrderSlipService.feign.BookServiceFeignInterface;
import com.example.OrderSlipService.feign.SupplierServiceFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DetailOrderSlipService {

    @Autowired
    DetailOrderSlipDao detailOrderSlipDao;

    @Autowired
    OrderSlipDao orderSlipDao;

    @Autowired
    BookServiceFeignInterface bookServiceFeignInterface;

    @Autowired
    SupplierServiceFeignInterface supplierServiceFeignInterface;

    public ResponseEntity<DetailOrderSlipDto> AddDetailSlip(DetailOrderSlipDto detailOrderSlipDto) {
        try {
            DetailOrderSlip detailOrderSlip = new DetailOrderSlip();

            // Get book id by title
            String bookId = GetBookIdByTitle(detailOrderSlipDto.getBookName()).getBody();
            System.out.println(bookId);

            // Set primary key
            DetailOrderSlipPK detailOrderSlipPK = new DetailOrderSlipPK();
            detailOrderSlipPK.setId_order_slip(detailOrderSlipDto.getOrderSlipId());
            detailOrderSlipPK.setId_book(bookId);
            detailOrderSlip.setId(detailOrderSlipPK);

            // Set OrderSlip
            Optional<OrderSlip> OrderSlip = orderSlipDao.findById(detailOrderSlipDto.getOrderSlipId());
            detailOrderSlip.setOrderSlip(OrderSlip.orElse(null));
            detailOrderSlip.setQuantity(detailOrderSlipDto.getQuantity());
            detailOrderSlip.setStatus(detailOrderSlipDto.getStatus());

            detailOrderSlipDao.save(detailOrderSlip);
            return new ResponseEntity<>(detailOrderSlipDto, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<DetailOrderSlipDto>> GetAllDetailSlip() {
        try {
            List<DetailOrderSlip> slipsRs = detailOrderSlipDao.findAll();
            List<DetailOrderSlipDto> detailOrderSlipDtos = new ArrayList<>();
            for (var slip : slipsRs) {
                DetailOrderSlipDto OrderSlipDto = new DetailOrderSlipDto();
                OrderSlipDto.setOrderSlipId(slip.getOrderSlip().getId());
                OrderSlipDto.setBookName(GetBookTitleById(slip.getId().getId_book()).getBody());
                OrderSlipDto.setQuantity(slip.getQuantity());
                detailOrderSlipDtos.add(OrderSlipDto);
            }
            return new ResponseEntity<>(detailOrderSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<DetailOrderSlipDto>> GetByOrderId(Integer id) {
        try {
            List<DetailOrderSlip> detailOrderSlipsRs = detailOrderSlipDao.findByOrderSlip_Id(id);
            List<DetailOrderSlipDto> detailOrderSlipDtos = new ArrayList<>();
            for (var slip : detailOrderSlipsRs) {
                DetailOrderSlipDto detailOrderSlipDto = new DetailOrderSlipDto();
                detailOrderSlipDto.setOrderSlipId(slip.getOrderSlip().getId());
                detailOrderSlipDto.setBookName(GetBookTitleById(slip.getId().getId_book()).getBody());
                detailOrderSlipDto.setQuantity(slip.getQuantity());
                detailOrderSlipDto.setStatus(slip.getStatus());
                detailOrderSlipDtos.add(detailOrderSlipDto);
            }
            return new ResponseEntity<>(detailOrderSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<DetailOrderSlip> UpdateDetailSlipStatus(int id, String bookName){
        try {
            // Get book id by name
            String bookId = GetBookIdByTitle(bookName).getBody();
            System.out.println(bookId);

            DetailOrderSlipPK detailLoanSlipPK = new DetailOrderSlipPK();
            detailLoanSlipPK.setId_order_slip(id);
            detailLoanSlipPK.setId_book(bookId);

            System.out.println(detailLoanSlipPK.getId_order_slip() + " " + detailLoanSlipPK.getId_book());

            Optional<DetailOrderSlip> detailOrderSlipOptional = detailOrderSlipDao.findById(detailLoanSlipPK);
            System.out.println("Running here.");
            if(detailOrderSlipOptional.isPresent()){
                DetailOrderSlip detailOrderSlip = detailOrderSlipOptional.get();
                detailOrderSlip.setStatus("Imported");
                detailOrderSlipDao.save(detailOrderSlip);
                return new ResponseEntity<>(detailOrderSlip, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Feign book
    public ResponseEntity<List<BookDto>> GetBooksForDetailOrder() {
        List<BookDto> books = bookServiceFeignInterface.getAllBooksForSlip().getBody();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    public ResponseEntity<String> GetBookIdByTitle(String title) {
        return new ResponseEntity<>(bookServiceFeignInterface.getIdBookByTitle(title).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> UpdateBookAvailableQuantity(String id, Integer quantity) {
        return new ResponseEntity<>(bookServiceFeignInterface.updateBookQuantity(id, quantity).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> GetBookTitleById(String id) {
        return new ResponseEntity<>(bookServiceFeignInterface.getBookTitleById(id).getBody(), HttpStatus.OK);
    }

    // Feign Supplier
    public ResponseEntity<List<SupplierDto>> GetSuppliersForDetailOrder() {
        List<SupplierDto> supplierDtos = supplierServiceFeignInterface.getAllSuppliersForSLip().getBody();
        return new ResponseEntity<>(supplierDtos, HttpStatus.OK);
    }

    public ResponseEntity<Integer> GetSupplierIdByName(String name) {
        return new ResponseEntity<>(
                supplierServiceFeignInterface.getIdSupplierByName(name).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> GetSupplierNameById(int id) {
        return new ResponseEntity<>(
                supplierServiceFeignInterface.getSupplierNameById(id).getBody(), HttpStatus.OK);
    }
}
