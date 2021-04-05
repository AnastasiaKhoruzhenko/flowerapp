package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.domain.Status;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.service.AddressService;
import com.hse.flowerapp.service.ItemService;
import com.hse.flowerapp.service.ShopService;
import com.hse.flowerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/seller/shop/")
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
    public ResponseEntity<String> addItemToShop(@RequestBody ItemDto itemDto){
        Item item = itemService.addItemToShop(itemDto);

        return new ResponseEntity<>("Item with id = " + item.getId() + " was added to shop with id = " + item.getShop().getId(), HttpStatus.OK);
    }

    // обновление информации о конкретном товаре магазина
    @PostMapping(value = "update/item")
    public ResponseEntity<String> updateItem(@RequestBody ItemDto itemDto){
        Item item = itemService.updateItemInfo(itemDto);

        return new ResponseEntity<>("Item with id = " + item.getId() + " was updated", HttpStatus.OK);
    }

    // обновление информации о конкретном товаре магазина
    @PostMapping(value = "update/shop")
    public ResponseEntity<String> updateShopInfo(@RequestBody Shop shop){
        shopService.updateShopInfo(shop);

        return new ResponseEntity<>("Shop with id = " + shop.getId() + " was updated", HttpStatus.OK);
    }

    // отправка на подтвержение магазина
    @PostMapping(value = "{shop_id}/confirm")
    public ResponseEntity<String> sendToConfirmation(@PathVariable("shop_id") Long id){
        Shop shop = shopService.getShopById(id);
        shop.setStatus(Status.ON_CONFIRMATION);
        shopService.updateShopInfo(shop);

        return new ResponseEntity<>("Status updated to ON_CONFIRMATION", HttpStatus.OK);
    }
}
