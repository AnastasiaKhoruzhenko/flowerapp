package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.dto.ShopDto;
import com.hse.flowerapp.repository.ShopRepository;
import com.hse.flowerapp.repository.UserRepository;
import com.hse.flowerapp.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, UserRepository userRepository) {
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Shop getShopById(Long id) {
        return shopRepository.getShopById(id);
    }

    @Override
    public void updateShopInfo(Shop shop) {
        shopRepository.save(shop);
    }

    private ShopDto convertToDTO(Shop shop){
        ShopDto shopDto = new ShopDto();

        shopDto.setDeliveryPrice(shop.getDeliveryPrice());
        shopDto.setOpenTime(shop.getOpenTime());
        shopDto.setRating(shop.getRating());
        shopDto.setShopName(shop.getName());
        shopDto.setDescription(shop.getDescription());
        shopDto.setAddressId(shop.getShopAddress().getId());
        shopDto.setUserId(shop.getUser().getId());
        shopDto.setItemCount(shop.getItemCount());

        return shopDto;
    }

    private Shop convertToEntity(ShopDto shopDto){
        Shop shop = new Shop();

        shop.setDeliveryPrice(shopDto.getDeliveryPrice());
        shop.setOpenTime(shopDto.getOpenTime());
        shop.setRating(shopDto.getRating());
        shop.setName(shopDto.getShopName());
        shop.setDescription(shopDto.getDescription());
        shop.setItemCount(shopDto.getItemCount());
        //shop.setShopAddress(shopDto.getAddressId());
        //shop.setUser(shopDto.getUserId());

        return shop;
    }

}
