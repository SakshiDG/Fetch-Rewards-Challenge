package com.example.fetch.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Long id;
    private String shortDescription;
    private String price;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    // Getters
    public Long getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public Receipt getReceiptId() {
        return receipt;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription.trim();
    }

    public void setPrice(String price) {
        this.price = price.trim();
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
    @Override
    public String toString() {
        return "Item{" +
                "id=" + this.getId() +
                ", shortDescription='" + this.getShortDescription() + '\'' +
                ", price='" + this.getPrice() + '\'' +
                '}';
    }
}