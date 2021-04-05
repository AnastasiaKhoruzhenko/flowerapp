package com.hse.flowerapp.domain;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @EmbeddedId
    OrderItemKey id = new OrderItemKey();

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @MapsId("itemId")
    @JoinColumn(name = "item_id")
    Item item;

    int countOfThisItemInOrder;

    public OrderItemKey getId() {
        return id;
    }

    public void setId(OrderItemKey id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getCountOfThisItemInOrder() {
        return countOfThisItemInOrder;
    }

    public void setCountOfThisItemInOrder(int countOfThisItemInOrder) {
        this.countOfThisItemInOrder = countOfThisItemInOrder;
    }
}
