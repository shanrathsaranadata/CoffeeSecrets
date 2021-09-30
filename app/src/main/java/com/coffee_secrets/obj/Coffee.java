package com.coffee_secrets.obj;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
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

    //Constructors
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
    public Coffee(){

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

        public void setBitmap(Bitmap bitmap) {
//            try {
//            //    this.bitmap = MediaStore.Images.Media.getBitmap(this,bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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

    //Statics
    public static ArrayList<Coffee.Category> getAllCategories(){
        //Get all cats from DB
        return DB.getAllCategories();
    }
    public static List<Coffee> getAllCoffees(String categoryName){
        return DB.getAllCoffees(categoryName);
    }
    public static List<Coffee> getAllCoffees(){
        return DB.getAllCoffees();
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

        return (float)p/100f;
    }
    public float getDiscountedPrice() {
        int p = (int) ((price*(discount/100)*100));

        return (float)p/100f;
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
