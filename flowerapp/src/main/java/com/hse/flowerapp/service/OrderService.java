package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto getItemById(Long order_id);

    List<OrderDto> getOrdersByUserId(Long user_id);

    Order createOrder(OrderDto orderDto);

    OrderDto cancelOrder(OrderDto orderDto);


}
