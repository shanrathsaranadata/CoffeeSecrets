package com.coffee_secrets.obj;

import android.media.Image;

import java.util.ArrayList;

public class User {

    public  static int ID;
    public  static String Name;
    public  static String Email;
    public  static String Street;
    public  static String City;
    public  static String ContactNum;
    public  static Image image;

    public static ArrayList<Integer> favourites = new ArrayList<>();


    public User(int ID, String name, String email,
                String street, String city,
                String contactNum, Image image) {

        User.ID = ID;
        Name = name;
        Email = email;
        Street = street;
        City = city;
        ContactNum = contactNum;
        User.image = image;
    }
}
