package com.example.fetch.controller;

import com.example.fetch.service.ReceiptService;
import com.example.fetch.model.Receipt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// This is the JUnit 5 extension for Spring Boot test
@ExtendWith(SpringExtension.class)

// Specifies that we want to load the full ApplicationContext for the test
@SpringBootTest

// Enables testing of Spring MVC application by not starting the server but by mocking the behavior of HTTP requests and responses
@AutoConfigureMockMvc

// This is the test class for ReceiptController
public class ReceiptControllerTest {

    // Autowiring MockMvc to perform HTTP requests without starting the server
    @Autowired
    private MockMvc mockMvc;

    // Mocking the ReceiptService that our controller depends on
    @MockBean
    private ReceiptService service;

    // This variable will hold the content of the sample.json file
    private String sampleJson;

    // This method runs before each test and reads the sample.json file into the sampleJson variable
    @BeforeEach
    public void setup() throws IOException {
        Path jsonPath = new File("src/test/java/com/example/fetch/controller/receipt.json").toPath();
        sampleJson = Files.readString(jsonPath);
    }

    // This is a test case for the processReceipt method of ReceiptController
    @Test
    public void testProcessReceipt() throws Exception {
        Map<String, String> response = new HashMap<>();
        response.put("id", UUID.randomUUID().toString());

        // We're telling Mockito to return a specific response when processReceipt method of the ReceiptService is called
        when(service.processReceipt(any(Receipt.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // We're using mockMvc to send a POST request and verify the response
        mockMvc.perform(post("/receipts/process")
                        .contentType("application/json")
                        .content(sampleJson))
                .andExpect(status().isOk()) // We expect the status of the response to be 200 OK
                .andExpect(jsonPath("$.id").exists()); // We expect the response to have an "id" field
    }

    // This is a test case for the getReceiptPoints method of ReceiptController
    @Test
    public void testGetReceiptPoints() throws Exception {
        UUID id = UUID.randomUUID();
        Map<String, Integer> response = new HashMap<>();
        response.put("points", 500);

        // We're telling Mockito to return a specific response when getReceiptPoints method of the ReceiptService is called
        when(service.getReceiptPoints(eq(id)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        // We're using mockMvc to send a GET request and verify the response
        mockMvc.perform(get("/receipts/" + id.toString() + "/points")
                        .contentType("application/json"))
                .andExpect(status().isOk()) // We expect the status of the response to be 200 OK
                .andExpect(jsonPath("$.points").value(500)); // We expect the "points" field of the response to be 500
    }
}
