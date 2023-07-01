package com.example.fetch.model;

// Receipt.java
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Receipt {

    @Id
    private UUID id;
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private String total;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private List<Item> items;
    private int points;

    // Getters
    public UUID getId() {
        return id;
    }

    public String getRetailer() {
        return retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public String getTotal() {
        return total;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getPoints() {
        return points;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer.trim();
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate.trim();
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime.trim();
    }

    public void setTotal(String total) {
        this.total = total.trim();
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }


    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", retailer='" + retailer + '\'' +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", total='" + total + '\'' +
                ", items=" + items +
                ", points=" + points +
                '}';
    }
}


