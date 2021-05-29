package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.*;
import com.hse.flowerapp.dto.AddressDto;
import com.hse.flowerapp.dto.FeedbackDto;
import com.hse.flowerapp.dto.ItemDto;

import java.util.List;

public interface UserService {
    User register(User user, String role);

    List<User> getAll();

    User findById(Long id);

    User findByEmail(String name);

    void delete(Long id);

    List<Order> getUserOrders(Long userId);

    //Shop getUserShop(Long userId);

    List<Review> getUserReview(Long userId);

    void update(User user);

    User addShopToUser(User user);

    List<Address> getUserAddresses(Long id);

    void deleteUserAddress(Long user_d, Address address);

    Address createShopAddress(AddressDto addressDto);

    Address createPersonalAddress(AddressDto addressDto);

    void saveUser(User user);

    List<Item> addToFavourites(Long item_id, Long user_id);

    List<Item> removeFromFavourites(Long item_id, Long user_id);

    List<ItemDto> getFavouriteList(Long user_id);
}