package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Cart;
import com.hse.flowerapp.domain.Item;
import com.hse.flowerapp.domain.ItemCart;
import com.hse.flowerapp.dto.CartDto;
import com.hse.flowerapp.dto.ItemDto;
import com.hse.flowerapp.dto.ShortItemDto;
import com.hse.flowerapp.repository.*;
import com.hse.flowerapp.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ShortItemDto> getItemsCart(Cart cart) {

        List<ItemCart> list = new ArrayList<>();
        List<ShortItemDto> result = new ArrayList<>();
        Iterable<ItemCart> itemCartList = itemCartRepository.findAll();

        for(ItemCart itemCart : itemCartList){
            if(itemCart.getCart().getId().equals(cart.getId())){
                list.add(itemCart);
            }
        }

        for(ItemCart itemCart: list){
            ShortItemDto shortItemDto = new ShortItemDto();
            shortItemDto.setItemId(itemCart.getItem().getId());
            shortItemDto.setPrice(itemCart.getItem().getPrice());
            shortItemDto.setDiscount(itemCart.getItem().getDiscount());
            shortItemDto.setDiscountPrice(itemCart.getItem().getDiscountPrice());
            shortItemDto.setShortDescription(itemCart.getItem().getShortDescription());
            shortItemDto.setName(itemCart.getItem().getName());
            shortItemDto.setPhotoUrl(itemCart.getItem().getPhotoURL());
            shortItemDto.setItemCount(itemCart.getCountOfThisItemInCart());

            result.add(shortItemDto);
        }

        return result;
    }

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

        int discountPrice = cart.getDiscountSum();
        int totalPrice = cart.getTotalSum();
        int totalCount = cart.getTotalCount();

        for(ItemCart itemCart : itemCartList){
            if(itemCart.getCart().getId().equals(cart.getId()) && itemCart.getItem().getId().equals(item.getId())){
                isFound = true;
                itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() + 1);
                itemCartRepository.save(itemCart);

                if(itemCart.getItem().getDiscountPrice() != null)
                    cart.setDiscountSum(discountPrice + itemCart.getItem().getDiscountPrice());
                else
                    cart.setDiscountSum(discountPrice + itemCart.getItem().getPrice());

                cart.setTotalSum(totalPrice + itemCart.getItem().getPrice());
                cart.setTotalCount(totalCount + 1);
                cartRepository.save(cart);

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

            if(itemCart.getItem().getDiscountPrice() != null)
                cart.setDiscountSum(discountPrice + itemCart.getItem().getDiscountPrice());
            else
                cart.setDiscountSum(discountPrice + itemCart.getItem().getPrice());
            cart.setTotalSum(totalPrice + itemCart.getItem().getPrice());
            cart.setTotalCount(totalCount + 1);
            cartRepository.save(cart);

        }
    }

    @Override
    public void clearCart(Long user_id) {
        Cart cart = cartRepository.getCartById(userRepository.getById(user_id).getCart().getId());

        List<ItemCart> cartItemList = cart.getCountItemInCarts();

        int count = cartItemList.size();

        log.info(cartItemList.get(0).toString());
        log.info(cartItemList.get(1).toString());

        for (int i = 0; i < count; i++) {
            ItemCart itemCart = cartItemList.get(0);

            itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() - 1);
            itemCartRepository.save(itemCart);

            List<ItemCart> itemCartList1 = itemCart.getItem().getCountItemInCarts();
            itemCartList1.remove(itemCart);
            itemCart.getItem().setCountItemInCarts(itemCartList1);
            itemRepository.save(itemCart.getItem());


            cart.getCountItemInCarts().remove(itemCart);
            cartRepository.save(cart);

            if (itemCart.getItem().getDiscountPrice() != null)
                cart.setDiscountSum(cart.getDiscountSum() - itemCart.getItem().getDiscountPrice());
            else
                cart.setDiscountSum(cart.getDiscountSum() - itemCart.getItem().getPrice());
            cart.setTotalSum(cart.getTotalSum() - itemCart.getItem().getPrice());
            cart.setTotalCount(cart.getTotalCount() - 1);
            cartRepository.save(cart);

            itemCartRepository.delete(itemCart);
        }

        cart.getCountItemInCarts().clear();

        cart.setDiscountSum(0);
        cart.setTotalSum(0);
        cart.setTotalCount(0);
        cart.setPromocode(null);
        cartRepository.save(cart);
    }

    @Override
    public void deleteItemFromCart(Long cart_id, Long item_id) {
        Item item = itemRepository.getItemById(item_id);
        Cart cart = cartRepository.getCartById(cart_id);

        List<ItemCart> itemCartList = cart.getCountItemInCarts();

        for(ItemCart itemCart : itemCartList){
            if(itemCart.getItem().getId().equals(item.getId())){
                itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() - 1);
                itemCartRepository.save(itemCart);

                itemCartRepository.delete(itemCart);

                List<ItemCart> itemCartList1 = item.getCountItemInCarts();
                itemCartList1.remove(itemCart);
                item.setCountItemInCarts(itemCartList1);
                itemRepository.save(item);

                List<ItemCart> itemCartList2 = cart.getCountItemInCarts();
                itemCartList2.remove(itemCart);
                cart.setCountItemInCarts(itemCartList2);
                cartRepository.save(cart);

                if(itemCart.getItem().getDiscountPrice() != null)
                    cart.setDiscountSum(cart.getDiscountSum() - itemCart.getItem().getDiscountPrice());
                else
                    cart.setDiscountSum(cart.getDiscountSum() - itemCart.getItem().getPrice());
                cart.setTotalSum(cart.getTotalSum() - itemCart.getItem().getPrice());
                cart.setTotalCount(cart.getTotalCount() - 1);
                cartRepository.save(cart);

                break;
            }
        }
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
        //cartDto.setItemCartList(cart.getCountItemInCarts());
        //cartDto.setUser(cart.getUser());

        return cartDto;
    }

    private Cart convertToEntity(CartDto cartDto){
        Cart cart = new Cart();

        cart.setDiscountSum(cartDto.getDiscountSum());
        cart.setPromocode(cartDto.getPromocode());
        cart.setTotalCount(cartDto.getTotalCount());
        cart.setTotalSum(cartDto.getTotalSum());
        //cart.setCountItemInCarts(cartDto.getItemCartList());
        //cart.setUser(cartDto.getUser());

        return cart;
    }

    @Override
    public void addOneToCartItem(Long cart_id, Long item_id) {
        Cart cart = cartRepository.getCartById(cart_id);

        List<ItemCart> itemCartList = cart.getCountItemInCarts();
        for(ItemCart itemCart: itemCartList){
            if(itemCart.getItem().getId().equals(item_id)){
                itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() + 1);

                cart.setTotalCount(cart.getTotalCount() + 1);
                cart.setTotalSum(cart.getTotalSum() + itemCart.getItem().getPrice());
                if(itemCart.getItem().getDiscountPrice() != null)
                    cart.setDiscountSum(cart.getDiscountSum() + itemCart.getItem().getDiscountPrice());
                else
                    cart.setDiscountSum(cart.getDiscountSum() + itemCart.getItem().getPrice());
            }
        }

        cart.setCountItemInCarts(itemCartList);
        cartRepository.save(cart);
    }

    @Override
    public void minusOneFromCartItem(Long cart_id, Long item_id) {
        Cart cart = cartRepository.getCartById(cart_id);

        List<ItemCart> itemCartList = cart.getCountItemInCarts();
        for(ItemCart itemCart: itemCartList){
            if(itemCart.getItem().getId().equals(item_id)){
                itemCart.setCountOfThisItemInCart(itemCart.getCountOfThisItemInCart() - 1);

                cart.setTotalCount(cart.getTotalCount() - 1);
                cart.setTotalSum(cart.getTotalSum() - itemCart.getItem().getPrice());
                if(itemCart.getItem().getDiscountPrice() != null)
                    cart.setDiscountSum(cart.getDiscountSum() - itemCart.getItem().getDiscountPrice());
                else
                    cart.setDiscountSum(cart.getDiscountSum() - itemCart.getItem().getPrice());
            }
        }

        cart.setCountItemInCarts(itemCartList);
        cartRepository.save(cart);
    }
}
