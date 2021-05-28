package com.hse.flowerapp.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "favourites")
@Data
public class Favourite extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "favouriteList", fetch = FetchType.LAZY)
    private List<Item> itemList = new ArrayList<>();

    public Favourite(){
        Date date = new Date();
        setCreated(date);
        setUpdated(date);
        setStatus(Status.ACTIVE);
        itemList = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}
