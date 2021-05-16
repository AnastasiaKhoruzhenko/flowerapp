package com.hse.flowerapp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockedButtonsDto {
    Boolean lockAddress;
    Boolean lockAddShopInfo;
    Boolean lockAddItems;
    Boolean showConfirmButton;

    public Boolean getLockAddress() {
        return lockAddress;
    }

    public void setLockAddress(Boolean lockAddress) {
        this.lockAddress = lockAddress;
    }

    public Boolean getLockAddShopInfo() {
        return lockAddShopInfo;
    }

    public void setLockAddShopInfo(Boolean lockAddShopInfo) {
        this.lockAddShopInfo = lockAddShopInfo;
    }

    public Boolean getLockAddItems() {
        return lockAddItems;
    }

    public void setLockAddItems(Boolean lockAddItems) {
        this.lockAddItems = lockAddItems;
    }

    public Boolean getShowConfirmButton() {
        return showConfirmButton;
    }

    public void setShowConfirmButton(Boolean showConfirmButton) {
        this.showConfirmButton = showConfirmButton;
    }
}
