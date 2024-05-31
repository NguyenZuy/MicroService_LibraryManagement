package com.example.LoanSlipService.service;

import com.example.LoanSlipService.dao.LoanSlipDao;
import com.example.LoanSlipService.dto.BookDto;
import com.example.LoanSlipService.dao.DetailLoanSlipDao;
import com.example.LoanSlipService.dto.DetailLoanSlipDto;
import com.example.LoanSlipService.entity.DetailLoanSlip;
import com.example.LoanSlipService.entity.DetailLoanSlipPK;
import com.example.LoanSlipService.entity.LoanSlip;
import com.example.LoanSlipService.feign.DetailLoanSlipInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DetailLoanSlipService {

    @Autowired
    DetailLoanSlipDao detailLoanSlipDao;

    @Autowired
    LoanSlipDao loanSlipDao;

    @Autowired
    DetailLoanSlipInterface detailLoanSlipInterface;

    public ResponseEntity<List<DetailLoanSlip>> AddDetailSlip(DetailLoanSlipDto detailLoanSlipDto) {
        try {
            DetailLoanSlip detailLoanSlip = new DetailLoanSlip();

            // Get book id by title
            String bookId = GetBookIdByTitle(detailLoanSlipDto.getBookName()).getBody();

            // Set primary key
            DetailLoanSlipPK detailLoanSlipPK = new DetailLoanSlipPK();
            detailLoanSlipPK.setId_loan_slip(detailLoanSlipDto.getLoanSlipId());
            detailLoanSlipPK.setId_book(bookId);
            detailLoanSlip.setDetailLoanSlipPK(detailLoanSlipPK);

            // Set loanSlip
            Optional<LoanSlip> loanSlip = loanSlipDao.findById(detailLoanSlipDto.getLoanSlipId());
            detailLoanSlip.setLoanSlip(loanSlip.orElse(null));

            // Add bookId
            detailLoanSlip.setBookId(bookId);

            // Update book available quantity
            String updateQuantityResult = UpdateBookAvailableQuantity(bookId, detailLoanSlipDto.getQuantity()).getBody();
            if (!Objects.equals(updateQuantityResult, "Update book available quantity successful"))
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            detailLoanSlip.setQuantity(detailLoanSlipDto.getQuantity());

            // Add dates
            detailLoanSlip.setBorrowDate(detailLoanSlipDto.getBorrowDate());
            detailLoanSlip.setReturnDate(detailLoanSlipDto.getReturnDate());

            // Add status
            detailLoanSlip.setStatus(detailLoanSlipDto.getStatus());

            detailLoanSlipDao.save(detailLoanSlip);
            List<DetailLoanSlip> slipsRs = detailLoanSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<DetailLoanSlipDto>> GetAllDetailSlip() {
        try {
            List<DetailLoanSlip> slipsRs = detailLoanSlipDao.findAll();
            List<DetailLoanSlipDto> detailLoanSlipDtos = new ArrayList<>();
            for (var slip : slipsRs) {
                DetailLoanSlipDto loanSlipDto = new DetailLoanSlipDto();
                loanSlipDto.setLoanSlipId(slip.getLoanSlip().getId());
                loanSlipDto.setBookName(GetBookTitleById(slip.getBookId()).getBody());
                loanSlipDto.setQuantity(slip.getQuantity());
                loanSlipDto.setBorrowDate(slip.getBorrowDate());
                loanSlipDto.setReturnDate(slip.getReturnDate());
                loanSlipDto.setStatus(slip.getStatus());
                detailLoanSlipDtos.add(loanSlipDto);
            }
            return new ResponseEntity<>(detailLoanSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<DetailLoanSlipDto>> GetDetailSlipByLoanId(int id){
        try {
            List<DetailLoanSlip> slipsRs = detailLoanSlipDao.findByLoanSlip_Id(id);
            List<DetailLoanSlipDto> detailLoanSlipDtos = new ArrayList<>();
            for (var slip : slipsRs) {
                DetailLoanSlipDto loanSlipDto = new DetailLoanSlipDto();
                loanSlipDto.setLoanSlipId(slip.getLoanSlip().getId());
                loanSlipDto.setBookName(GetBookTitleById(slip.getBookId()).getBody());
                loanSlipDto.setQuantity(slip.getQuantity());
                loanSlipDto.setBorrowDate(slip.getBorrowDate());
                loanSlipDto.setReturnDate(slip.getReturnDate());
                loanSlipDto.setStatus(slip.getStatus());
                detailLoanSlipDtos.add(loanSlipDto);
            }
            return new ResponseEntity<>(detailLoanSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<DetailLoanSlip> UpdateDetailSlipStatus(int id, String bookName){
        try {
            // Get book id by name
            String bookId = GetBookIdByTitle(bookName).getBody();
            System.out.println(bookId);

            DetailLoanSlipPK detailLoanSlipPK = new DetailLoanSlipPK();
            detailLoanSlipPK.setId_loan_slip(id);
            detailLoanSlipPK.setId_book(bookId);

            Optional<DetailLoanSlip> detailLoanSlipOptional = detailLoanSlipDao.findById(detailLoanSlipPK);

            if(detailLoanSlipOptional.isPresent()){
                DetailLoanSlip detailLoanSlip = detailLoanSlipOptional.get();
                detailLoanSlip.setStatus("Returned");
                detailLoanSlipDao.save(detailLoanSlip);
                return new ResponseEntity<>(detailLoanSlip, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Feign
    public ResponseEntity<List<BookDto>> GetBooksForDetailLoan() {
        List<BookDto> books = detailLoanSlipInterface.getAllBooksForSlip().getBody();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    public ResponseEntity<String> GetBookIdByTitle(String title) {
        return new ResponseEntity<>(detailLoanSlipInterface.getIdBookByTitle(title).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> UpdateBookAvailableQuantity(String id, Integer quantity) {
        return new ResponseEntity<>(detailLoanSlipInterface.updateBookQuantity(id, quantity).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> GetBookTitleById(String id) {
        return new ResponseEntity<>(detailLoanSlipInterface.getBookTitleById(id).getBody(), HttpStatus.OK);
    }
}
