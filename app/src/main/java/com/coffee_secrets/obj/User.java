package com.coffee_secrets.obj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    public  static String ID;
    public  static String Name;
    public  static String Email;
    public  static String Street;
    public  static String City;
    public  static String ContactNum;
    public  static Bitmap bitmap;
    public static String Password;
    public static Uri uri;

    public static ArrayList<Integer> favourites = new ArrayList<>();
    public static ArrayList<Integer> cart = new ArrayList<>();

    public static String getAddress(){
        return Name+", "+Street+", "+City;
    }


    public static void create(String name, String email,
                              String street, String city,
                              String contactNum, Uri imageuri, String password,
                              Context context) {

        User.ID = null;
        Name = name;
        Email = email;
        Street = street;
        City = city;
        ContactNum = contactNum;
        Password = password;
        uri = imageuri;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageuri);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    static boolean isFavouriteCoffee(int ID){
        return favourites.contains(ID);
    }

    static void addFavourite(int ID, boolean fav){
        if (!favourites.contains(ID) && fav){
            favourites.add(ID);
        }else if (favourites.contains(ID)){
            favourites.remove((Integer) ID);
        }
    }

    public static boolean isEditTextContainEmail(EditText argEditText) {

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(argEditText.getText());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
