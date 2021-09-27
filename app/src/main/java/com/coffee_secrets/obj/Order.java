package com.coffee_secrets.obj;

import java.util.ArrayList;

public class Order {

    int[] coffeeIDs;
    float[] unitPrices;
    float soldPrice;
    String date;

    public Order(int[] coffeeIDs, float soldPrice, String date) {
        this.coffeeIDs = coffeeIDs;
        this.unitPrices = unitPrices;
        this.soldPrice = soldPrice;
        this.date = date;
    }
    public Order(int coffeeID, float unitPrice,
                 float soldPrice, String date) {
        this.coffeeIDs = new int[]{coffeeID};
        this.unitPrices = new float[]{unitPrice};
        this.soldPrice = soldPrice;
        this.date = date;
    }
    public Order(int[] coffeeIDs, float[] unitPrices, float soldPrice, String date) {
        this.coffeeIDs = coffeeIDs;
        this.unitPrices = unitPrices;
        this.soldPrice = soldPrice;
        this.date = date;
    }
}
