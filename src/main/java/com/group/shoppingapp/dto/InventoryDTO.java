package com.group.shoppingapp.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InventoryDTO {

    private Long inventoryId;

    @NotNull(message = "Available quantity cannot be null")
    @Min(value = 0, message = "Available quantity must be 0 or more")
    private Integer availableQuantity;

    @NotNull(message = "Reorder level cannot be null")
    @Min(value = 0, message = "Reorder level must be 0 or more")
    private Integer reorderLevel;

    private LocalDateTime updatedAt;

    @NotNull(message = "Product ID cannot be null")
    private Long productId; 

    public InventoryDTO() {
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}