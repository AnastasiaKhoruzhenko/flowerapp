package com.hse.flowerapp.dto;

import com.hse.flowerapp.domain.OrderStatus;

import java.util.Map;

public class OrderDto {
    private Long orderId;
    private String totalSum;
    private String promocode;
    private OrderStatus orderStatus;
    private Integer discountSum;
    private Boolean secretDelivery;
    private Integer deliveryPrice;
    private String name;
    private String email;
    private String phone;
    private String comment;
    private Long userId;
    // мапа "id" и "количество таких элементов"
    private Map<Long, Integer> listItemIds;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(Integer discountSum) {
        this.discountSum = discountSum;
    }

    public Boolean getSecretDelivery() {
        return secretDelivery;
    }

    public void setSecretDelivery(Boolean secretDelivery) {
        this.secretDelivery = secretDelivery;
    }

    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Map<Long, Integer> getListItemIds() {
        return listItemIds;
    }

    public void setListItemIds(Map<Long, Integer> listItemIds) {
        this.listItemIds = listItemIds;
    }
}
