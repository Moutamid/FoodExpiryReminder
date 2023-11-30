package com.example.foodexpiryreminderapp.Model;
// FoodItem.java
public class FoodItem {
    public String name;
    public String expiryDate;
    public int notificationID;

    public FoodItem() {
    }

    public FoodItem(String name, String expiryDate) {
        this.name = name;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
}
