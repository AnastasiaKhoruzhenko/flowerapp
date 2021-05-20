package com.hse.flowerapp.service;

import com.hse.flowerapp.dto.ReviewDto;
import org.springframework.stereotype.Service;

@Service
public interface ReviewService {

    ReviewDto createItemReview(ReviewDto reviewDto);
}
