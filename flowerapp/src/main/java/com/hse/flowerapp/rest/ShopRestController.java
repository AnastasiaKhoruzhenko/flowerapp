package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.domain.Status;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.ItemCountDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.AddressService;
import com.hse.flowerapp.service.ItemService;
import com.hse.flowerapp.service.ShopService;
import com.hse.flowerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping(value = "/api/owner/shop/")
public class ShopRestController {
    private final UserService userService;
    private final ShopService shopService;
    private final AddressService addressService;
    private final ItemService itemService;

    @Autowired
    public ShopRestController(UserService userService, ShopService shopService, AddressService addressService, ItemService itemService) {
        this.userService = userService;
        this.shopService = shopService;
        this.addressService = addressService;
        this.itemService = itemService;
    }

    // добавление товара в магазин
    @PostMapping(value = "add/item")
    public ResponseEntity addItemToShop(@Validated ItemDto itemDto){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);


        itemDto.setUserId(user.getId());
        itemDto.setShopId(user.getShop().getId());
        Item item = itemService.addItemToShop(itemDto);

        return ResponseEntity.ok(itemDto);
    }

    // обновление информации о конкретном товаре магазина
    @PostMapping(value = "item/update")
    public ResponseEntity<String> updateItem(@RequestBody ItemDto itemDto){
        Item item = itemService.updateItemInfo(itemDto);

        return new ResponseEntity<>("Item with id = " + item.getId() + " was updated", HttpStatus.OK);
    }

    // обновление информации о конкретном товаре магазина
    @PostMapping(value = "shop/update")
    public ResponseEntity<String> updateShopInfo(@RequestBody Shop shop){
        shopService.updateShopInfo(shop);

        return new ResponseEntity<>("Shop with id = " + shop.getId() + " was updated", HttpStatus.OK);
    }

    // отправка на подтвержение магазина
    @PostMapping(value = "confirm")
    public ResponseEntity<String> sendToConfirmation(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);


        Shop shop = shopService.getShopById(user.getShop().getId());
        shop.setStatus(Status.ON_CONFIRMATION);
        shopService.updateShopInfo(shop);

        return ResponseEntity.ok("on confirmation");
    }

    // получение количества товаров в магазине пользователя
    @GetMapping(value = "itemscount")
    public ResponseEntity getItemsCount(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        int count = user.getShop().getItemCount();
        return ResponseEntity.ok(new ItemCountDto(count));
    }
}
