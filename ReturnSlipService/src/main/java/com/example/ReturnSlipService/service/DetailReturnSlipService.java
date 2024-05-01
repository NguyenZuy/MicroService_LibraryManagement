package com.example.ReturnSlipService.service;

import com.example.ReturnSlipService.dao.DetailReturnSlipDao;
import com.example.ReturnSlipService.dao.ReturnSlipDao;
import com.example.ReturnSlipService.dto.BookDto;
import com.example.ReturnSlipService.dto.DetailReturnSlipDto;
import com.example.ReturnSlipService.entity.DetailReturnSlip;
import com.example.ReturnSlipService.entity.DetailReturnSlipPK;
import com.example.ReturnSlipService.entity.ReturnSlip;
import com.example.ReturnSlipService.feign.DetailReturnSlipInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DetailReturnSlipService {

    @Autowired
    DetailReturnSlipDao detailReturnSlipDao;

    @Autowired
    ReturnSlipDao ReturnSlipDao;

    @Autowired
    DetailReturnSlipInterface detailReturnSlipInterface;

    public ResponseEntity<List<DetailReturnSlip>> AddDetailSlip(DetailReturnSlipDto detailReturnSlipDto) {
        try {
            DetailReturnSlip detailReturnSlip = new DetailReturnSlip();

            // Get book id by title
            String bookId = GetBookIdByTitle(detailReturnSlipDto.getBookName()).getBody();
            System.out.println(bookId);

            // Set primary key
            DetailReturnSlipPK detailReturnSlipPK = new DetailReturnSlipPK();
            detailReturnSlipPK.setId_return_slip(detailReturnSlipDto.getReturnSlipId());
            detailReturnSlipPK.setId_book(bookId);
            detailReturnSlip.setDetailReturnSlipPK(detailReturnSlipPK);

            // Set ReturnSlip
            Optional<ReturnSlip> ReturnSlip = ReturnSlipDao.findById(detailReturnSlipDto.getReturnSlipId());
            detailReturnSlip.setReturnSlip(ReturnSlip.orElse(null));

            // Add bookId
            detailReturnSlip.setBookId(bookId);

            // Update book available quantity
            String updateQuantityResult = UpdateBookAvailableQuantity(bookId, detailReturnSlipDto.getQuantity()).getBody();
            if (!Objects.equals(updateQuantityResult, "Update book available quantity successful"))
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            detailReturnSlip.setQuantity(detailReturnSlipDto.getQuantity());

            // Add dates
            detailReturnSlip.setBorrowDate(detailReturnSlipDto.getBorrowDate());
            detailReturnSlip.setReturnDate(detailReturnSlipDto.getReturnDate());

            detailReturnSlipDao.save(detailReturnSlip);
            List<DetailReturnSlip> slipsRs = detailReturnSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<DetailReturnSlipDto>> GetAllDetailSlip() {
        try {
            List<DetailReturnSlip> slipsRs = detailReturnSlipDao.findAll();
            List<DetailReturnSlipDto> detailReturnSlipDtos = new ArrayList<>();
            for (var slip : slipsRs) {
                DetailReturnSlipDto ReturnSlipDto = new DetailReturnSlipDto();
                ReturnSlipDto.setReturnSlipId(slip.getReturnSlip().getId());
                ReturnSlipDto.setBookName(slip.getBookId());
                ReturnSlipDto.setQuantity(slip.getQuantity());
                ReturnSlipDto.setBorrowDate(slip.getBorrowDate());
                ReturnSlipDto.setReturnDate(slip.getReturnDate());
                detailReturnSlipDtos.add(ReturnSlipDto);
            }
            return new ResponseEntity<>(detailReturnSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Feign
    public ResponseEntity<List<BookDto>> GetBooksForDetailReturn() {
        List<BookDto> books = detailReturnSlipInterface.getAllBooksForSLip().getBody();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    public ResponseEntity<String> GetBookIdByTitle(String title) {
        return new ResponseEntity<>(detailReturnSlipInterface.getIdBookByTitle(title).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> UpdateBookAvailableQuantity(String id, Integer quantity) {
        return new ResponseEntity<>(detailReturnSlipInterface.updateBookQuantity(id, quantity).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> GetBookTitleById(String id) {
        return new ResponseEntity<>(detailReturnSlipInterface.getBookTitleById(id).getBody(), HttpStatus.OK);
    }
}
