package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Cart;
import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.ItemCart;
import com.hse.flowerapp.dto.CartDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.repository.*;
import com.hse.flowerapp.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private ItemServiceImpl itemServiceImpl;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private final ItemCartRepository itemCartRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, ItemServiceImpl itemServiceImpl, ShopRepository shopRepository, UserRepository userRepository, ItemRepository itemRepository, ItemCartRepository itemCartRepository) {
        this.cartRepository = cartRepository;
        this.itemServiceImpl = itemServiceImpl;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemCartRepository = itemCartRepository;
    }

    @Override
    public List<CartDto> getAllItems() {
        return ((List<Cart>) cartRepository
                .findAll())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CartDto getItemById(Long id) { return convertToDTO(cartRepository.findById(id).orElse(new Cart())); }

    @Override
    public CartDto saveCart(CartDto cartDto) {
        Cart cart  = convertToEntity(cartDto);
        cart = cartRepository.save(cart);
        return convertToDTO(cart);
    }

    @Override
    public void addItemToCart(ItemDto itemDto) {
        boolean isFound = false;
        Item item = itemRepository.getItemById(itemDto.getItemId());
        Cart cart = cartRepository.getCartById(userRepository.getById(itemDto.getUserId()).getCart().getId());

        Iterable<ItemCart> itemCartList = itemCartRepository.findAll();

        for(ItemCart itemCart : itemCartList){
            if(itemCart.getCart().getId().equals(cart.getId()) && itemCart.getItem().getId().equals(item.getId())){
                isFound = true;
                itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() + 1);
                itemCartRepository.save(itemCart);

                break;
            }
        }

        if(!isFound){
            ItemCart itemCart = new ItemCart();
            itemCart.setItem(item);
            itemCart.setCart(cart);
            itemCart.setCountOfThisItemInCart(1);
            itemCartRepository.save(itemCart);

            List<ItemCart> itemCartList1 = item.getCountItemInCarts();
            itemCartList1.add(itemCart);
            item.setCountItemInCarts(itemCartList1);
            itemRepository.save(item);

            List<ItemCart> itemCartList2 = cart.getCountItemInCarts();
            itemCartList2.add(itemCart);
            cart.setCountItemInCarts(itemCartList2);
            cartRepository.save(cart);

        }

//        List<Cart> cartList = item.getCartList();
//        List<Item> itemList = cart.getItemList();
//        cartList.add(cart);
//        itemList.add(item);
//
//        item.setCartList(cartList);
//        cart.setItemList(itemList);
//
//        itemRepository.save(item);
//        cartRepository.save(cart);
    }

    @Override
    public void deleteItemFromCart(ItemDto itemDto) {
        Item item = itemRepository.getItemById(itemDto.getItemId());
        Cart cart = cartRepository.getCartById(userRepository.getById(itemDto.getUserId()).getCart().getId());

        Iterable<ItemCart> itemCartList = itemCartRepository.findAll();

        for(ItemCart itemCart : itemCartList){
            if(itemCart.getCart().getId().equals(cart.getId()) && itemCart.getItem().getId().equals(item.getId())){
                itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() - 1);
                itemCartRepository.save(itemCart);

                // khor проверить потом точно ли надо проверять на 0
                if(itemCart.getCountOfThisItemInCart() == 0){
                    itemCartRepository.delete(itemCart);

                    List<ItemCart> itemCartList1 = item.getCountItemInCarts();
                    itemCartList1.remove(itemCart);
                    item.setCountItemInCarts(itemCartList1);
                    itemRepository.save(item);

                    List<ItemCart> itemCartList2 = cart.getCountItemInCarts();
                    itemCartList2.remove(itemCart);
                    cart.setCountItemInCarts(itemCartList2);
                    cartRepository.save(cart);
                }

                break;
            }
        }

//        List<Cart> cartList = item.getCartList();
//        List<Item> itemList = cart.getItemList();
//        cartList.remove(cart);
//        itemList.remove(item);
//
//        item.setCartList(cartList);
//        cart.setItemList(itemList);
//
//        itemRepository.save(item);
//        cartRepository.save(cart);
    }

//    @Override
//    public void deleteAllItemsFromCart(Long user_id) {
//        Cart cart = cartRepository.getCartById(userRepository.getById(user_id).getCart().getId());
//
//        List<Item> itemList = cart.getItemList();
//        if(itemList != null) {
//            for (Item item : itemList) {
//                itemList.remove(item);
//                List<Cart> thisCartList = item.getCartList();
//                for(Cart cart1 : thisCartList){
//                    if(cart1.getId().equals(cart.getId()))
//                    {
//                        thisCartList.remove(cart1);
//                        item.setCartList(thisCartList);
//                        itemRepository.save(item);
//
//                        cart.setItemList(itemList);
//                        cartRepository.save(cart);
//                    }
//                }
//            }
//            cart.setItemList(itemList);
//            cartRepository.save(cart);
//        }
//    }

    private CartDto convertToDTO(Cart cart){
        CartDto cartDto = new CartDto();

        cartDto.setDiscountSum(cart.getDiscountSum());
        cartDto.setPromocode(cart.getPromocode());
        cartDto.setTotalCount(cart.getTotalCount());
        cartDto.setTotalSum(cart.getTotalSum());
        cartDto.setUser(cart.getUser());

        return cartDto;
    }

    private Cart convertToEntity(CartDto cartDto){
        Cart cart = new Cart();

        cart.setDiscountSum(cartDto.getDiscountSum());
        cart.setPromocode(cartDto.getPromocode());
        cart.setTotalCount(cartDto.getTotalCount());
        cart.setTotalSum(cartDto.getTotalSum());
        cart.setUser(cartDto.getUser());

        return cart;
    }
}
