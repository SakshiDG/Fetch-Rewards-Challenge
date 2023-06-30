package com.example.fetch.controller;

import com.example.fetch.model.Receipt;
import com.example.fetch.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

// The @RestController annotation is used to indicate that this class is a RESTful controller
@RestController
public class ReceiptController {

    // Autowiring the ReceiptService
    @Autowired
    ReceiptService service;

    // This POST mapping is used to process a receipt
    @PostMapping("/receipts/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        // Logs the retailer for debugging or informational purposes
        System.out.println(receipt.getRetailer());
        // Calls the processReceipt method of ReceiptService
        return service.processReceipt(receipt);
    }

    // This GET mapping is used to fetch the points for a given receipt id
    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(@PathVariable UUID id) {
        // Logs the receipt id for debugging or informational purposes
        System.out.println(id);
        // Calls the getReceiptPoints method of ReceiptService
        return service.getReceiptPoints(id);
    }
}
