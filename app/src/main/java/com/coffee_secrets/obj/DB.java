package com.coffee_secrets.obj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.coffee_secrets.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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


    public static Coffee getCoffeeByID(int ID,Context context){



        DatabaseReference arts = FirebaseDatabase.getInstance().getReference().child("Coffee").child(String.valueOf(ID));

        arts.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    Bitmap imagebitmap = null;
                    int ID=Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("ID").getValue()).toString());
                    int rating = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("rating").getValue()).toString());
                    String name= Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    String category= Objects.requireNonNull(dataSnapshot.child("category").getValue()).toString();
                    String ingredients= Objects.requireNonNull(dataSnapshot.child("ingredients").getValue()).toString();
                    float price= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString());
                    float discount = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("discount").getValue()).toString());
                    String image= Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString();

                    try {
                        URL url = new URL(image);
                        imagebitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    } catch(IOException e) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    Coffee coffee = new Coffee(ID,name,category, (byte) rating,ingredients,price,discount,imagebitmap);


                }
                else{

                    Toast.makeText(context, "You coffee is not Exists", Toast.LENGTH_SHORT).show();

                    }

                }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(context, "database error", Toast.LENGTH_SHORT).show();

            }
        });


        return coffees.get(ID);
    }

    public static void load(){



    }
//
//    @Deprecated
//    public static Coffee getCoffeeByID(int ID, Context context){
//        Coffee coffee = new Coffee();
//        coffee.setName("Check");
//        coffee.setBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.espresso));
//
//        return coffee;
//    }

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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Coffee");
        Query query = usersRef.orderByChild("category").equalTo(categoryName);


        ArrayList<Coffee> coffees = new ArrayList<Coffee>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot  : dataSnapshot.getChildren()) {
                    Coffee coffee = snapshot .getValue(Coffee.class);
                    coffees.add(coffee);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

        return coffees;


    }

    public static List<Coffee> getAllCoffees(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Coffee");
        ArrayList<Coffee> coffees = new ArrayList<Coffee>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coffee coffee = snapshot.getValue(Coffee.class);
                    coffees.add(coffee);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        usersRef.addValueEventListener(valueEventListener);
        return coffees;

    }

    public static ArrayList<Coffee.Category> getAllCategories(){

        return new ArrayList<Coffee.Category>();

    }

    public static boolean doesExists(String email){
        //Check already reg

        return false;

    }





}
