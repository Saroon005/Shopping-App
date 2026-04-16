package com.group.shoppingapp.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.group.shoppingapp.entity.OrderItem;
import com.group.shoppingapp.entity.OrderStatus;
import com.group.shoppingapp.entity.Product;
import com.group.shoppingapp.repository.OrderRepository;
import com.group.shoppingapp.repository.ProductRepository;

public class OrderDTO {

	private Long user_id;		
	
	private List<OrderItemDTO> orderItemsList;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public List<OrderItemDTO> getOrderItemsList() {
		return orderItemsList;
	}

	public void setOrderItemsList(List<OrderItemDTO> orderItemsList) {
		this.orderItemsList = orderItemsList;
	}
	

	
}
