package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.OrderDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.CartService;
import com.hse.flowerapp.service.ItemService;
import com.hse.flowerapp.service.OrderService;
import com.hse.flowerapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@RestController
@RequestMapping(value = "/api/order/")
public class OrderRestController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @Autowired
    public OrderRestController(ItemService itemService, OrderService orderService, UserService userService, CartService cartService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping(value = "create")
    public ResponseEntity createOrder(@Validated OrderDto order){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        order.setUserId(user.getId());

        Order order1 = orderService.createOrder(order);
        cartService.clearCart(user.getId());

        return ResponseEntity.ok(OrderDto.convertToOrderDto(order1));
    }
}
