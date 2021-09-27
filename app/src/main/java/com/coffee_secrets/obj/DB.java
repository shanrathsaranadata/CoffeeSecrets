package com.coffee_secrets.obj;

import android.net.Uri;

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

import java.util.HashMap;

public class DB {

    static HashMap<Integer, Coffee> coffees = new HashMap<>();

    public static Coffee getCoffeeByID(int ID){



        return coffees.get(ID);
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


    public static boolean doesExists(String email){
        //Check already reg

        return false;

    }

    public static Coffee getUsers(int ID){


        return coffees.get(ID);
    }





}
