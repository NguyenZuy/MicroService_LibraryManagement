package com.example.ImportSlipService.service;

import com.example.ImportSlipService.dao.DetailImportSlipDao;
import com.example.ImportSlipService.dao.ImportSlipDao;
import com.example.ImportSlipService.dto.BookDto;
import com.example.ImportSlipService.dto.DetailImportSlipDto;
import com.example.ImportSlipService.dto.SupplierDto;
import com.example.ImportSlipService.etity.DetailImportSlip;
import com.example.ImportSlipService.etity.DetailImportSlipPK;
import com.example.ImportSlipService.etity.ImportSlip;
import com.example.ImportSlipService.feign.BookServiceFeignInterface;
import com.example.ImportSlipService.feign.SupplierServiceFeignInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DetailImportSlipService {

    @Autowired
    DetailImportSlipDao detailImportSlipDao;

    @Autowired
    ImportSlipDao importSlipDao;

    @Autowired
    BookServiceFeignInterface bookServiceFeignInterface;

    @Autowired
    SupplierServiceFeignInterface supplierServiceFeignInterface;

    public ResponseEntity<List<DetailImportSlip>> AddDetailSlip(DetailImportSlipDto detailImportSlipDto) {
        try {
            DetailImportSlip detailImportSlip = new DetailImportSlip();

            // Get book id by title
            String bookId = GetBookIdByTitle(detailImportSlipDto.getBookName()).getBody();
            System.out.println(bookId);

            // Set primary key
            DetailImportSlipPK detailImportSlipPK = new DetailImportSlipPK();
            detailImportSlipPK.setImportSlipId(detailImportSlipDto.getImportSlipId());
            detailImportSlipPK.setBookId(bookId);
            detailImportSlip.setId(detailImportSlipPK);

            // Set ImportSlip
            Optional<ImportSlip> ImportSlip = importSlipDao.findById(detailImportSlipDto.getImportSlipId());
            detailImportSlip.setImportSlip(ImportSlip.orElse(null));

            // Update book available quantity
            String updateQuantityResult = UpdateBookAvailableQuantity(bookId, detailImportSlipDto.getQuantity()).getBody();
            if (!Objects.equals(updateQuantityResult, "Update book available quantity successful"))
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
            detailImportSlip.setQuantity(detailImportSlipDto.getQuantity());

            detailImportSlipDao.save(detailImportSlip);
            List<DetailImportSlip> slipsRs = detailImportSlipDao.findAll();
            return new ResponseEntity<>(slipsRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<DetailImportSlipDto>> GetAllDetailSlip() {
        try {
            List<DetailImportSlip> slipsRs = detailImportSlipDao.findAll();
            List<DetailImportSlipDto> detailImportSlipDtos = new ArrayList<>();
            for (var slip : slipsRs) {
                DetailImportSlipDto ImportSlipDto = new DetailImportSlipDto();
                ImportSlipDto.setImportSlipId(slip.getImportSlip().getId());
                ImportSlipDto.setBookName(GetBookTitleById(slip.getId().getBookId()).getBody());
                ImportSlipDto.setQuantity(slip.getQuantity());
                detailImportSlipDtos.add(ImportSlipDto);
            }
            return new ResponseEntity<>(detailImportSlipDtos, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Feign book
    public ResponseEntity<List<BookDto>> GetBooksForDetailImport() {
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
    public ResponseEntity<List<SupplierDto>> GetSuppliersForDetailImport() {
        List<SupplierDto> supplierDtos = supplierServiceFeignInterface.getAllSuppliersForSLip().getBody();
        return new ResponseEntity<>(supplierDtos, HttpStatus.OK);
    }

    public ResponseEntity<Integer> GetSupplierIdByName(String name) {
        return new ResponseEntity<>(
                supplierServiceFeignInterface.getIdSupplierByName(name).getBody(), HttpStatus.OK);
    }

    public ResponseEntity<String> GetSupplierNameById(String id) {
        return new ResponseEntity<>(
                supplierServiceFeignInterface.getSupplierNameById(id).getBody(), HttpStatus.OK);
    }
}
