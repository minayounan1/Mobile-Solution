package com.example.mobilesolutions.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CartItems")
public class CartItemList {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int itemID;
    private String itemName;
    private String storeName;

    @Override
    public String toString() {
        return itemID + " - " + itemName;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
