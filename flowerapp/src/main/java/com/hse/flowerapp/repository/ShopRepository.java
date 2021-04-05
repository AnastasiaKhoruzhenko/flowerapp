package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop getShopById(Long id);

}
