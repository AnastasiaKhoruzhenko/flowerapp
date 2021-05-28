package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortItemDto {

    private Long itemId;
    private Long shopId;
    private String name;
    private Integer price;
    private String shortDescription;
    private String photoUrl;
    private Integer discountPrice;
    private Integer discount;

    private Integer itemCount;

    public ShortItemDto toShortItemDto(ItemDto itemDto){
        ShortItemDto shortItemDto = new ShortItemDto();
        shortItemDto.setItemId(itemDto.getItemId());
        shortItemDto.setName(itemDto.getName());
        shortItemDto.setPrice(itemDto.getPrice());
        shortItemDto.setShortDescription(itemDto.getShortDescription());
        shortItemDto.setPhotoUrl(itemDto.getPhotoUrl());
        shortItemDto.setDiscountPrice(itemDto.getDiscountPrice());
        shortItemDto.setDiscount(itemDto.getDiscount());
        shortItemDto.setShopId(itemDto.getShopId());

        return shortItemDto;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }
}
