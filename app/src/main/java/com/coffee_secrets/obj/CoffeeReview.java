package com.coffee_secrets.obj;

public class CoffeeReview {

    String userID;
    int coffeeID;

    byte quality;
    byte support;
    byte price;
    String comment;

    public CoffeeReview(int coffeeID,
                        byte quality, byte support,
                        byte price, String comment) {
        this.userID = User.ID;
        this.coffeeID = coffeeID;
        this.quality = quality;
        this.support = support;
        this.price = price;
        this.comment = comment;
    }

    public void save(){
        DB.addOrUpdate(this);
    }
}
