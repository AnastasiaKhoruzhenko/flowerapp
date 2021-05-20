package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.OrderStatus;
import com.hse.flowerapp.dto.ChangeOrderStatusDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto getItemById(Long order_id);

    List<OrderDto> getOrdersByUserId(Long user_id);

    Order createOrder(OrderDto orderDto);

    OrderDto cancelOrder(OrderDto orderDto);

    List<OrderDto> getAllShopOrders(Long id);

    OrderDto changeOrderStatus(Long order_id, OrderStatus orderStatus);

    OrderDto takeOrder(Long order_id, Integer seller_id);

    List<ItemDto> getAllOrderItems(Long order_id);

}
