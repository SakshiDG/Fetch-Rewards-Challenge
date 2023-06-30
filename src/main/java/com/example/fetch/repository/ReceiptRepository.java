package com.example.fetch.repository;

import com.example.fetch.model.Receipt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ReceiptRepository extends CrudRepository<Receipt, UUID> {
    @Query("SELECT r.points FROM Receipt r WHERE r.id = :id")
    Integer findPointsById(@Param("id") UUID id);
}