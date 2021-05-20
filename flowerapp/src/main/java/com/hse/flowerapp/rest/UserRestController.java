package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Address;
import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.*;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.AddressService;
import com.hse.flowerapp.service.ShopService;
import com.hse.flowerapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.List;

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

    @GetMapping(value = "getbytoken")
    public ResponseEntity<UserDto> getUserByToken() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);

        return ResponseEntity.ok(UserDto.toUserDto(userService.findByEmail(jwtTokenProvider.getUsername(token))));
    }

    // добавить информации о пользователе
    @PostMapping(value = "info")
    public ResponseEntity setUserInfo(@Validated UserDto userDto){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);

        // добавить полученную информацю пользователю
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        if(userDto.getName() != null)
            user.setName(userDto.getName());

        if(userDto.getSurname() != null)
            user.setSurname(userDto.getSurname());

        if(userDto.getPhone() != null)
            user.setPhone(userDto.getPhone());

        if(userDto.getBirthday() != null)
            user.setBirthday(userDto.getBirthday());

        userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "shops")
    public ResponseEntity getAllShops(){
        return ResponseEntity.ok(shopService.getAllShops());
    }

    @GetMapping(value = "get/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.toUserDto(user);

        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "hasshop")
    public ResponseEntity<BooleanDto> checkUserHasShop(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        log.info("in hasshop check token: "+token);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        return ResponseEntity.ok(new BooleanDto(user.getShop() != null));
    }

    // первоначальное (пустое) создание магазина
    @PostMapping(value = "create/shop")
    public ResponseEntity createShopByUserToken() {
        //User user = userService.findById(token);

        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        log.info(token);
        String username = jwtTokenProvider.getUsername(token);
        log.info(username);

        User user = userService.findByEmail(username);

        log.info(user.getId().toString());

        if (user == null) {
            return ResponseEntity.ok(new ErrorDto(true, "create/shop", "user is null"));
        }

        user = userService.addShopToUser(user);

        return ResponseEntity.ok(new IdDTO(user.getShop().getId()));
    }

    // добавление адреса магазина (сразу после пустого создания)
    @PostMapping(value = "shop/address")
    public ResponseEntity createShopAddress(@Validated AddressDto addressDto){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        addressDto.setUserId(user.getId());
        addressDto.setShopId(user.getShop().getId());

        Address address = userService.createShopAddress(addressDto);

        if(address == null)
            return ResponseEntity.ok(new ErrorDto(true, "address", "address creation failed"));

        AddressDto dto = AddressDto.toDto(address);
        dto.setShopId(user.getShop().getId());

        return ResponseEntity.ok(dto);
    }

    // отправка заполненных данных о магазине (после адреса)
    @PostMapping(value = "shopinfo")
    public ResponseEntity setShopInfo(@Validated ShopInfoDto shopInfoDto){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Date date = new Date();

        Shop shop = shopService.getShopById(user.getShop().getId());
        shop.setUpdated(date);
        shop.setItemCount(0);
        shop.setName(shopInfoDto.getShopName());
        shop.setDescription(shopInfoDto.getDescription());
        shop.setRating("0.0");
        shop.setDeliveryPrice(shopInfoDto.getDeliveryPrice());
        shop.setOpenTime(shopInfoDto.getOpenTime());

        shopService.updateShopInfo(shop);

        ShopInfoDto shopInfoDto2 = shopService.convertShopToShopInfoDto(shop);

        return ResponseEntity.ok(shopInfoDto2);
    }
}
