package com.coffee_secrets.obj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    int id;
    ArrayList<int[]> coffeeIDs= new ArrayList<>();
    ArrayList<Float> soldPrice= new ArrayList<>();
    ArrayList<Integer> quantity = new ArrayList<>();
    String datetime;

    static ArrayList<Order> orders = new ArrayList<>();
    static ArrayList<Integer> order_ID = new ArrayList<>();

    static int getOrderID(){
        return DB.getOrderID();
    }

    public void save(){
        orders.add(this);
        order_ID.add(this.id);
        DB.saveOrder(this);

    }

    static Order get(int ID){
        //Load Order from db
        if (order_ID.contains(ID)){
            return orders.get(order_ID.indexOf((Integer) ID));
        }else {

            return DB.loadOrder(ID);
        }



    }



    public Order(Coffee coffee){
        id = getOrderID();
        coffeeIDs.add(new int[]{coffee.getID()});
        soldPrice.add(coffee.getDiscountedPrice());
        quantity.add(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD HH:mm:ss");
        Date date = new Date();
        datetime = sdf.format(date);
    }

    public Order(Coffee coffee, int Quantity){
        id = getOrderID();

        coffeeIDs.add(new int[]{coffee.getID()});
        soldPrice.add(coffee.getDiscountedPrice());
        quantity.add(Quantity);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd HH:MM:ss");
        Date date = new Date();
        datetime = sdf.format(date);
        System.out.println(datetime);
    }


    public float getTotal(){
        float price = 0f;

        for (int i=0; i<coffeeIDs.size(); i++){
            price+= soldPrice.get(i)*quantity.get(i);
        }

        return (float) ((int) (price*100))/100;
    }

    public void changeQuantity(Coffee coffee, int relativeQuantity){
        int index = quantity.indexOf(coffee.getID());

        int q = quantity.get(index)+relativeQuantity;

        int newQuantity = Math.max(q, 0);

        quantity.set(index, newQuantity);

    }

    public int getQuantity(Coffee coffee){
        int index = quantity.indexOf(coffee.getID());

        return quantity.get(index);


    }


    public int getID() {
        return id;
    }
}
