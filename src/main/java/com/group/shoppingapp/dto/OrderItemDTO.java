package com.group.shoppingapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class OrderItemDTO {

    @NotNull(message = "Product ID cannot be null")
    private Long product_id;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public OrderItemDTO() {} // IMPORTANT: default constructor needed

    public OrderItemDTO(Long product_id, Integer quantity) {
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}