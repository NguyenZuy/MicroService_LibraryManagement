package com.example.ReturnSlipService.feign;

import com.example.ReturnSlipService.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient("BOOK-SERVICE")
public interface DetailReturnSlipInterface {
    @GetMapping("book/getBooksForSlip")
    ResponseEntity<List<BookDto>> getAllBooksForSLip();

    @GetMapping("book/getIdBookByTitle/{title}")
    ResponseEntity<String> getIdBookByTitle(@PathVariable String title);

    @PutMapping("book/updateBookQuantity/{id}/{quantity}")
    ResponseEntity<String> updateBookQuantity(@PathVariable String id, @PathVariable Integer quantity);

    @GetMapping("book/getBookTitleById/{id}")
    public ResponseEntity<String> getBookTitleById(@PathVariable String id);
}
