package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.ReviewDto;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.ItemService;
import com.hse.flowerapp.service.ReviewService;
import com.hse.flowerapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping(value = "/api/items/")
public class ItemRestController {

    private final ItemService itemService;
    private final ReviewService reviewService;
    private final UserService userService;

    @Autowired
    public ItemRestController(ItemService itemService, ReviewService reviewService, UserService userService) {
        this.itemService = itemService;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity getAll(){
        List<ItemDto> itemDtoList = itemService.getAllItems();
        return ResponseEntity.ok(itemDtoList);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ResponseEntity getItemById(@PathVariable("id") Long id){
        return ResponseEntity.ok(itemService.getItemById(id));
    }

    @PostMapping(value = "rate")
    public ResponseEntity rateItem(@Validated ReviewDto reviewDto){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        reviewDto.setUserId(user.getId());
        reviewDto.setHeader(user.getEmail());

        return ResponseEntity.ok(reviewService.createItemReview(reviewDto));
    }
}

