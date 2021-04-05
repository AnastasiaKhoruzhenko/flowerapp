package com.hse.flowerapp.domain;

import javax.persistence.*;

@Entity
@Table(name = "item_cart")
public class ItemCart {

    @EmbeddedId
    ItemCartKey id = new ItemCartKey();

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    Cart cart;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    Item item;

    int countOfThisItemInCart;

    public ItemCartKey getId() {
        return id;
    }

    public void setId(ItemCartKey id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCountOfThisItemInCart() {
        return countOfThisItemInCart;
    }

    public void setCountOfThisItemInCart(int countOfThisItemInCart) {
        this.countOfThisItemInCart = countOfThisItemInCart;
    }
}
