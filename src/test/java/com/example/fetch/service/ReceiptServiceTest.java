package com.example.fetch.service;

import com.example.fetch.model.Receipt;
import com.example.fetch.repository.ItemRepository;
import com.example.fetch.repository.ReceiptRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// This annotation tells JUnit to enable Mockito annotations
@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    // This annotation creates a mock implementation of the ReceiptRepository
    @Mock
    private ReceiptRepository receiptRepository;

    // This annotation creates a mock implementation of the ItemRepository
    @Mock
    private ItemRepository itemRepository;

    // This annotation creates an instance of ReceiptService and injects the mocks into it
    @InjectMocks
    private ReceiptService receiptService;

    private Receipt receipt;

    // This method is run before each test. It sets up the test data.
    @BeforeEach
    void setUp() throws IOException {
        // Create a new ObjectMapper for reading JSON
        ObjectMapper objectMapper = new ObjectMapper();
        // Read a Receipt from a JSON file
        receipt = objectMapper.readValue(Files.readString(Paths.get("src/test/java/com/example/fetch/service/receipt.json")), Receipt.class);
    }

    // This is a test for the processReceipt method
    @Test
    void processReceipt() {
        // Mock the repository to return the receipt
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        // Call the method under test
        ResponseEntity<Map<String, String>> responseEntity = receiptService.processReceipt(receipt);

        // Verify the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "The processReceipt method should return a response with status OK");

        // Verify the response body contains the correct ID
        assertEquals(receipt.getId().toString(), responseEntity.getBody().get("id"), "The response body should contain the correct ID");
    }

    @Test
    void getReceiptPoints() {
        // Prepare the ID for the test
        UUID id = receipt.getId();

        // Mock the repository to return the points
        when(receiptRepository.findPointsById(id)).thenReturn(receipt.getPoints());

        // Call the method under test
        ResponseEntity<Map<String, Integer>> responseEntity = receiptService.getReceiptPoints(id);

        // Verify the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "The getReceiptPoints method should return a response with status OK");

        // Verify the response body contains the correct points
        assertEquals(receipt.getPoints(), responseEntity.getBody().get("points"), "The response body should contain the correct points");
    }

}
