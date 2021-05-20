package com.hse.flowerapp.service.impl;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.Review;
import com.hse.flowerapp.domain.Status;
import com.hse.flowerapp.dto.ReviewDto;
import com.hse.flowerapp.repository.OrderRepository;
import com.hse.flowerapp.repository.ReviewRepository;
import com.hse.flowerapp.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public ReviewDto createItemReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setUserId(reviewDto.getUserId());
        review.setItemId(reviewDto.getItemId());
        review.setGrade(reviewDto.getGrade());
        review.setBody(reviewDto.getBody());
        review.setHeader(reviewDto.getHeader());
        review.setStatus(Status.ACTIVE);
        review.setOrderId(reviewDto.getOrderId());
        Date date = new Date();
        review.setCreated(date);
        review.setUpdated(date);
        reviewRepository.save(review);

        Order order = orderRepository.getById(reviewDto.getOrderId());
        order.setIsRated(true);
        orderRepository.save(order);

        return reviewDto;
    }
}
