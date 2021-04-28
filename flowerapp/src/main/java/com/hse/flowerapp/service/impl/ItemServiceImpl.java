package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.ItemSize;
import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.repository.ItemRepository;
import com.hse.flowerapp.repository.ShopRepository;
import com.hse.flowerapp.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, ShopRepository shopRepository) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public ItemDto getItemById(Long id) {
        return convertToDTO(itemRepository.findById(id).orElse(new Item()));
    }

    @Override
    public Item addItemToShop(ItemDto itemDto) {
        Item item = convertToEntity(itemDto);
        Shop shop = shopRepository.getShopById(itemDto.getShopId());
        shop.setItemCount(shop.getItemCount() + 1);
        item.setShop(shop);
        itemRepository.save(item);
        shopRepository.save(shop);
        return item;
    }

    @Override
    public List<ItemDto> getAllItemsOfShop(Long id) {
        List<ItemDto> itemList = getAllItems();
        List<ItemDto> itemsShopList = new ArrayList<>();

        for (ItemDto item : itemList) {
            if(item.getShopId().equals(id))
                itemsShopList.add(item);
        }

        return itemsShopList;
    }

    @Override
    public Item updateItemInfo(ItemDto itemDto) {
        Item item = itemRepository.getItemById(itemDto.getItemId());
        item.setName(itemDto.getName());
        item.setShortDescription(itemDto.getShortDescription());
        item.setFullDescription(itemDto.getFullDescription());
        item.setWidth(itemDto.getWidth());
        item.setHeight(itemDto.getHeight());
        item.setPrice(itemDto.getPrice());
        item.setSize(itemDto.getSize());
        item.setAvailable(itemDto.getAvailable());

        item.setPhotoURL(itemDto.getPhotoUrl());
        item.setDiscount(itemDto.getDiscount());
        item.setDiscountPrice(itemDto.getDiscountPrice());

        itemRepository.save(item);

        return item;
    }

    @Override
    public List<ItemDto> getAllItems() {
        return ((List<Item>) itemRepository
                .findAll())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemDto convertToDTO(Item item){
        ItemDto itemDto = new ItemDto();

        itemDto.setItemId(item.getId());
        itemDto.setPhotoUrl(item.getPhotoURL());
        itemDto.setHeight(item.getHeight());
        itemDto.setWidth(item.getWidth());
        itemDto.setShortDescription(item.getShortDescription());
        itemDto.setFullDescription(item.getFullDescription());
        itemDto.setSize(item.getSize());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPrice());
        itemDto.setDiscount(item.getDiscount());
        itemDto.setDiscountPrice(item.getDiscountPrice());
        itemDto.setAvailable(item.getAvailable());
        itemDto.setCategoryName(item.getCategoryName());
        itemDto.setFlowers(item.getFlowers());

        return itemDto;
    }

    public Item convertToEntity(ItemDto itemDto){
        Item item = new Item();

        item.setPhotoURL(itemDto.getPhotoUrl());
        item.setHeight(itemDto.getHeight());
        item.setWidth(itemDto.getWidth());
        item.setShortDescription(itemDto.getShortDescription());
        item.setFullDescription(itemDto.getFullDescription());
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        item.setAvailable(itemDto.getAvailable());
        switch (itemDto.getSize()){
            case "Маленький": item.setSize(ItemSize.SMALL.toString());break;
            case "Средний": item.setSize(ItemSize.AVERAGE.toString());break;
            case "Большой": item.setSize(ItemSize.BIG.toString());break;
        }

        if(itemDto.getDiscount()!= null)
            item.setDiscount(itemDto.getDiscount());
        if(itemDto.getDiscountPrice() != null)
            item.setDiscountPrice(itemDto.getDiscountPrice());

        item.setCategoryName(itemDto.getCategoryName());
        item.setFlowers(itemDto.getFlowers());

        return item;
    }
}
