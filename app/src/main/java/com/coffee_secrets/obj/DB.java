package com.coffee_secrets.obj;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.coffee_secrets.R;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DB {
    
    static HashMap<Integer, Coffee> coffees = new HashMap<>();
    static ArrayList<Integer> coffeeIDs = new ArrayList<>();

    static ArrayList<Order> orders = new ArrayList<>();
    static ArrayList<Integer> orderIDs = new ArrayList<>();




    //Order
    static void saveOrder(Order order){

        final DatabaseReference Ordered;
        Ordered = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object>userdata = new HashMap<>();
        userdata.put("ID",order.id);
        userdata.put("coffeeIDs",order.coffeeIDs);
        userdata.put("soldPrice",order.soldPrice);
        userdata.put("quantity",order.quantity);
        userdata.put("datetime",order.datetime);

        Ordered.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Order").child(String.valueOf(order.id)).exists()))
                {


                    Ordered.child("Order").child(String.valueOf(order.id)).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {

                                    if(task.isSuccessful()){

                                        System.out.println(task.toString());


                                    }
                                    else{

                                        System.out.println("not saved");

                                    }
                                }
                            });


                }
                else{

                    Ordered.child("Order").child(String.valueOf(order.id)).updateChildren(userdata)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {

                                    if(task.isSuccessful()){

                                        System.out.println(task.toString());


                                    }
                                    else{

                                        System.out.println("not saved");

                                    }
                                }
                            });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println(databaseError.toString());

            }
        });


    }

//
//    public static abstract class OrderLoad{
//        public OrderLoad(int ID){
//
//        }
//
//        public abstract void onLoaded(Order order);
//
//        void loadOrder(int ID){
//
//            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//            DatabaseReference usersRef = rootRef.child("Order");
//            Query query = usersRef.orderByChild("id").equalTo(ID);
//
//
//            ValueEventListener valueEventListener = new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Order orders= dataSnapshot.getValue(Order.class);
//                    onLoaded(orders);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            };
//            query.addListenerForSingleValueEvent(valueEventListener);
//
//
//        }
//
//
//
//    }
//

    static Order loadOrder(int ID){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Order");
        Query query = usersRef.orderByChild("id").equalTo(ID);



        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot  : dataSnapshot.getChildren()) {

                    int id = Integer.parseInt(Objects.requireNonNull(snapshot.child("ID").getValue()).toString());;
                    int coffeeIDs= Integer.parseInt(Objects.requireNonNull(snapshot.child("coffeeIDs").getValue()).toString());
                    float soldPrice= Float.parseFloat(Objects.requireNonNull(snapshot.child("soldPrice").getValue()).toString());
                    int quantity = Integer.parseInt(Objects.requireNonNull(snapshot.child("quantity").getValue()).toString());
                    String datetime = Objects.requireNonNull(snapshot.child("datetime").getValue()).toString();
                    Order order = new Order(id,coffeeIDs,soldPrice,quantity,datetime);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

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
        return 0;
    }

    static ArrayList<Order> getOrders(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Coffee");
        ArrayList<Order> orders = new ArrayList<Order>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot  : dataSnapshot.getChildren()) {

                    int id = Integer.parseInt(Objects.requireNonNull(snapshot.child("ID").getValue()).toString());;
                    int coffeeIDs= Integer.parseInt(Objects.requireNonNull(snapshot.child("coffeeIDs").getValue()).toString());
                    float soldPrice= Float.parseFloat(Objects.requireNonNull(snapshot.child("soldPrice").getValue()).toString());
                    int quantity = Integer.parseInt(Objects.requireNonNull(snapshot.child("quantity").getValue()).toString());
                    String datetime = Objects.requireNonNull(snapshot.child("datetime").getValue()).toString();
                    Order order = new Order(id,coffeeIDs,soldPrice,quantity,datetime);
                    orders.add(order);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        usersRef.addValueEventListener(valueEventListener);
        return orders;
    }


    public static Coffee getCoffeeByID(int ID,Context context){
        if (DB.coffeeIDs.contains(ID)){
            return coffees.get(ID);
        }


        DatabaseReference coffee = FirebaseDatabase.getInstance().getReference().child("Coffee").child(String.valueOf(ID));

        coffee.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    int ID=Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("ID").getValue()).toString());
                    int rating = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("rating").getValue()).toString());
                    String name= Objects.requireNonNull(dataSnapshot.child("name").getValue()).toString();
                    String category= Objects.requireNonNull(dataSnapshot.child("category").getValue()).toString();
                    String ingredients= Objects.requireNonNull(dataSnapshot.child("ingredients").getValue()).toString();
                    float price= Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("price").getValue()).toString());
                    float discount = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child("discount").getValue()).toString());
                    Bitmap image= URItoBitMap(Objects.requireNonNull(dataSnapshot.child("image").getValue()).toString());
                    Coffee coffee = new Coffee(ID,name,category, (byte) rating,ingredients,price,discount,image);


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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

        return coffees;


    }

    public static void loadAllCoffees(String categoryName,Context context){
        //Get all coffees from the database
        //Its better to include locally

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Coffee");
        Query query = usersRef.orderByChild("category").equalTo(categoryName);




        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Coffee> coffees = new ArrayList<Coffee>();


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

                    if (!DB.coffeeIDs.contains(ID)){
                        DB.coffeeIDs.add(ID);
                        DB.coffees.put(ID,coffee);
                    }


                }



                NameActivity.setUpLayout(coffees,context);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

    }

    public static List<Coffee> getAllCoffees(){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Coffee");
        ArrayList<Coffee> coffees = new ArrayList<Coffee>();

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

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Category");
        ArrayList<Coffee.Category> coffees = new ArrayList<Coffee.Category>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String name = snapshot.child("name").getValue().toString();
                    Bitmap bitmap = URItoBitMap(snapshot.child("bitmap").getValue().toString());
                    Coffee.Category category = new Coffee.Category(name, bitmap);
                    coffees.add(category);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        usersRef.addValueEventListener(valueEventListener);
        return coffees;

    }

    public static ArrayList<Coffee.Category> getAllCategories(CoffeeCats coffeeCats){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("Category");
        ArrayList<Coffee.Category> coffees = new ArrayList<Coffee.Category>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String name = snapshot.child("name").getValue().toString();
                    Bitmap bitmap = URItoBitMap(snapshot.child("bitmap").getValue().toString());
                    Coffee.Category category = new Coffee.Category(name, bitmap);
                    coffeeCats.addCategory(category);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                int a =0;
            }
        };

        usersRef.addValueEventListener(valueEventListener);
        return coffees;

    }

    public static boolean doesExists(String email){
        //Check already reg

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





}
