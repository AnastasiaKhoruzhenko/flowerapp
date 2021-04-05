package com.hse.flowerapp.service;

import com.hse.flowerapp.dto.CartDto;
import com.hse.flowerapp.dto.ItemDto;

import java.util.List;

public interface CartService {

    CartDto getItemById(Long id);

    CartDto saveCart(CartDto cartDto);

    List<CartDto> getAllItems();

    void addItemToCart(ItemDto itemDto);

    void deleteItemFromCart(ItemDto itemDto);

//    void deleteAllItemsFromCart(Long user_id);
}
