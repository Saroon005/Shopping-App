package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.shoppingapp.dto.OrderDTO;
import com.group.shoppingapp.entity.Order;
import com.group.shoppingapp.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/checkout")
	public Order checkout(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }
	
	@GetMapping("/orders")
	public List<Order> fetchAllOrders(){
		return orderService.fetchAllOrders();
	}
	
	@GetMapping("/orders/{id}")
	public Order fetchOrder(@PathVariable Long id) {
		return orderService.fetchOrder(id);
	}
	
	
	
	@GetMapping("/orders/user/{userId}")
	public List<Order> fetchOrdersByUser(@PathVariable Long userId){
		
		return orderService.fetchOrdersByUser(userId);
	}
	
	
}
