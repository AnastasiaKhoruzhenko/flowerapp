package com.hse.flowerapp.dto;

import com.hse.flowerapp.domain.Review;

public class ReviewDto {
    private Long reviewId;
    private Long userId;
    private Long orderId;
    private Long itemId;
    private String header;
    private String body;
    private Integer grade;

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public static ReviewDto convertReviewToReviewDTO(Review review){
        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setItemId(review.getItemId());
        reviewDto.setUserId(review.getUserId());
        reviewDto.setGrade(review.getGrade());
        reviewDto.setBody(review.getBody());
        reviewDto.setHeader(review.getHeader());
        reviewDto.setOrderId(review.getOrderId());
        return reviewDto;
    }

    public static Review convertReviewDTOToReview(ReviewDto reviewDto){
        Review review = new Review();

        review.setItemId(reviewDto.getItemId());
        review.setUserId(reviewDto.getUserId());
        review.setGrade(reviewDto.getGrade());
        review.setBody(reviewDto.getBody());
        review.setHeader(reviewDto.getHeader());
        review.setOrderId(reviewDto.getOrderId());
        return review;
    }
}
