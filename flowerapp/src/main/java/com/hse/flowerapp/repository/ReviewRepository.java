package com.hse.flowerapp.repository;

import com.hse.flowerapp.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review getById(Long id);
}
