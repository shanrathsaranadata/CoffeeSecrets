package com.coffee_secrets.obj;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.coffee_secrets.R;
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

public class DB {
    
    static HashMap<Integer, Coffee> coffees = new HashMap<>();


    //Order
    static void saveOrder(Order order){


    }

    static Order loadOrder(int ID){

        return null;
    }

    static void deleteOrder(Order order){


    }

    static int getOrderID(){
        return 0;
    }











    public static Coffee getCoffeeByID(int ID){

        return coffees.get(ID);
    }

    public static void load(){



    }

    @Deprecated
    public static Coffee getCoffeeByID(int ID, Context context){
        Coffee coffee = new Coffee();
        coffee.setName("Check");
        coffee.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.espresso));

        return coffee;
    }

    public static Coffee setCoffeeByID(int ID){

        if (coffees.containsKey(ID)) {
            return coffees.get(ID);
        }else {
            //TODO get from DB
            return null;
        }
    }


    public static boolean createOrUpdateUser(Context context){

        final DatabaseReference rootref;
        final StorageTask[] uploadTask = new StorageTask[1];
        final String[] myuri = new String[1];
        final StorageReference storageReference;
        rootref = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pictures");

        FirebaseAuth imauth = FirebaseAuth.getInstance();

        imauth.createUserWithEmailAndPassword(User.Email,User.Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String sid = imauth.getCurrentUser().getUid();

                            final StorageReference fileref = storageReference
                                    .child(sid+".jpg");

                            uploadTask[0] =fileref.putFile(User.Imageuri);

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
                                        sellermap.put("password",User.Password);

                                        rootref.child("User").child(sid).updateChildren(sellermap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        Toast.makeText(context, "Your are register Successful", Toast.LENGTH_SHORT).show();


                                                    }
                                                });

                                    }
                                    else {

                                        Toast.makeText(context, "Image Upload is not Complete", Toast.LENGTH_SHORT).show();


                                    }

                                }
                            });
                        }
                        else{
                            Toast.makeText(context, "Please Write Valid Email and Password", Toast.LENGTH_SHORT).show();
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
