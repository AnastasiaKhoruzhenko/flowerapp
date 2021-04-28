package com.hse.flowerapp.dto;

public class ItemCountDto {
    Integer count;

    public ItemCountDto(Integer count){
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
