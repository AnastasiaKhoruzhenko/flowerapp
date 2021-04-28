package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.Cart;
import com.hse.flowerapp.dto.CartDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.ShortItemDto;

import java.util.List;

public interface CartService {

    CartDto getItemById(Long id);

    CartDto saveCart(CartDto cartDto);

    List<CartDto> getAllItems();

    void addItemToCart(ItemDto itemDto);

    void deleteItemFromCart(Long cart_id, Long item_id);

    void clearCart(Long user_id);

    List<ShortItemDto> getItemsCart(Cart cart);

    void addOneToCartItem(Long cart_id, Long item_id);

    void minusOneFromCartItem(Long cart_id, Long item_id);

//    void deleteAllItemsFromCart(Long user_id);
}
