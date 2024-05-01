package com.example.ReturnSlipService.controller;

import com.example.ReturnSlipService.dto.BookDto;
import com.example.ReturnSlipService.dto.DetailReturnSlipDto;
import com.example.ReturnSlipService.entity.DetailReturnSlip;
import com.example.ReturnSlipService.service.DetailReturnSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("detailReturnSlip")
public class DetailReturnSlipController {
    @Autowired
    DetailReturnSlipService detailReturnSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<DetailReturnSlipDto>> GetAllDetailReturnSlip() {
        return detailReturnSlipService.GetAllDetailSlip();
    }

    @PostMapping("add")
    public ResponseEntity<List<DetailReturnSlip>> AddDetailReturnSlip
            (@RequestBody DetailReturnSlipDto detailReturnSlipDto) {
        return detailReturnSlipService.AddDetailSlip(detailReturnSlipDto);
    }

    @GetMapping("getBooks")
    public ResponseEntity<List<BookDto>> GetBooks() {
        return detailReturnSlipService.GetBooksForDetailReturn();
    }

    @GetMapping("findBookId/{title}")
    public ResponseEntity<String> GetBookId(@PathVariable String title) {
        return detailReturnSlipService.GetBookIdByTitle(title);
    }
}
