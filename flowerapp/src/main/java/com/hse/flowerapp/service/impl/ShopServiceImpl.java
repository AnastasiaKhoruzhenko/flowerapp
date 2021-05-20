package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.dto.ShopDto;
import com.hse.flowerapp.dto.ShopInfoDto;
import com.hse.flowerapp.repository.ShopRepository;
import com.hse.flowerapp.repository.UserRepository;
import com.hse.flowerapp.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<ShopDto> getAllShops() {
        List<Shop> shopList = shopRepository.findAll();
        List<ShopDto> shopDtoList = new ArrayList<>();
        for (Shop shop : shopList) {
            shopDtoList.add(convertToDTO(shop));
        }

        return shopDtoList;
    }

    @Override
    public ShopDto convertToDTO(Shop shop) {
        ShopDto shopDto = new ShopDto();

        shopDto.setShopId(Integer.valueOf(shop.getId().toString()));

        shopDto.setDeliveryPrice(shop.getDeliveryPrice());
        shopDto.setOpenTime(shop.getOpenTime());
        shopDto.setRating(shop.getRating());
        shopDto.setShopName(shop.getName());
        shopDto.setDescription(shop.getDescription());
        shopDto.setStatus(shop.getStatus().toString());
        shopDto.setItemCount(shop.getItemCount());

        return shopDto;
    }

    @Override
    public ShopInfoDto convertShopToShopInfoDto(Shop shop) {
        ShopInfoDto shopInfoDto = new ShopInfoDto();

        shopInfoDto.setDeliveryPrice(shop.getDeliveryPrice());
        shopInfoDto.setOpenTime(shop.getOpenTime());
        shopInfoDto.setShopName(shop.getName());
        shopInfoDto.setDescription(shop.getDescription());

        return shopInfoDto;
    }

    @Override
    public Shop convertToEntity(ShopDto shopDto) {
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
