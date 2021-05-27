package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.ShopDto;
import com.hse.flowerapp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/admin/")
public class AdminRestController {

    private final UserService userService;
    private final ShopService shopService;
    private final AddressService addressService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public AdminRestController(UserService userService, ShopService shopService, AddressService addressService, ItemService itemService, OrderService orderService) {
        this.userService = userService;
        this.shopService = shopService;
        this.addressService = addressService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping(value = "request")
    public ResponseEntity getShopOrders(){
        List<ShopDto> shopDtoList = shopService.getAllRequestShops();

        return ResponseEntity.ok(shopDtoList);
    }

    @GetMapping(value = "{id}/info")
    public ResponseEntity getShopInfo(@PathVariable("id") Integer id){

        ShopDto shopDto = shopService.getShopInfo(id);

        return ResponseEntity.ok(shopDto);
    }

    @GetMapping(value = "{id}/items")
    public ResponseEntity getAllShopItems(@PathVariable("id") Long id){
        List<ItemDto> itemDtoList = itemService.getAllItems();
        itemDtoList.removeIf(itemDto -> !itemDto.getShopId().toString().equals(id.toString()));

        return ResponseEntity.ok(itemDtoList);
    }

    @PostMapping(value = "confirm")
    public ResponseEntity confirmShop(@Validated Long id) {
        ShopDto shopDto = shopService.confirmShop(id);

        return ResponseEntity.ok(shopDto);
    }
}
