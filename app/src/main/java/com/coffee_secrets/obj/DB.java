package com.coffee_secrets.obj;

import java.util.HashMap;

public class DB {

    static HashMap<Integer, Coffee> coffees = new HashMap<>();

    public static Coffee getCoffeeByID(int ID){
        return coffees.get(ID);
    }

}
