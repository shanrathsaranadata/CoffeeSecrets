package com.coffee_secrets.obj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order {

    int id;
    ArrayList<Integer> coffeeIDs= new ArrayList<>();
    ArrayList<Float> soldPrice= new ArrayList<>();
    ArrayList<Integer> quantity = new ArrayList<>();
    String datetime;

    static ArrayList<Order> orders = new ArrayList<>();
    static ArrayList<Integer> order_ID = new ArrayList<>();

    boolean payed = false;
    String changed_address;
    String changed_name;
    String changed_number;
    String changed_email;



    public void setChangedDetails(String address,
                                  String name, String number,
                                  String email){

        changed_address = address;
        changed_name = name;
        changed_email = email;
        changed_number = number;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ArrayList<Integer> getCoffeeIDs() {
        return coffeeIDs;
    }
    public void setCoffeeIDs(ArrayList<Integer> coffeeIDs) {
        this.coffeeIDs = coffeeIDs;
    }
    public ArrayList<Float> getSoldPrice() {
        return soldPrice;
    }
    public void setSoldPrice(ArrayList<Float> soldPrice) {
        this.soldPrice = soldPrice;
    }
    public ArrayList<Integer> getQuantity() {
        return quantity;
    }
    public void setQuantity(ArrayList<Integer> quantity) {
        this.quantity = quantity;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public static ArrayList<Order> getOrders() {
        return DB.orders;
    }
    public static void setOrders(ArrayList<Order> orders) {
        Order.orders = orders;
    }
    public static ArrayList<Integer> getOrder_ID() {
        return order_ID;
    }

    public static void setOrder_ID(ArrayList<Integer> order_ID) {
        Order.order_ID = order_ID;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public String getChanged_address() {
        return changed_address;
    }

    public void setChanged_address(String changed_address) {
        this.changed_address = changed_address;
    }

    public String getChanged_name() {
        return changed_name;
    }

    public void setChanged_name(String changed_name) {
        this.changed_name = changed_name;
    }

    public String getChanged_number() {
        return changed_number;
    }

    public void setChanged_number(String changed_number) {
        this.changed_number = changed_number;
    }

    public String getChanged_email() {
        return changed_email;
    }

    public void setChanged_email(String changed_email) {
        this.changed_email = changed_email;
    }

    static int getOrderID(){
        return DB.getOrderID();
    }


    public String getDatetime() {
        return datetime;
    }

    public void save(){
        orders.add(this);
        order_ID.add(this.id);
        DB.saveOrder(this);

    }

    public static Order get(int ID){
        //Load Order from db
        if (order_ID.contains(ID)){
            return orders.get(order_ID.indexOf((Integer) ID));
        }else {

            return DB.loadOrder(ID);
        }



    }

    public static ArrayList<Order> getAll(){
        return DB.getOrders();
    }

    public Coffee getFirstCoffee(){
        return DB.getCoffeeByID(coffeeIDs.get(0));
    }

    public Order(int id, ArrayList<Integer> coffeeIDs,
                 ArrayList<Float> soldPrice, ArrayList<Integer> quantity,
                 String datetime) {
        this.id = id;
        this.coffeeIDs = coffeeIDs;
        this.soldPrice = soldPrice;
        this.quantity = quantity;
        this.datetime = datetime;
    }
    public Order(int id, int coffeeIDs,
                 float soldPrice, int quantity,
                 String datetime) {
        this.id = id;

        this.coffeeIDs.add(coffeeIDs);
        this.soldPrice.add(soldPrice);

        this.quantity.add(quantity);
        this.datetime = datetime;
    }

    public Order(Coffee coffee){
        id = getOrderID();
        coffeeIDs.add(coffee.getID());
        soldPrice.add(coffee.getDiscountedPrice());
        quantity.add(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        datetime = sdf.format(date);
    }

    public Order(Coffee coffee, int Quantity){
        id = getOrderID();

        coffeeIDs.add(coffee.getID());
        soldPrice.add(coffee.getDiscountedPrice());
        quantity.add(Quantity);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/dd HH:MM:ss");
        Date date = new Date();
        datetime = sdf.format(date);
        System.out.println(datetime);
    }

    public Order(ArrayList<Coffee> coffees, ArrayList<Integer> Quantity){
        id = getOrderID();

        for (int i=0; i< coffees.size(); i++){
            coffeeIDs.add(coffees.get(i).getID());
            soldPrice.add(coffees.get(i).getDiscountedPrice());
            quantity.add(Quantity.get(i));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/mm/DD HH:MM:ss");
        Date date = new Date();
        datetime = sdf.format(date);
        System.out.println(datetime);
    }

    public void delete(){
        DB.deleteOrder(this);
    }

    public float getTotal(){
        float price = 0f;

        for (int i=0; i<coffeeIDs.size(); i++){
            price+= soldPrice.get(i)*quantity.get(i);
        }

        return (float) ((int) (price*100))/100;
    }

    public void changeQuantity(Coffee coffee, int relativeQuantity){
        int index = 0; //TODO remove

        int q = quantity.get(index)+relativeQuantity;

        int newQuantity = Math.max(q, 0);

        quantity.set(index, newQuantity);

    }

    public int getQuantity(Coffee coffee){
        int index = 0;//TODO quantity.indexOf(coffee.getID());

        return quantity.get(index);


    }


    public int getID() {
        return id;
    }
}
