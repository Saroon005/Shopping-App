package com.group.shoppingapp.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

public class OrderDTO {

    @NotNull(message = "User ID cannot be null")
    private Long user_id;

    @NotEmpty(message = "Order items list cannot be empty")
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