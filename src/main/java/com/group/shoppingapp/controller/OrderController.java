package com.group.shoppingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.shoppingapp.dto.OrderDTO;
import com.group.shoppingapp.entity.Order;
import com.group.shoppingapp.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/checkout")
	public Order checkout(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }
	
	
	
}
