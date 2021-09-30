package com.coffee_secrets.obj;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.coffee_secrets.adapters.CoffeeCats;
import com.coffee_secrets.ui.basic.NameActivity;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DB {

    static ArrayList<Coffee> coffees = new ArrayList<>();
    static ArrayList<Integer> coffeeIDs = new ArrayList<>();
    static ArrayList<Coffee.Category> categories = new ArrayList<>();

    static ArrayList<Order> orders = new ArrayList<>();
    static ArrayList<Integer> orderIDs = new ArrayList<>();


    //Start up
    public abstract static class startUp {
        int o = 0;
        int cat = 0;
        int cof = 0;

        public startUp(){
            loadOrders();
            getAllCategories();
            getAllCoffees();
        }


        void loadOrders() {
            orders.clear();
            orderIDs.clear();

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersRef = rootRef.child("Order");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        int id = Integer.parseInt(Objects.requireNonNull(snapshot.child("ID").getValue()).toString());
                        int coffeeIDs = Integer.parseInt(Objects.requireNonNull(snapshot.child("coffeeIDs").getValue()).toString());
                        float soldPrice = Float.parseFloat(Objects.requireNonNull(snapshot.child("soldPrice").getValue()).toString());
                        int quantity = Integer.parseInt(Objects.requireNonNull(snapshot.child("quantity").getValue()).toString());
                        String datetime = Objects.requireNonNull(snapshot.child("datetime").getValue()).toString();
                        Order order = new Order(id, coffeeIDs, soldPrice, quantity, datetime);
                        orders.add(order);
                        orderIDs.add(order.getID());

                    }
                    o = 1;
                    check();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    o = -1;
                    check();
                }
            };

            usersRef.addValueEventListener(valueEventListener);
        }
        void getAllCoffees(){

            coffees.clear();
            coffeeIDs.clear();
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersRef = rootRef.child("Coffee");


            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot  : dataSnapshot.getChildren()) {

                        int ID=Integer.parseInt(Objects.requireNonNull(snapshot.child("ID").getValue()).toString());
                        int rating = Integer.parseInt(Objects.requireNonNull(snapshot.child("rating").getValue()).toString());
                        String name= Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                        String category= Objects.requireNonNull(snapshot.child("category").getValue()).toString();
                        String ingredients= Objects.requireNonNull(snapshot.child("ingredients").getValue()).toString();
                        float price= Float.parseFloat(Objects.requireNonNull(snapshot.child("price").getValue()).toString());
                        float discount = Float.parseFloat(Objects.requireNonNull(snapshot.child("discount").getValue()).toString());
                        Bitmap image= URItoBitMap(Objects.requireNonNull(snapshot.child("bitmap").getValue()).toString());
                        Coffee coffee = new Coffee(ID,name,category, (byte) rating,ingredients,price,discount,image);
                        coffees.add(coffee);
                        coffeeIDs.add(coffee.getID());


                    }
                    cof =1;
                    check();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    cof = -1;
                    check();
                }
            };

            usersRef.addValueEventListener(valueEventListener);
        }
        void getAllCategories(){

            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference usersRef = rootRef.child("Category");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        try {
                            String name = Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                            Bitmap bitmap = URItoBitMap(Objects.requireNonNull(snapshot.child("bitmap").getValue()).toString());
                            Coffee.Category category = new Coffee.Category(name, bitmap);
                            categories.add(category);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    cat = 1;
                    check();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    cat = -1;
                    check();
                }
            };

            usersRef.addValueEventListener(valueEventListener);

        }

        void check(){
            if (!(o==0 || cat==0 || cof ==0)){
                if (o==-1){
                    loadOrders();
                }else if (cat==-1){
                    getAllCategories();
                }else if (cof==-1){
                    getAllCoffees();
                }else {
                    done();
                }
            }


        }

        public abstract void done();

    }


    //TODO connect with database
    //Review
    static void addOrUpdate(CoffeeReview review){
        final DatabaseReference CoffeeReviews;
        CoffeeReviews = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>userdata = new HashMap<>();

        userdata.put("coffeeIDs",String.valueOf(review.coffeeID));
        userdata.put("userID",String.valueOf(review.userID));
        userdata.put("comment",String.valueOf(review.comment));
        userdata.put("price",String.valueOf(review.price));
        userdata.put("quality",String.valueOf(review.quality));
        userdata.put("support",String.valueOf(review.support));

        CoffeeReviews.child("CoffeeReviews").child(String.valueOf(review.coffeeID+review.userID)).updateChildren(userdata)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        if(task.isSuccessful()){

                            return;


                        }
                        else{

                            return;

                        }
                    }
                });
    }


    //Order
    static void saveOrder(Order order){
        if (!orderIDs.contains(order.getID())) {
            orders.add(order);
            orderIDs.add(order.getID());
        }



        final DatabaseReference Ordered;
        Ordered = FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object>userdata = new HashMap<>();
        userdata.put("ID",String.valueOf(order.id));
        userdata.put("sid",User.ID);
        userdata.put("coffeeIDs",String.valueOf(order.coffeeIDs.get(0)));
        userdata.put("soldPrice",String.valueOf(order.soldPrice.get(0)));
        userdata.put("quantity",String.valueOf(order.quantity.get(0)));
        userdata.put("datetime",String.valueOf(order.datetime));


        Ordered.child("Order").child(String.valueOf(order.id+User.ID)).updateChildren(userdata)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        if(task.isSuccessful()){

                            if(true) return;


                        }
                        else{

                           return;

                        }
                    }
                });


    }
    static Order loadOrder(int ID){
        if (orderIDs.contains(ID)){
            return orders.get(orderIDs.indexOf(ID));
        }
        return null;
    }
    static void deleteOrder(Order order){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ordered = rootRef.child("Order");

        ordered.child(String.valueOf(order.id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                System.out.println(task.toString());

            }
        });


    }
    static int getOrderID(){
        if (orderIDs.size()>0) {
            return Collections.max(orderIDs) + 1;
        }else {
            return 1;
        }
    }
    static ArrayList<Order> getOrders(){
        return orders;
    }


    //Coffee
    public static Coffee getCoffeeByID(int ID){
        if (DB.coffeeIDs.contains(ID)){
            return coffees.get(coffeeIDs.indexOf(ID));
        }
        return null;
    }
    static List<Coffee> getAllCoffees(String categoryName){
            ArrayList<Coffee> r = new ArrayList<>();
            for (int i=0; i<coffees.size(); i++){
                Coffee coffee = coffees.get(i);
                if (coffee.getCategory().equals(categoryName)){
                    r.add(coffee);
                }




            }
            return r;
    }
    static List<Coffee> getAllCoffees(){
            return coffees;
    }
    static ArrayList<Coffee.Category> getAllCategories(){
        return categories;
    }


    public static boolean doesExists(String email){
        //TODO Check already reg

        return false;

    }
    static Bitmap URItoBitMap(String uri){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        try {
            URL url = new URL(uri);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            return null;
        }
    }


    public static void setUserID(String ID){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("User").child(ID);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    User.ID= Objects.requireNonNull(snapshot.child("sid").getValue()).toString();
                    User.Name= Objects.requireNonNull(snapshot.child("Name").getValue()).toString();
                    User.Email= Objects.requireNonNull(snapshot.child("Email").getValue()).toString();
                    User.Street= Objects.requireNonNull(snapshot.child("Street").getValue()).toString();
                    User.City= Objects.requireNonNull(snapshot.child("City").getValue()).toString();
                    User.ContactNum= Objects.requireNonNull(snapshot.child("ContactNum").getValue()).toString();
                    User.bitmap= URItoBitMap(Uri.parse(Objects.requireNonNull(snapshot.child("Image").getValue()).toString()).toString());
                    User.Password= Objects.requireNonNull(snapshot.child("password").getValue()).toString();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



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
                            User.ID = imauth.getCurrentUser().getUid();

                            final StorageReference fileref = storageReference
                                    .child(User.ID+".jpg");


                            uploadTask[0] =fileref.putFile(User.uri);

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

                                        sellermap.put("sid",User.ID);
                                        sellermap.put("Name",User.Name);
                                        sellermap.put("Email",User.Email);
                                        sellermap.put("Street",User.Street);
                                        sellermap.put("City",User.City);
                                        sellermap.put("Image", myuri[0]);
                                        sellermap.put("password",User.Password);
                                        sellermap.put("ContactNum",User.ContactNum);

                                        rootref.child("User").child(User.ID).updateChildren(sellermap)
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
}
