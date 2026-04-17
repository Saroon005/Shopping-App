package com.group.shoppingapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group.shoppingapp.dto.OrderDTO;
import com.group.shoppingapp.dto.OrderItemDTO;
import com.group.shoppingapp.entity.Order;
import com.group.shoppingapp.entity.OrderItem;
import com.group.shoppingapp.entity.OrderStatus;
import com.group.shoppingapp.entity.Product;
import com.group.shoppingapp.repository.OrderRepository;
import com.group.shoppingapp.repository.ProductRepository;

@Service
public class OrderService {

	@Autowired
    private ProductRepository productRepo;

    @Autowired
    private OrderRepository orderRepo;

    public Order createOrder(OrderDTO orderDTO) {

        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);
        order.setOrderDate(LocalDateTime.now());

        Double totalCost = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO itemDTO : orderDTO.getOrderItemsList()) {

            Product product = productRepo.findById(itemDTO.getProduct_id())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setOrder(order);

            totalCost += itemDTO.getQuantity() * product.getPrice();

            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalCost);
        order.setOrderItemList(orderItems);

        return orderRepo.save(order);
    }
	
}
