package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDto {

    private Long shopId;
    private Long itemId;
    private Long userId;
    private String name;
    private Integer price;
    private String shortDescription;
    private String fullDescription;
    private String size;
    private Integer width;
    private Integer height;
    private String photoUrl;
    private Integer discountPrice;
    private Integer discount;
    private Boolean available;

    public Long getShopId() { return shopId; }

    public void setShopId(Long shopId) { this.shopId = shopId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public Integer getPrice() { return price; }

    public void setPrice(Integer price) { this.price = price; }

    public String getShortDescription() { return shortDescription; }

    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }

    public String getFullDescription() { return fullDescription; }

    public void setFullDescription(String fullDescription) { this.fullDescription = fullDescription; }

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size; }

    public Integer getWidth() { return width; }

    public void setWidth(Integer width) { this.width = width; }

    public Integer getHeight() { return height; }

    public void setHeight(Integer height) { this.height = height; }

    public String getPhotoUrl() { return photoUrl; }

    public void setPhotoUrl(String photoUrl) { this.photoUrl = photoUrl; }

    public Integer getDiscountPrice() { return discountPrice; }

    public void setDiscountPrice(Integer discountPrice) { this.discountPrice = discountPrice; }

    public Integer getDiscount() { return discount; }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Long getItemId() { return itemId; }

    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Long getUserId() { return userId; }

    public void setUserId(Long userId) { this.userId = userId; }
}
