package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.OrderDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/seller/")
public class SellerRestController {

    private final UserService userService;
    private final ShopService shopService;
    private final AddressService addressService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public SellerRestController(UserService userService, ShopService shopService, AddressService addressService, ItemService itemService, OrderService orderService) {
        this.userService = userService;
        this.shopService = shopService;
        this.addressService = addressService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping(value = "orders")
    public ResponseEntity getShopOrders(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        Long shopId = user.getWorkShop().getId();

        List<OrderDto> listOrders = orderService.getAllShopOrders(shopId);

        return ResponseEntity.ok(listOrders);
    }
}
