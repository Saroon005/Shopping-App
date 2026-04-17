package com.group.shoppingapp.dto;

import java.time.LocalDateTime;

public class ProductResponseDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private String sku;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(Long id, String name, Double price, String description,
                              String sku, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sku = sku;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getSku() {
        return sku;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}