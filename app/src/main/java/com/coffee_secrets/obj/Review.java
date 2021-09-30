package com.coffee_secrets.obj;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Review {

    public static ArrayList<Review> getAllReviews(){
        return DB.getAllReviews();
    }



    String title;
    String name;
    byte rating;
    Bitmap image;
    String comment;

    public Review(String title, String name, byte rating, Bitmap image, String comment) {
        this.title = title;
        this.name = name;
        this.rating = rating;
        this.image = image;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void save(){


    }
}
