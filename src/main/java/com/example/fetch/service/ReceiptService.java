package com.example.fetch.service;

import com.example.fetch.model.Item;
import com.example.fetch.model.Receipt;
import com.example.fetch.repository.ItemRepository;
import com.example.fetch.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// The @Service annotation is used to indicate that this class is a service class
@Service
public class ReceiptService {
    // Hashmap to hold receipt UUID and corresponding receipt
    Map<UUID, Receipt> receipts = new HashMap<>();

    // Autowiring the receipt and item repositories
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ItemRepository itemRepository;

    // Function to process receipt and save to repository
    public ResponseEntity<Map<String, String>> processReceipt(Receipt receipt) {
        // Generate a new UUID for the receipt
        UUID id = UUID.randomUUID();
        System.out.println(id);
        // Set the generated UUID in the receipt
        receipt.setId(id);
        // Calculate points for the receipt
        calculatePoints(receipt);
        // Save the receipt in the repository
        receiptRepository.save(receipt);

        // Set the receipt in each item
        for (Item item : receipt.getItems()) {
            item.setReceipt(receipt);
        }

        // Generate a response map with receipt id
        Map<String, String> response = new HashMap<>();
        response.put("id", receipt.getId().toString());
        // Return the response along with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Function to get the receipt points from repository
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(UUID id) {
        Integer points = receiptRepository.findPointsById(id);

        // If no points found, return HTTP 404 status
        if (points == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Generate a response map with receipt points
        Map<String, Integer> response = new HashMap<>();
        response.put("points", points);
        // Return the response along with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Function to calculate points based on the receipt details
    private void calculatePoints(Receipt receipt) {
        // variable to hold the points
        int points = 0;

        // Code block to calculate points based on retailer name
        // 1 point for every alphanumeric character in the retailer name
        String retailerName = receipt.getRetailer();
        for (char c : retailerName.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points += 1;
            }
        }

        // Code block to calculate points based on total amount
        // 50 points if the total is a round dollar amount with no cents
        if (Double.valueOf(receipt.getTotal()) % 1 == 0) {
            points += 50;
        }

        // 25 points if the total is a multiple of 0.25
        if (Double.valueOf(receipt.getTotal()) % 0.25 == 0) {
            points += 25;
        }

        // 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // Code block to calculate points based on item description
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            // If the trimmed length of the item description is
            // a multiple of 3, multiply the price by 0.2 and round up to the nearest integer
            if (description.length() % 3 == 0) {
                points += Math.ceil(Double.valueOf(item.getPrice()) * 0.2);
            }
        }

        // Code block to calculate points based on the purchase date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate purchaseDate = LocalDate.parse(receipt.getPurchaseDate(), dateFormatter);
        // 6 points if the day in the purchase date is odd
        if (purchaseDate.getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // Code block to calculate points based on the purchase time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime(), timeFormatter);
        // 10 points if the time of purchase is after 2:00pm and before 4:00pm
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) &&
                purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        // Finally, set the calculated points in the receipt
        receipt.setPoints(points);
    }
}
