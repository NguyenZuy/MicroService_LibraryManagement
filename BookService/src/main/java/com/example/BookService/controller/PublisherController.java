package com.example.BookService.controller;

import com.example.BookService.entity.Publisher;
import com.example.BookService.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("publisher")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping("add")
    public ResponseEntity<List<Publisher>> addPublisher(@RequestBody Publisher publisher) {
        return publisherService.addPublisher(publisher);
    }

    @DeleteMapping
    public ResponseEntity<List<Publisher>> deletePublisher(@RequestBody Publisher publisher) {
        return publisherService.deletePublisher(publisher);
    }

    @PutMapping("update")
    public ResponseEntity<List<Publisher>> updatePublisher(@RequestBody Publisher publisher) {
        return publisherService.updatePublisher(publisher);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return publisherService.getAllPublishers();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Publisher>> findPublisherById(@PathVariable Integer id) {
        return publisherService.findPublisherById(id);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<List<Publisher>> findPublisherByName(@PathVariable String name) {
        return publisherService.findPublisherByName(name);
    }
}
