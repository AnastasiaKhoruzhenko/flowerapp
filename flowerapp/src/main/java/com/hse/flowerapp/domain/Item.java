package com.hse.flowerapp.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "items")
@Data
public class Item extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "name")
    String name;

    @NotNull
    @Column(name = "short_description")
    String shortDescription;

    @NotNull
    @Column(name = "full_description")
    String fullDescription;

    @NotNull
    @Column(name = "width")
    Integer width;

    @NotNull
    @Column(name = "height")
    Integer height;

    @NotNull
    @Column(name = "price")
    Integer price;

    @Column(name = "photo_url")
    String photoURL;

    @NotNull
    @Column(name = "size")
    String size;

    @Column(name = "discount_price")
    Integer discountPrice;

    @Column(name = "discount")
    Integer discount;

    @NotNull
    @Column(name = "available")
    Boolean available;

    @NotNull
    @Column(name = "category")
    String categoryName;

    @NotNull
    @Column(name = "flowers")
    String flowers;

    @Column(name = "rating")
    Float rating = 0.0f;

    @Column(name = "review_count")
    Integer reviewCount = 0;

    @ManyToOne
    @JoinColumn(name="shop_id")
    private Shop shop;

    @OneToMany(mappedBy = "item")
    List<ItemCart> countItemInCarts = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    List<OrderItem> countItemInOrders = new ArrayList<>();

    public Item(){
        Date date = new Date();
        setCreated(date);
        setUpdated(date);
        setStatus(Status.ACTIVE);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Shop getShop() { return shop; }

    public void setShop(Shop shop) { this.shop = shop; }

    public List<ItemCart> getCountItemInCarts() {
        return countItemInCarts;
    }

    public void setCountItemInCarts(List<ItemCart> countItemInCarts) {
        this.countItemInCarts = countItemInCarts;
    }

    public List<OrderItem> getCountItemInOrders() {
        return countItemInOrders;
    }

    public void setCountItemInOrders(List<OrderItem> countItemInOrders) {
        this.countItemInOrders = countItemInOrders;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getFlowers() {
        return flowers;
    }

    public void setFlowers(String flowers) {
        this.flowers = flowers;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
}
