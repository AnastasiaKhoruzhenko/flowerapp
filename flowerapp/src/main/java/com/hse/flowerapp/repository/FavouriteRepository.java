package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Favourite getById(Long id);
}
