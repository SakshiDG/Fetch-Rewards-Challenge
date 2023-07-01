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

    // Hashmap to hold receipt UUID and corresponding receipt
    Map<UUID, Receipt> receipts = new HashMap<>();

    // Autowiring the receipt and item repositories
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ItemRepository itemRepository;

    // This enables/disables the detail view of Print Info Logs, These logs are Beneficial for debugging
    Boolean detailView = true;

    // Function to process receipt and save to repository
    public ResponseEntity<Map<String, String>> processReceipt(Receipt receipt) {

        // Generate a new UUID for the receipt
        UUID id = UUID.randomUUID();

        printInfo("Processing receipt", receipt.toString());

        // Set the generated UUID in the receipt
        receipt.setId(id);
        printInfo("Receipt ID assigned" , String.valueOf(id));

        // Calculate points for the receipt
        calculatePoints(receipt);
        printInfo("Points calculated for receipt with ID" , String.valueOf(id));

        // Set the receipt in each item
        for (Item item : receipt.getItems()) {
            item.setReceipt(receipt);
        }

        // Save the receipt in the repository
        receiptRepository.save(receipt);
        printInfo("Receipt saved successfully", receiptRepository.findById(id).toString());

        // Generate a response map with receipt id
        Map<String, String> response = new HashMap<>();
        response.put("id", receipt.getId().toString());
        printInfo("Processed receipt successfully. Response" , response.toString());

        // Return the response along with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // Function to get the receipt points from repository
    public ResponseEntity<Map<String, Integer>> getReceiptPoints(UUID id) {
        printInfo("Retrieving receipt points for ID", String.valueOf(id));

        Integer points = receiptRepository.findPointsById(id);

        // If no points found, return HTTP 404 status
        if (points == null) {
            throw new IllegalArgumentException("Invalid Request, Please Try Again!");
        }

        // Generate a response map with receipt points
        Map<String, Integer> response = new HashMap<>();
        response.put("points", points);

        printInfo("Retrieved receipt points for ID", String.valueOf(id) + " - Points: " + points);

        // Return the response along with HTTP status
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private void calculatePoints(Receipt receipt) {
    // variable to hold the points
    int points = 0;

    // Code block to calculate points based on retailer name
    // 1 point for every alphanumeric character in the retailer name
    String retailerName = receipt.getRetailer();
    int retailerPoints = 0;
    for (char c : retailerName.toCharArray()) {
        if (Character.isLetterOrDigit(c)) {
            retailerPoints += 1;
        }
    }
    points += retailerPoints;
    printInfo("Retailer points", String.valueOf(retailerPoints));

    // Code block to calculate points based on total amount
    // 50 points if the total is a round dollar amount with no cents
    if (Double.valueOf(receipt.getTotal()) % 1 == 0) {
        points += 50;
        printInfo("Total is a round dollar amount", "50");

    }
    // 25 points if the total is a multiple of 0.25
    if (Double.valueOf(receipt.getTotal()) % 0.25 == 0) {
        points += 25;
        printInfo("Total is a multiple of 0.25", "25");

    }

    // 5 points for every two items on the receipt
    int itemsPoints = (receipt.getItems().size() / 2) * 5;
    points += itemsPoints;
    printInfo("Points for every two items on the receipt", String.valueOf(itemsPoints));

    // Code block to calculate points based on item description
    int descriptionPoints = 0;
    for (Item item : receipt.getItems()) {
        String description = item.getShortDescription().trim();
        // If the trimmed length of the item description is
        // a multiple of 3, multiply the price by 0.2 and round up to the nearest integer
        if (description.length() % 3 == 0) {
            double itemPoints = Math.ceil(Double.valueOf(item.getPrice()) * 0.2);
            descriptionPoints += itemPoints;
        }
    }
    points += descriptionPoints;
    printInfo("Description points", String.valueOf(descriptionPoints));

    // Code block to calculate points based on the purchase date
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate purchaseDate = LocalDate.parse(receipt.getPurchaseDate(), dateFormatter);
    // 6 points if the day in the purchase date is odd
    int datePoints = (purchaseDate.getDayOfMonth() % 2 != 0) ? 6 : 0;
    points += datePoints;
    printInfo("Purchase date points", String.valueOf(datePoints));

    // Code block to calculate points based on the purchase time
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime(), timeFormatter);
    // 10 points if the time of purchase is after 2:00pm and before 4:00pm
    int timePoints = (purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) ? 10 : 0;
    points += timePoints;
    printInfo("Purchase time points", String.valueOf(timePoints));

    // Finally, set the calculated points in the receipt
    receipt.setPoints(points);
    printInfo("Total Points", String.valueOf(points));
}
    public void printInfo(String msg, String data){
        if(detailView){
            System.out.println("--------------------------------------------------------");
            System.out.println(msg + ": " + data);
            System.out.println("--------------------------------------------------------");
        }

    }
}