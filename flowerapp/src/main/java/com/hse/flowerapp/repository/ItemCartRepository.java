package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.ItemCart;
import org.springframework.data.repository.CrudRepository;

public interface ItemCartRepository extends CrudRepository<ItemCart, Long> {
}
