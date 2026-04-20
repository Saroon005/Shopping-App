package com.group.shoppingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group.shoppingapp.dto.OrderDTO;
import com.group.shoppingapp.entity.Order;
import com.group.shoppingapp.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderService.createOrder(orderDTO);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> fetchAllOrders() {
        return ResponseEntity.ok(orderService.fetchAllOrders());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> fetchOrder(@PathVariable Long id) {
        Order order = orderService.fetchOrder(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<Order>> fetchOrdersByUser(@PathVariable Long userId) {
        List<Order> orders = orderService.fetchOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }
}