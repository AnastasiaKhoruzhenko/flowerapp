package com.hse.flowerapp.repository;


import com.hse.flowerapp.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart getCartById(Long id);
}
