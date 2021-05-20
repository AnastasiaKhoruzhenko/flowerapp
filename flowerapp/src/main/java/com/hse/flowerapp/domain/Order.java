package com.hse.flowerapp.domain;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "total_sum")
    private Integer totalSum;

    @Column(name = "promocode")
    private String promocode;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "discount_sum")
    private Integer discountSum;

    @NotNull
    @Column(name = "secret_delivery")
    private Boolean secretDelivery;

    @NotNull
    @Column(name = "delivery_price")
    private Integer deliveryPrice;

    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "phone")
    private String phone;

    @ManyToOne(cascade=CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "order")
    List<OrderItem> countItemInOrder = new ArrayList<>();

    @Column(name = "address_id")
    Long addressId;

    @Column(name = "secretName")
    String sectretName;

    @Column(name = "secretPhone")
    String secretPhone;

    @Column(name = "addPostcard")
    Boolean addPostcard;

    @Column(name = "postcardText")
    String postcardText;

    @Column(name = "sayItIsFlower")
    Boolean sayItIsFlower;

    @Column(name = "sayFrom")
    Boolean sayFrom;

    @Column(name = "callToConfirm")
    Boolean callToConfirm;

    @Column(name = "town")
    String town;

    @Column(name = "street")
    String street;

    @Column(name = "house")
    String house;

    @Column(name = "building")
    String building;

    @Column(name = "flat")
    String flat;

    @Column(name = "shop_id")
    Long shopId;

    @Column(name = "seller_id")
    Integer sellerId;

    @Column(name = "is_rated")
    Boolean isRated = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getCountItemInOrder() {
        return countItemInOrder;
    }

    public void setCountItemInOrder(List<OrderItem> countItemInOrder) {
        this.countItemInOrder = countItemInOrder;
    }

    public Long getAddressId() { return addressId; }

    public void setAddressId(Long addressId) { this.addressId = addressId; }

    public String getSectretName() {
        return sectretName;
    }

    public void setSectretName(String sectretName) {
        this.sectretName = sectretName;
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

    public Boolean getRated() {
        return isRated;
    }

    public void setRated(Boolean rated) {
        isRated = rated;
    }
}
