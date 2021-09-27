package com.coffee_secrets.obj;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.coffee_secrets.adapters.CoffeeCats;

import java.util.ArrayList;
import java.util.List;

public class Coffee {

    int ID;
    String name;
    String category;
    byte rating;
    String ingredients;
    float price;
    float discount = 0f;
    Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }



    public static ArrayList<Coffee.Category> getAllCategories(){
        //Get all cats from DB


        return DB.getAllCategories();
    }

    public static class Category{

        String name;
        Bitmap bitmap;



        public Category(String name, Bitmap bitmap) {
            this.name = name;
            this.bitmap = bitmap;

        }

        public String getName() {
            return name;
        }

        public Bitmap getBitmap() {
            return bitmap;
        }

        public List<Coffee> getAllCoffees(){
            return DB.getAllCoffees(name);
        }
    }


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
    public void setName(String name) {
        this.name = name;
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
