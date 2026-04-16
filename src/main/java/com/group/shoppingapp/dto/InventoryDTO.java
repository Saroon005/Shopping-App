package com.group.shoppingapp.dto;

import java.time.LocalDateTime;

public class InventoryDTO {
	private Long inventoryId;

    private Integer availableQuantity;
    private Integer reorderLevel;
    private LocalDateTime updatedAt;
    
    public InventoryDTO() {
    	
    }
    
	public InventoryDTO(Long inventoryId, Integer availableQuantity, Integer reorderLevel) {
		this.inventoryId = inventoryId;
		this.availableQuantity = availableQuantity;
		this.reorderLevel = reorderLevel;
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
    
    

}
