package com.hse.flowerapp.rest;

import com.hse.flowerapp.dto.OrderDto;
import com.hse.flowerapp.service.ItemService;
import com.hse.flowerapp.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/order/")
public class OrderRestController {

    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public OrderRestController(ItemService itemService, OrderService orderService) {
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @PostMapping(value = "create")
    public ResponseEntity<String> createOrder(@RequestBody OrderDto order){
        orderService.createOrder(order);

        return new ResponseEntity<>("Order created", HttpStatus.OK);
    }
}
