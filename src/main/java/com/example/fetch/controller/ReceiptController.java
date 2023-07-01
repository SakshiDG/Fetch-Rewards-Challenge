package com.example.fetch.controller;

import com.example.fetch.model.ErrorResponse;
import com.example.fetch.model.Receipt;
import com.example.fetch.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
public class ReceiptController {

    @Autowired
    ReceiptService service;

    // Processing a receipt
    @PostMapping("/receipts/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        // Calls the processReceipt method of ReceiptService
        return service.processReceipt(receipt);
    }

    // Fetching the points for a given receipt id
    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(@PathVariable UUID id) {

        // Calls the getReceiptPoints method of ReceiptService
        return service.getReceiptPoints(id);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleErrors(Exception e) {
        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Invalid Request, Please try again!");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}