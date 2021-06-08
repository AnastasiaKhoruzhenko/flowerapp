package com.hse.flowerapp.service;

import com.hse.flowerapp.dto.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {

    ReviewDto createItemReview(ReviewDto reviewDto);

    List<ReviewDto> itemReviews(Long item_id);
}
