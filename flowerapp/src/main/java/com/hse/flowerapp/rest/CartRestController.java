package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Cart;
import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.CartDto;
import com.hse.flowerapp.dto.ErrorDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.CartService;
import com.hse.flowerapp.service.ItemService;
import com.hse.flowerapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RestController
@RequestMapping(value = "/api/cart/")
public class CartRestController {

    private final CartService cartService;
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public CartRestController(CartService cartService, ItemService itemService, UserService userService){
        this.cartService = cartService;
        this.itemService = itemService;
        this.userService = userService;
    }

    // добавить товар в корзину
    @PostMapping(value = "item")
    public ResponseEntity addItemToCart(@Validated Long id){

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);
        ItemDto itemDto = itemService.getItemById(id);

        itemDto.setUserId(user.getId());
        //itemDto.setUserId(user.getId());
        cartService.addItemToCart(itemDto);

        return ResponseEntity.ok(itemDto);
    }

    // прибавить один в корзине
    @PostMapping(value = "item/plus")
    public ResponseEntity plusOneToCartItem(@Validated Long id){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        cartService.addOneToCartItem(user.getCart().getId(), id);
        CartDto cartDto = cartService.getItemById(user.getCart().getId());
        cartDto.setItemCartList(cartService.getItemsCart(user.getCart()));

        return ResponseEntity.ok(cartDto);
    }

    // уменьшить один в корзине
    @PostMapping(value = "item/minus")
    public ResponseEntity minusOneFromCartItem(@Validated Long id){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        cartService.minusOneFromCartItem(user.getCart().getId(), id);
        CartDto cartDto = cartService.getItemById(user.getCart().getId());
        cartDto.setItemCartList(cartService.getItemsCart(user.getCart()));

        return ResponseEntity.ok(cartDto);
    }

    // получить страницу корзины со стоимостями и списком корзины
    @GetMapping(value = "items")
    public ResponseEntity getCart(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        Cart cart = user.getCart();
        CartDto cartDto = cartService.getItemById(cart.getId());
        cartDto.setItemCartList(cartService.getItemsCart(cart));

        return ResponseEntity.ok(cartDto);
    }

    // удалить товар из корзины
    @PostMapping(value = "item/del")
    public ResponseEntity deleteItemFromCart(@Validated Long id) {

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        cartService.deleteItemFromCart(user.getCart().getId(), id);
        CartDto cartDto = cartService.getItemById(user.getCart().getId());
        cartDto.setItemCartList(cartService.getItemsCart(user.getCart()));

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping("clear")
    public ResponseEntity clearCart() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        cartService.clearCart(user.getId());
        CartDto cartDto = cartService.getItemById(user.getCart().getId());
        cartDto.setItemCartList(cartService.getItemsCart(user.getCart()));

        return ResponseEntity.ok(cartDto);
    }

    @PostMapping(value = "save")
    public CartDto saveCart(@RequestBody CartDto cartDto){
        return cartService.saveCart(cartDto);
    }
}
