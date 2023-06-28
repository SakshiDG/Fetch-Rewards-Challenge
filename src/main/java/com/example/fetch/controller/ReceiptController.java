package com.example.fetch.controller;

import com.example.fetch.model.Receipt;
import com.example.fetch.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
public class ReceiptController {

    @Autowired
    ReceiptService service;

    @PostMapping("/receipts/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody Receipt receipt) {
        System.out.println(receipt.getRetailer());
        return service.processReceipt(receipt);
    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(@PathVariable UUID id) {
        System.out.println(id);
        return service.getReceiptPoints(id);
    }
}