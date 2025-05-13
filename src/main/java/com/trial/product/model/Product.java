package com.trial.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String code;
    String name;
    String description;
    String image;
    String category;
    double price;
    int quantity;
    String internalReference;
    int shellId;
    InventoryStatus inventoryStatus;
    int rating;
    long createdAt;
    long updatedAt;
}
