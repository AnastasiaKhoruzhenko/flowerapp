package com.hse.flowerapp.service;

import com.hse.flowerapp.domain.Address;
import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.Review;
import com.hse.flowerapp.domain.User;
import com.hse.flowerapp.dto.AddressDto;

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
}