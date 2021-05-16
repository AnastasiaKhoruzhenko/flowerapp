package com.hse.flowerapp.service;


import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.dto.ItemDto;

import java.util.List;

public interface ItemService {

    List<ItemDto> getAllItems();

    ItemDto getItemById(Long id);

    Item addItemToShop(ItemDto itemDto);

    ItemDto updateItemInfo(ItemDto itemDto);

    List<ItemDto> getAllItemsOfShop(Long id);
}
