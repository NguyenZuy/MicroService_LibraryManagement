package com.example.BookService.service;


import com.example.BookService.dao.PublisherDao;
import com.example.BookService.entity.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    @Autowired
    private PublisherDao publisherDao;

    public ResponseEntity<List<Publisher>> addPublisher(Publisher publisher) {
        try {
            publisherDao.save(publisher);
            List<Publisher> publishers = publisherDao.findAll();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Publisher>> deletePublisher(Publisher publisher) {
        try {
            publisherDao.delete(publisher);
            List<Publisher> publishers = publisherDao.findAll();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Publisher>> updatePublisher(Publisher publisher) {
        try {
            if (!publisherDao.existsById(publisher.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            publisherDao.save(publisher);
            List<Publisher> publishers = publisherDao.findAll();
            return new ResponseEntity<>(publishers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Publisher>> getAllPublishers() {
        try {
            return new ResponseEntity<>(publisherDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<Publisher>> findPublisherById(Integer id) {
        try {
            return new ResponseEntity<>(publisherDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Publisher>> findPublisherByName(String name) {
        try {
            return new ResponseEntity<>(publisherDao.findByPublisherName(name), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
