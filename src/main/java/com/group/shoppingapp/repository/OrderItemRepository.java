package com.group.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.shoppingapp.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
