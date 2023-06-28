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

@Service
public class ReceiptService {
    Map<UUID, Receipt> receipts = new HashMap<>();

    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ItemRepository itemRepository;

    public ResponseEntity<Map<String, String>> processReceipt(Receipt receipt) {
        UUID id = UUID.randomUUID();
        System.out.println(id);
        receipt.setId(id);
        calculatePoints(receipt);
        // Save the receipt along with its items
        receiptRepository.save(receipt);

        // Set the receipt in each item
        for (Item item : receipt.getItems()) {
            item.setReceipt(receipt);
        }



        Map<String, String> response = new HashMap<>();
        response.put("id", receipt.getId().toString());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

public ResponseEntity<Map<String, Integer>> getReceiptPoints(UUID id) {
    Integer points = receiptRepository.findPointsById(id);

    if (points == null) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    Map<String, Integer> response = new HashMap<>();
    response.put("points", points);
    return new ResponseEntity<>(response, HttpStatus.OK);
}

    private void calculatePoints(Receipt receipt) {
        int points = 0;

        // 1 point for every alphanumeric character in the retailer name
        String retailerName = receipt.getRetailer();
        for (char c : retailerName.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                points += 1;
            }
        }

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

        // If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                points += Math.ceil(Double.valueOf(item.getPrice()) * 0.2);
            }
        }

        // 6 points if the day in the purchase date is odd
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate purchaseDate = LocalDate.parse(receipt.getPurchaseDate(), dateFormatter);
        if (purchaseDate.getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime(), timeFormatter);
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) &&
                purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        // Set the points in the receipt
        receipt.setPoints(points);
    }




//    private void calculatePoints(Receipt receipt) {
//        // Perform your point calculations here based on the receipt data
//
//        int points = 0;
//
//        // One point for every alphanumeric character in the retailer name.
//        String retailerName = receipt.getRetailer();
//        for (char ch : retailerName.toCharArray()) {
//            if (Character.isLetterOrDigit(ch)) {
//                points++;
//            }
//        }
//
//        // 50 points if the total is a round dollar amount with no cents.
//        double total = Double.parseDouble(receipt.getTotal());
//        if (total % 1 == 0) {
//            points += 50;
//        }
//
//        // 25 points if the total is a multiple of 0.25.
//        else if (total % 0.25 == 0) {
//            points += 25;
//        }
//
//        // 5 points for every two items on the receipt.
//        points += (receipt.getItems().size() / 2) * 5;
//
//        // If the trimmed length of the item description is a multiple of 3,
//        // multiply the price by 0.2 and round up to the nearest integer.
//        // The result is the number of points earned.
//        for (Item item : receipt.getItems()) {
//            if (item.getShortDescription().trim().length() % 3 == 0) {
//                double itemPrice = Double.parseDouble(item.getPrice());
//                points += Math.ceil(itemPrice * 0.2);
//            }
//        }
//
//        // 6 points if the day in the purchase date is odd.
//        String[] dateParts = receipt.getPurchaseDate().split("-");
//        int day = Integer.parseInt(dateParts[2]);
//        if (day % 2 != 0) {
//            points += 6;
//        }
//
//        // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
//        String[] timeParts = receipt.getPurchaseTime().split(":");
//        int hour = Integer.parseInt(timeParts[0]);
//        if (hour >= 14 && hour < 16) {
//            points += 10;
//        }
//
//        receipt.setPoints(points);
//    }
}

