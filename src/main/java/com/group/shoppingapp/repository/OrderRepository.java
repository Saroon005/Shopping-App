package com.group.shoppingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.shoppingapp.entity.Order;
import java.util.List;
import org.apache.catalina.User;


public interface OrderRepository extends JpaRepository<Order, Long>{
	public List<Order> findByUser(com.group.shoppingapp.entity.User user);
}
