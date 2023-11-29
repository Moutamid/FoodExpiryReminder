package com.example.foodexpiryreminderapp.Model;
// FoodItem.java
public class FoodItem {
    private String name;
    private String expiryDate;

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
