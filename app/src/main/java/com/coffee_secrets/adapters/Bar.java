package com.coffee_secrets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Bar extends BaseAdapter {
    ArrayList<Coffee> coffees = new ArrayList<>();
    LayoutInflater inflter;
    Context context;
    ArrayList<Integer> checked = new ArrayList<>();


    public Bar(ArrayList<Coffee> coffees, Context context) {
        this.coffees = coffees;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return coffees.size();
    }

    @Override
    public Object getItem(int i) {
        return coffees.get(i);
    }

    @Override
    public long getItemId(int i) {
        return coffees.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.co1, null);

        ImageView image = view.findViewById(R.id.ci_image);
        TextView name = view.findViewById(R.id.b_name);
        CheckBox check = view.findViewById(R.id.fav_cc_cb);

        Coffee coffee = coffees.get(i);
        image.setImageBitmap(coffee.getBitmap());
        name.setText(coffee.getName());

        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int coffeeID = (int) getItemId(i);
                if (b){
                    if (!checked.contains(coffeeID)){
                        checked.add(coffeeID);
                    }
                }else {
                    if (checked.contains(coffeeID)){
                        checked.remove((Integer) coffeeID);
                    }
                }

            }
        });



        return view;
    }

    public ArrayList<Integer> getChecked() {
        return checked;
    }
}
