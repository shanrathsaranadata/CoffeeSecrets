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
import com.coffee_secrets.ui.basic.details_Activity;

import java.util.ArrayList;

public class Cart extends BaseAdapter {

    LayoutInflater inflter;
    Context context;
    ArrayList<Integer> checkedCoffee = new ArrayList<>();

    TextView[] textViews;

    int currentPage;
    int pages;
    final int COFFEE_PER_PAGE =5;

    public Cart(Context context, TextView[] textViews){
        this.context = context;
        inflter = (LayoutInflater.from(context));
        currentPage = 1;
        pages = (User.cart.size()%COFFEE_PER_PAGE==0)?
                (User.cart.size()/COFFEE_PER_PAGE) : (User.cart.size()/COFFEE_PER_PAGE)+1;

        this.textViews = textViews;

        setUpNavigation();
    }

    @Override
    public int getCount() {
        return COFFEE_PER_PAGE;
    }

    @Override
    public Object getItem(int i) {
        int index = (currentPage-1)*COFFEE_PER_PAGE + i;

        return DB.getCoffeeByID(User.cart.get(index),
                context);
    }

    @Override
    public long getItemId(int i) {
        int index = (currentPage-1)*COFFEE_PER_PAGE + i;
        return User.cart.get(index) ;
    }

    @Override
    public View getView(int in, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.c0, null);

        int i = (currentPage-1)*COFFEE_PER_PAGE + in;

        Coffee coffee = DB.getCoffeeByID((int) getItemId(i), context);

        TextView name = view.findViewById(R.id.cart_si_name);
        name.setText(coffee.getName());

        TextView price = view.findViewById(R.id.cart_si_price);
        price.setText("Rs. "+coffee.getPrice()+" /=");


        Button update = view.findViewById(R.id.cart_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, details_Activity.class);
//                intent.putExtra("CoffeeID", coffee.getID());
//                context.startActivity(intent);
            }
        });

        Button delete = view.findViewById(R.id.cart_si_del);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.cart.remove(i);
                notifyDataSetChanged();
            }
        });

        ImageView image = view.findViewById(R.id.cart_cc_image);
        image.setImageBitmap(coffee.getBitmap());

        CheckBox check = view.findViewById(R.id.cart_cc_cb);
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
            Coffee coffee = DB.getCoffeeByID(i, context);
            total+=coffee.getDiscountedPrice();
        }

        return total;
    }

    void setUpNavigation(){
        textViews[0].setText("Page " + currentPage + " of " + pages);
        if (pages>=4) {
            textViews[1].setText("<<");
            textViews[2].setText(String.valueOf(currentPage));
            textViews[3].setText(String.valueOf(currentPage + 1));
            textViews[4].setText(">>");
        }else {
            for (int i=1; i<5; i++) {
                if (i<=pages) {
                    textViews[i].setText(String.valueOf(i));
                }else {
                    textViews[i].setVisibility(View.INVISIBLE);
                }
            }

        }

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView) view;
                String text = textView.getText().toString();

                if (text.matches("[0-9]+")){
                    int newPage = Integer.parseInt(text);
                    currentPage = newPage;


                }else if (text.equals("<<")){
                    if (currentPage>1){
                        currentPage--;
                        textViews[2].setText(String.valueOf(currentPage));
                        textViews[3].setText(String.valueOf(currentPage + 1));
                    }

                }else {
                    if (currentPage<pages-1){
                        currentPage++;
                        textViews[2].setText(String.valueOf(currentPage));
                        textViews[3].setText(String.valueOf(currentPage + 1));
                    }else if (currentPage==pages-1){
                        currentPage++;
                    }
                }
                notifyDataSetChanged();
                textViews[0].setText("Page " + currentPage + " of " + pages);
            }
        };
        for (int i=0; i<textViews.length; i++){
            textViews[i].setOnClickListener(ocl);
        }

    }

}
