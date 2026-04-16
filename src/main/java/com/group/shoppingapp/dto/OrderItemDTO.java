package com.group.shoppingapp.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderItemDTO {

	private Long id;
	
	private Long product_id;
	
	private Long order_id;
	
	private Integer quantity;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;

	public OrderItemDTO(Long product_id, Long order_id, Integer quantity) {
		this.product_id = product_id;
		this.order_id = order_id;
		this.quantity = quantity;
	}

	public Long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Long product_id) {
		this.product_id = product_id;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
