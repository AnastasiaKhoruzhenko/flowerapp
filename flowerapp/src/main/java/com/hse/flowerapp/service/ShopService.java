package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.dto.ShopDto;
import com.hse.flowerapp.dto.ShopInfoDto;
import com.hse.flowerapp.dto.UserDto;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShopService {

    Shop getShopById(Long id);

    void updateShopInfo(Shop shop);

    ShopDto convertToDTO(Shop shop);

    ShopInfoDto convertShopToShopInfoDto(Shop shop);

    Shop convertToEntity(ShopDto shopDto);

    List<ShopDto> getAllShops();

    List<ShopDto> getAllRequestShops();

    ShopDto getShopInfo(Integer id);

    ShopDto confirmShop(Long id);
}
