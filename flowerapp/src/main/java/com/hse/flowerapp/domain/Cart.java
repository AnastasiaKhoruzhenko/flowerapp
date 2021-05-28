package com.hse.flowerapp.domain;


import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_count")
    private Integer totalCount;

    @NotNull
    @Column(name = "total_sum")
    private Integer totalSum;

    @Column(name = "discount_sum")
    private Integer discountSum;

    @Column(name = "promocode")
    private String promocode;

    @OneToOne(mappedBy = "cart", cascade=CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "cart")
    List<ItemCart> countItemInCarts = new ArrayList<>();

    public Cart() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Integer totalSum) {
        this.totalSum = totalSum;
    }

    public Integer getDiscountSum() {
        return discountSum;
    }

    public void setDiscountSum(Integer discountSum) {
        this.discountSum = discountSum;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ItemCart> getCountItemInCarts() {
        return countItemInCarts;
    }

    public void setCountItemInCarts(List<ItemCart> countItemInCarts) {
        this.countItemInCarts = countItemInCarts;
    }



    //    public List<Item> getItemList() { return itemList; }
//
//    public void setItemList(List<Item> itemList) { this.itemList = itemList; }
}
