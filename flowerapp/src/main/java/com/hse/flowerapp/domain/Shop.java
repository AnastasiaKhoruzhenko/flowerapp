package com.hse.flowerapp.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "rating")
    private String rating;

    @NotNull
    @Column(name = "item_count")
    private Integer itemCount;

    @NotNull
    @Column(name = "delivery_price")
    private Integer deliveryPrice;

    @OneToOne(mappedBy = "shop", cascade = CascadeType.ALL)
    private User user;

    @Column(name = "open_time")
    private String openTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address shopAddress;

    @OneToMany(mappedBy = "shop")
    private List<Item> itemList = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getDelivery_price() {
        return deliveryPrice;
    }

    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Address getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(Address shopAddress) { this.shopAddress = shopAddress; }

    public List<Item> getItemList() { return itemList; }

    public void setItemList(List<Item> itemList) { this.itemList = itemList; }

    public Integer getItemCount() { return itemCount; }

    public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }
}
