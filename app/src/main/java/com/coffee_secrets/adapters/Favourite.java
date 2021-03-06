package com.coffee_secrets.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.User;
import com.coffee_secrets.ui.basic.MyOrderActivity;
import com.coffee_secrets.ui.basic.details_Activity;

import java.util.ArrayList;

public abstract class Favourite extends BaseAdapter {

    LayoutInflater inflter;
    Context context;
    ArrayList<Integer> checkedCoffee = new ArrayList<>();

    public Favourite(Context context){
        this.context = context;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return User.favourites.size();
    }

    @Override
    public Object getItem(int i) {
        return DB.getCoffeeByID(User.favourites.get(i)
        );
    }

    @Override
    public long getItemId(int i) {
        return User.favourites.get(i) ;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.cc, null);

        Coffee coffee = DB.getCoffeeByID((int) getItemId(i));

        TextView name = view.findViewById(R.id.fav_cc_name);
        name.setText(coffee.getName());

        TextView price = view.findViewById(R.id.fav_cc_price);
        price.setText("Rs. "+coffee.getDiscountedPrice()+" /=");

        Button order = view.findViewById(R.id.fav_cc_order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyOrderActivity.class);
                intent.putExtra("CoffeeID", coffee.getID());
                context.startActivity(intent);
            }
        });

        Button details = view.findViewById(R.id.fav_cc_details);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, details_Activity.class);
                intent.putExtra("CoffeeID", coffee.getID());
                context.startActivity(intent);
            }
        });

        Button delete = view.findViewById(R.id.fav_cc_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.favourites.remove(i);
                notifyDataSetChanged();
            }
        });

        ImageView image = view.findViewById(R.id.fav_cc_image);
        image.setImageBitmap(coffee.getBitmap());

        CheckBox check = view.findViewById(R.id.fav_cc_cb);
        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int coffeeID = (int) getItemId(i);
                if (b){
                    if (!checkedCoffee.contains(coffeeID)){
                        checkedCoffee.add(coffeeID);
                    }
                }else {
                    if (checkedCoffee.contains(coffeeID)){
                        checkedCoffee.remove((Integer) coffeeID);
                    }
                }

                refreshTotal();
            }
        });

        return view;
    }

    public ArrayList<Integer> getSelectedCoffees(){
        return checkedCoffee;
    }

    public float refreshTotal(){

        float total = 0f;
        for (int i=0; i<checkedCoffee.size(); i++){
            int index = checkedCoffee.get(i);
            Coffee coffee = DB.getCoffeeByID(index);
            total+=coffee.getDiscountedPrice();
        }

        updateTotal((float) ((int)total*100)/100);
        return total;
    }

    public abstract void updateTotal(float total);
}
