package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.Shop;
import org.springframework.stereotype.Service;

@Service
public interface ShopService {

    Shop getShopById(Long id);

    void updateShopInfo(Shop shop);


}
