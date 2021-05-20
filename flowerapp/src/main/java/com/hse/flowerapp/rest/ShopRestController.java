package com.hse.flowerapp.rest;

import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.Shop;
import com.hse.flowerapp.domain.Status;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.*;
import com.hse.flowerapp.security.jwt.JwtTokenProvider;
import com.hse.flowerapp.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/owner/shop/")
public class ShopRestController {
    private final UserService userService;
    private final ShopService shopService;
    private final AddressService addressService;
    private final ItemService itemService;
    private final OrderService orderService;

    @Autowired
    public ShopRestController(UserService userService, ShopService shopService, AddressService addressService, ItemService itemService, OrderService orderService) {
        this.userService = userService;
        this.shopService = shopService;
        this.addressService = addressService;
        this.itemService = itemService;
        this.orderService = orderService;
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
        itemDto.setItemId(item.getId());

        return ResponseEntity.ok(itemDto);
    }

    // обновление информации о конкретном товаре магазина
    @PostMapping(value = "item/update")
    public ResponseEntity updateItem(@Validated ItemDto itemDto){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        ItemDto item = itemService.updateItemInfo(itemDto);

        return ResponseEntity.ok(item);
    }

    // обновление информации о конкретном товаре магазина
    @PostMapping(value = "shop/update")
    public ResponseEntity<String> updateShopInfo(@RequestBody Shop shop){
        shopService.updateShopInfo(shop);

        return new ResponseEntity<>("Shop with id = " + shop.getId() + " was updated", HttpStatus.OK);
    }

    @GetMapping(value = "lockbuttons")
    public ResponseEntity getLockedButtons(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);
        Shop shop = user.getShop();

        LockedButtonsDto lockedButtonsDto = new LockedButtonsDto();
        lockedButtonsDto.setLockAddress(shop.getShopAddress() != null);
        lockedButtonsDto.setLockAddShopInfo(shop.getName() != null);
        lockedButtonsDto.setLockAddItems(shop.getItemCount() >= 20);
        lockedButtonsDto.setShowConfirmButton(shop.getItemCount() >= 20);

        return ResponseEntity.ok(lockedButtonsDto);
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

    @GetMapping(value = "status")
    public ResponseEntity getShopStatus(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        StringDto stringDto = new StringDto();
        stringDto.setMyString(user.getShop().getStatus().toString());
        //log.info(user.getShop().getStatus().toString());

        return ResponseEntity.ok(stringDto);
    }

    @GetMapping(value = "all")
    public ResponseEntity getAllShopItems(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        Long shopId = user.getShop().getId();

        List<ItemDto> itemDtoList = itemService.getAllItems();
        log.info(String.valueOf(itemDtoList.size()));
        log.info(shopId.toString());
        log.info(itemDtoList.get(0).getShopId().toString());
        itemDtoList.removeIf(itemDto -> !itemDto.getShopId().toString().equals(shopId.toString()));
        log.info(String.valueOf(itemDtoList.size()));
        return ResponseEntity.ok(itemDtoList);
    }

    @GetMapping(value = "orders")
    public ResponseEntity getAllShopOrders(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        Long shopId = user.getShop().getId();

        List<OrderDto> listOrders = orderService.getAllShopOrders(shopId);

        return ResponseEntity.ok(listOrders);
    }

    @GetMapping(value = "info")
    public ResponseEntity getShopInfo(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        ShopDto shopDto = shopService.convertToDTO(user.getShop());

        return ResponseEntity.ok(shopDto);
    }

    @GetMapping(value = "sellers")
    public ResponseEntity getShopSellers(){
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        token = token.substring(7);
        String username = jwtTokenProvider.getUsername(token);
        User user = userService.findByEmail(username);

        List<User> sellersList = user.getShop().getSellersList();
        List<UserDto> sellersDtoList = new ArrayList<>();
        for (User seller : sellersList) {
            sellersDtoList.add(UserDto.toUserDto(seller));
        }

        return ResponseEntity.ok(sellersDtoList);
    }
}
