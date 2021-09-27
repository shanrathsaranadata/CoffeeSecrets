package com.coffee_secrets.obj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DB {
    
    static HashMap<Integer, Coffee> coffees = new HashMap<>();

    public static Coffee getCoffeeByID(int ID){

        return coffees.get(ID);
    }

    public static Coffee setCoffeeByID(int ID){

        return coffees.get(ID);

    }

    public static Coffee setUsers(String Name, String Email,
                                  String Street, String City, String ContactNum,
                                  Bitmap image, String Password){

            final DatabaseReference rootref;
            rootref = FirebaseDatabase.getInstance().getReference();

            FirebaseAuth imauth = FirebaseAuth.getInstance();

            imauth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){


                                String sid = imauth.getCurrentUser().getUid();

                                HashMap<String,Object> sellermap = new HashMap<>();

//                                sellermap.put("sid",sid);
//                                sellermap.put("name",regname);
//                                sellermap.put("email",regemail);
//                                sellermap.put("phone",regnumber);
//                                sellermap.put("address",regaddress);
//                                sellermap.put("password",regpassword);

                                rootref.child("Users").child(sid).updateChildren(sellermap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {



                                            }
                                        });

                            }
                            else{
                                ///return"Please Write Valid Email and Password
                            }

                        }
                    });


        return new Coffee();

    }

    public boolean createOrUpdateUser(){

        final DatabaseReference rootref;
        final StorageTask[] uploadTask = new StorageTask[1];
        final String[] myuri = new String[1];
        final StorageReference storageReference;
        rootref = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        FirebaseAuth imauth = FirebaseAuth.getInstance();

        imauth.createUserWithEmailAndPassword(User.Email,User.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String sid = imauth.getCurrentUser().getUid();

                            final StorageReference fileref = storageReference
                                    .child(sid+".jpg");

                            uploadTask[0] =fileref.putFile(User.imageuri);

                            uploadTask[0].continueWithTask(new Continuation() {
                                @Override
                                public Object then(@NonNull Task task) throws Exception {

                                    if(!task.isSuccessful()){
                                        throw task.getException();
                                    }


                                    return fileref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    if(task.isSuccessful()){
                                        Uri downloadUri =task.getResult();
                                        myuri[0] =downloadUri.toString();

                                        HashMap<String,Object> sellermap = new HashMap<>();

                                        sellermap.put("sid",sid);
                                        sellermap.put("Name",User.Name);
                                        sellermap.put("Email",User.Email);
                                        sellermap.put("Street",User.Street);
                                        sellermap.put("City",User.City);
                                        sellermap.put("Image", myuri[0]);
                                        sellermap.put("password",User.password);

                                        rootref.child("User").child(sid).updateChildren(sellermap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {


                                                    }
                                                });

                                    }
                                    else {


                                    }

                                }
                            });
                        }
                        else{

                            ///return"Please Write Valid Email and Password
                        }

                    }
                });


        return true;
    }

    public static List<Coffee> getAllCoffees(String categoryName){
        //Get all coffees from the database
        //Its better to include locally

        return new ArrayList<Coffee>();


    }
    public static ArrayList<Coffee.Category> getAllCategories(){
        //Get all cats from DB
        return new ArrayList<>();
    }
    public static boolean doesExists(String email){
        //Check already reg

        return false;

    }

    public static Coffee getUsers(int ID){


        return coffees.get(ID);

    }





}
