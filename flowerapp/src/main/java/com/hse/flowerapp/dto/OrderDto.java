package com.hse.flowerapp.dto;

import com.hse.flowerapp.domain.Order;
import com.hse.flowerapp.domain.OrderItem;
import com.hse.flowerapp.domain.OrderStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDto {
     Long orderId;
     Integer totalSum;
     String promocode;
     OrderStatus orderStatus;
     Integer discountSum;
     Boolean secretDelivery;
     Integer deliveryPrice;
     String name;
     String email;
     String phone;
     Long userId;
     String secretName;
     String secretPhone;
     Boolean addPostcard;
     String postcardText;
     Boolean sayItIsFlower;
     Boolean sayFrom;
     Boolean callToConfirm;
     String town;
     String street;
     String house;
     String building;
     String flat;
     Date updatedDate;
     Long shopId;
     Integer sellerId;
     Boolean isRated;

    List<Long> listIds = new ArrayList<>();
    List<Integer> listCount = new ArrayList<>();

    public List<Long> getListIds() {
        return listIds;
    }

    public void setListIds(List<Long> listIds) {
        this.listIds = listIds;
    }

    public List<Integer> getListCount() {
        return listCount;
    }

    public void setListCount(List<Integer> listCount) {
        this.listCount = listCount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Integer totalSum) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public String getSecretPhone() {
        return secretPhone;
    }

    public void setSecretPhone(String secretPhone) {
        this.secretPhone = secretPhone;
    }

    public Boolean getAddPostcard() {
        return addPostcard;
    }

    public void setAddPostcard(Boolean addPostcard) {
        this.addPostcard = addPostcard;
    }

    public String getPostcardText() {
        return postcardText;
    }

    public void setPostcardText(String postcardText) {
        this.postcardText = postcardText;
    }

    public Boolean getSayItIsFlower() {
        return sayItIsFlower;
    }

    public void setSayItIsFlower(Boolean sayItIsFlower) {
        this.sayItIsFlower = sayItIsFlower;
    }

    public Boolean getSayFrom() {
        return sayFrom;
    }

    public void setSayFrom(Boolean sayFrom) {
        this.sayFrom = sayFrom;
    }

    public Boolean getCallToConfirm() {
        return callToConfirm;
    }

    public void setCallToConfirm(Boolean callToConfirm) {
        this.callToConfirm = callToConfirm;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Boolean getIsRated() {
        return isRated;
    }

    public void setIsRated(Boolean isRated) {
        this.isRated = isRated;
    }

    public static OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setTotalSum(order.getTotalSum());
        orderDto.setPromocode(order.getPromocode());
        orderDto.setOrderStatus(order.getOrderStatus());
        orderDto.setDiscountSum(order.getDiscountSum());
        orderDto.setSecretDelivery(order.getSecretDelivery());
        orderDto.setDeliveryPrice(order.getDeliveryPrice());
        orderDto.setName(order.getName());
        orderDto.setEmail(order.getEmail());
        orderDto.setPhone(order.getPhone());
        orderDto.setSecretName(order.getSectretName());
        orderDto.setSecretPhone(order.getSecretPhone());
        orderDto.setAddPostcard(order.getAddPostcard());
        orderDto.setPostcardText(order.getPostcardText());
        orderDto.setSayItIsFlower(order.getSayItIsFlower());
        orderDto.setSayFrom(order.getSayFrom());
        orderDto.setCallToConfirm(order.getCallToConfirm());

        orderDto.setTown(order.getTown());
        orderDto.setStreet(order.getStreet());
        orderDto.setHouse(order.getHouse());
        orderDto.setBuilding(order.getBuilding());
        orderDto.setFlat(order.getFlat());

        orderDto.setUpdatedDate(order.getUpdated());

        orderDto.setShopId(order.getShopId());
        orderDto.setSellerId(order.getSellerId());

        List<Long> idsList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();
        for(OrderItem orderItem : order.getCountItemInOrder()) {
            idsList.add(orderItem.getItem().getId());
            countList.add(orderItem.getCountOfThisItemInOrder());
        }

        orderDto.setListIds(idsList);
        orderDto.setListCount(countList);

        orderDto.setIsRated(order.getIsRated());

        return orderDto;
    }
}
