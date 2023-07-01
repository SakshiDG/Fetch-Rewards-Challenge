package com.example.fetch.repository;

import com.example.fetch.model.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> findByReceipt_Id(UUID receiptId);

}
