package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.ChangeOrderStatusDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.OrderDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/order/")
public class OrderRestController {

    private final ItemService itemService;
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final ReviewService reviewService;

    @Autowired
    public OrderRestController(ItemService itemService, OrderService orderService, UserService userService, CartService cartService, ReviewService reviewService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
        this.reviewService = reviewService;
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

    @GetMapping(value = "list")
    public ResponseEntity getAllOrders() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        List<Order> orderList = user.getOrderList();
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(Order order : orderList) {
            orderDtoList.add(OrderDto.convertToOrderDto(order));
        }

        return ResponseEntity.ok(orderDtoList);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity getOrderById(@PathVariable("id") Long id) {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        return ResponseEntity.ok(orderService.getItemById(id));
    }

    @PostMapping(value = "status")
    public ResponseEntity changeOrderStatus(@Validated ChangeOrderStatusDto changeOrderStatusDto){
        OrderDto orderDto = orderService.changeOrderStatus(changeOrderStatusDto.getOrderId(), changeOrderStatusDto.getStatus());

        return ResponseEntity.ok(orderDto);
    }

    @PostMapping(value = "take")
    public ResponseEntity takeOrder(@Validated Long order_id){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        OrderDto order = orderService.takeOrder(order_id, Integer.valueOf(user.getId().toString()));

        return ResponseEntity.ok(order);
    }

    @GetMapping(value = "{id}/items")
    public ResponseEntity getAllOrderItems(@PathVariable("id") Long id){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);
        List<ItemDto> itemDtoList = orderService.getAllOrderItems(id);

        log.info(String.valueOf(itemDtoList.size()));

        return ResponseEntity.ok(itemDtoList);
    }
}
