package com.hse.flowerapp.rest;

import com.hse.flowerapp.dto.CartDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.service.CartService;
import com.hse.flowerapp.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/cart/")
public class CartRestController {

    private final CartService cartService;
    private final ItemService itemService;

    @Autowired
    public CartRestController(CartService cartService, ItemService itemService){
        this.cartService = cartService;
        this.itemService = itemService;
    }

//    @GetMapping("{id}")
//    public CartDto getItemsInCartbyId(@PathVariable(value = "id") Long id){
//
//    }

    // добавить товар в корзину
    @PostMapping(value = "item")
    public ResponseEntity<String> addItemToCart(@RequestBody ItemDto itemDto){
        cartService.addItemToCart(itemDto);

        return new ResponseEntity<>("Item with id = " + itemDto.getItemId() + " was added to cart", HttpStatus.OK);
    }

    // удалить товар из корзины
    @DeleteMapping(value = "item")
    public ResponseEntity<String> deleteItemFromCart(@RequestBody ItemDto itemDto) {
        cartService.deleteItemFromCart(itemDto);

        return new ResponseEntity<>("Item with id = " + itemDto.getItemId() + " was deleted from cart", HttpStatus.OK);
    }

//    // очистить корзину
//    @DeleteMapping(value = "item/all")
//    public ResponseEntity<String> deleteAllItemsFromCart(@RequestBody Long userId) {
//        cartService.deleteAllItemsFromCart(userId);
//
//        return new ResponseEntity<>("All items were deleted", HttpStatus.OK);
//    }

    @PostMapping(value = "save")
    public CartDto saveCart(@RequestBody CartDto cartDto){
        return cartService.saveCart(cartDto);
    }



}
