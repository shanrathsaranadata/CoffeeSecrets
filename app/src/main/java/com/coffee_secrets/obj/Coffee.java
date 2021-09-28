package com.coffee_secrets.obj;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.coffee_secrets.adapters.CoffeeCats;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.net.URL;
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

    Coffee(int ID, String name, String category, byte rating, String ingredients, float price, float discount, Bitmap bitmap) {
        this.ID = ID;
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.ingredients = ingredients;
        this.price = price;
        this.discount = discount;
        this.bitmap = bitmap;
    }

    public Coffee(String ID, String name, String category, String rating, String ingredients, String price, String discount, String bitmap) {
        this.ID = Integer.parseInt(ID);
        this.name = name;
        this.category = category;
        this.rating = (byte) Integer.parseInt(rating);
        this.ingredients = ingredients;
        this.price = Float.parseFloat(price);
        this.discount = Float.parseFloat(discount);
        this.bitmap = URItoBitMap(bitmap);
    }

    @Deprecated
    public Coffee(){

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isFavourite(){
        return User.isFavouriteCoffee(this.ID);
    }

    public void setFavourite(boolean fav){
        User.addFavourite(ID, fav);
    }



    public static ArrayList<Coffee.Category> getAllCategories(){
        //Get all cats from DB


        return DB.getAllCategories();
    }

    public static class Category{

        String name;
        Bitmap bitmap;

        public Category(){

        }


        public Category(String name, Bitmap bitmap) {
            this.name = name;
            this.bitmap = bitmap;

        }

        public Category(String name, String bitmap) {
            this.name = name;
            this.bitmap = URItoBitMap(bitmap);

        }

        public void setName(String name) {
            this.name = name;
        }

        public void setBitmap(String bitmap) {
            try {
            //    this.bitmap = MediaStore.Images.Media.getBitmap(this,bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        int p = (int) (price*100);

        return price/100;
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

    static Bitmap URItoBitMap(String uri){
        try {
            URL url = new URL(uri);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            return null;
        }
    }



}
