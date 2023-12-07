package com.example.foodexpiryreminderapp.Model;
// FoodItem.java
public class FoodItem {
    public String name;
    public String expiryDate;
    public int notificationID;
    public boolean isDonated;

    public FoodItem() {
    }

    public FoodItem(String name, String expiryDate, int notificationID, boolean isDonated) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.notificationID = notificationID;
        this.isDonated = isDonated;
    }

    public FoodItem(String name, String expiryDate, boolean isDonated) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.isDonated = isDonated;
    }

    public String getName() {
        return name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public boolean isDonated() {
        return isDonated;
    }

    public void setDonated(boolean donated) {
        isDonated = donated;
    }
}
