package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Address;
import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.AddressDto;
import com.hse.flowerapp.dto.SaveShopDto;
import com.hse.flowerapp.dto.UserDto;
import com.hse.flowerapp.service.AddressService;
import com.hse.flowerapp.service.ShopService;
import com.hse.flowerapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/api/user/")
public class UserRestController {
    private final UserService userService;
    private final ShopService shopService;
    private final AddressService addressService;

    @Autowired
    public UserRestController(UserService userService, ShopService shopService, AddressService addressService) {
        this.userService = userService;
        this.shopService = shopService;
        this.addressService = addressService;
    }

    @GetMapping(value = "get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // отправка заполненных данных о магазине
    @PostMapping(value = "shopinfo")
    public ResponseEntity<String> setShopInfoByUserId(@RequestBody SaveShopDto saveShopDto){
        User user = userService.findById(saveShopDto.getUserId());

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Date date = new Date();

        Address address = userService.createShopAddress(saveShopDto.getAddress());

        Shop shop = shopService.getShopById(saveShopDto.getShopId());
        shop.setUpdated(date);
        shop.setItemCount(0);
        shop.setShopAddress(address);
        shop.setName(saveShopDto.getShopName());
        shop.setDescription(saveShopDto.getDescription());
        shop.setRating("0.0");
        shop.setDeliveryPrice(saveShopDto.getDeliveryPrice());
        shop.setOpenTime(saveShopDto.getOpenTime());

        shopService.updateShopInfo(shop);

        return new ResponseEntity<>("Shop for used with id: " + user.getId() + " was added with address "
                + shop.getShopAddress().toString() + " and id = " + shop.getId(), HttpStatus.OK);
    }

    // первоначальное (пустое) создание магазина
    @PostMapping(value = "create/shop/{id}")
    public ResponseEntity<String> createShopByUserId(@PathVariable(name = "id") Long id) {
        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        user = userService.addShopToUser(user);

        return new ResponseEntity<>("shop created for user " + user.getEmail(), HttpStatus.OK);
    }

    @PostMapping(value = "address")
    public ResponseEntity<String> createPersonalAddress(@RequestBody AddressDto addressDto){
        Address address = userService.createPersonalAddress(addressDto);

        if(address == null)
            return new ResponseEntity<>("no", HttpStatus.NO_CONTENT);

        return new ResponseEntity<>("personal address created for user " + addressDto.getUserId(), HttpStatus.OK);
    }
}
