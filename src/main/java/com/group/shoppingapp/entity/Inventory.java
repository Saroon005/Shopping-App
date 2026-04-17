package com.group.shoppingapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private Integer stockQuantity;
    private Integer threshold;
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    
    public Inventory() {
    	this.createdAt = LocalDateTime.now();
    }

	public Inventory(Long inventoryId, Integer stockQuantity, Integer threshold) {
		this.inventoryId = inventoryId;
		this.stockQuantity = stockQuantity;
		this.threshold = threshold;
		this.createdAt = LocalDateTime.now();
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}  
}
