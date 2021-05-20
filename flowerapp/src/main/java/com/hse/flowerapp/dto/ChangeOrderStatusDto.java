package com.hse.flowerapp.dto;

import com.hse.flowerapp.domain.OrderStatus;

public class ChangeOrderStatusDto {
    public Long orderId;
    public OrderStatus status;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
