package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
