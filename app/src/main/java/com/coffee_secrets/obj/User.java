package com.coffee_secrets.obj;

import android.net.Uri;

import java.util.ArrayList;

public class User {

    public  static int ID;
    public  static String Name;
    public  static String Email;
    public  static String Street;
    public  static String City;
    public  static String ContactNum;
    public  static Uri Imageuri;
    public static String Password;

    public static ArrayList<Integer> favourites = new ArrayList<>();


    public static void create(String name, String email,
                String street, String city,
                String contactNum, Uri imageuri,String password) {

        User.ID = 0;
        Name = name;
        Email = email;
        Street = street;
        City = city;
        ContactNum = contactNum;
        Password = password;
        User.Imageuri = imageuri;





    }


}
