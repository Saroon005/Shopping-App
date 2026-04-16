package com.group.shoppingapp.dto;

import java.time.LocalDateTime;

import com.group.shoppingapp.entity.OrderStatus;

public class OrderDTO {

	private Long id;
	
	private Long user_id;
	
	private LocalDateTime orderDate;
	
	private Double totalAmount;
	
	private LocalDateTime updatedAt;
	
	private OrderStatus orderStatus;

	
	public OrderDTO(Long user_id, Double totalAmount, OrderStatus orderStatus) {
		this.user_id = user_id;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
	}


	public Long getUser_id() {
		return user_id;
	}


	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}


	public Double getTotalAmount() {
		return totalAmount;
	}


	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
