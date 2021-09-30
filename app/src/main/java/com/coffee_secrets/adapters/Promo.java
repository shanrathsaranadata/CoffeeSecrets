package com.coffee_secrets.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Order;
import com.coffee_secrets.ui.basic.MyOrderActivity;

import java.util.ArrayList;

public class Promo extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    ArrayList<Coffee> discounts = new ArrayList<>();


    public Promo(Context context){
        this.context = context;
        inflter = (LayoutInflater.from(context));
        ArrayList<Coffee> coffees = (ArrayList<Coffee>) DB.getAllCoffees();

        for (int i=0; i<coffees.size(); i++){
            Coffee coffee = coffees.get(i);

            if (coffee.getPrice()==coffee.getDiscountedPrice()){
                discounts.add(coffee);
            }

        }

    }

    @Override
    public int getCount() {
        return discounts.size();
    }

    @Override
    public Object getItem(int i) {
        return discounts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return discounts.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.promos_deals, null);
        Coffee coffee = discounts.get(i);

        ImageView image = view.findViewById(R.id.imageView2);
        TextView name = view.findViewById(R.id.comment_rv);
        TextView disc = view.findViewById(R.id.pd_disc);
        Button place = view.findViewById(R.id.ple);

        image.setImageBitmap(coffee.getBitmap());
        name.setText(coffee.getName());
        int discount = (int) ((1-coffee.getDiscountedPrice()/ coffee.getPrice())*100);
        disc.setText(discount/100);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(context, MyOrderActivity.class);
               intent.putExtra("CoffeeID", coffee.getID());
               context.startActivity(intent);
            }
        });

        return view;
    }
}
