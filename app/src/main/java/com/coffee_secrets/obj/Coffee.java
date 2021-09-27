package com.coffee_secrets.obj;

import java.util.HashMap;

public class Coffee {

    int ID;
    String name;
    String category;
    byte rating;
    String ingredients;
    float price;
    float discount = 0f;


    public static float getCoffeePrice(int[] coffeeID){
        int size = coffeeID.length;

        float r = 0f;
        for (int j : coffeeID) {
            r += DB.coffees.get(j).getDiscountedPrice();
        }

        return r/size;
    }


    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public byte getRating() {
        return rating;
    }
    public void setRating(byte rating) {
        this.rating = rating;
    }
    public String getIngredients() {
        return ingredients;
    }
    public float getPrice() {
        return price;
    }
    public float getDiscountedPrice() {
        return price*(discount/100);
    }

    public float getDiscount() {
        return discount;
    }
    public void setDiscount(float discount) {
        this.discount = discount;
    }

}
