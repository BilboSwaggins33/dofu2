package com.dofu2.config.controller;

import com.dofu2.config.service.AbstractTableService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractController<T, ID extends Serializable> {

    AbstractTableService<T, ID> service;

    public AbstractController(AbstractTableService<T, ID> service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAll(HttpServletRequest request){
        List<T> results = service.findAll();
        if (results == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findOne(@PathVariable ID id, HttpServletRequest request) {
        T result = service.findOne(id);
        if (result == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(result);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody T object, HttpServletRequest request) {
        T result = service.save(object);
        return ResponseEntity.ok(result);
    }

    @PutMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable ID id, @RequestBody T object, HttpServletRequest request) {
        T result = service.save(object);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value= "/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id, HttpServletRequest request) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value="/delete/all" ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteInBatchById(@RequestBody ID[] list, HttpServletRequest request) {
        service.deleteInBatchById(Arrays.asList(list));
        return ResponseEntity.noContent().build();
    }
}
