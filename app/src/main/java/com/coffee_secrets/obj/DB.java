package com.coffee_secrets.obj;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
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


        return true;
    }


    public static boolean doesExists(String email){
        //Check already reg

        return false;

    }

    public static Coffee getUsers(int ID){


        return coffees.get(ID);
    }





}
