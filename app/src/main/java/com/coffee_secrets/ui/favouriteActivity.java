package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Favourite;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Order;
import com.coffee_secrets.obj.User;
import com.coffee_secrets.ui.basic.PayActivity;

import java.util.ArrayList;

public class favouriteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);

        ListView lv = findViewById(R.id.fav_list);
        TextView totalTV = findViewById(R.id.fav_total);

        Favourite favourite = new Favourite(this) {
            @Override
            public void updateTotal(float total) {
                totalTV.setText("Rs. "+total+" /=");
            }
        };
        lv.setAdapter(favourite);



        Button order = findViewById(R.id.fav_cc_order2);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favourite.refreshTotal()<0){
                    Toast.makeText(favouriteActivity.this, "Invalid selection!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Integer> c = favourite.getSelectedCoffees();

                ArrayList<Coffee> coffees = new ArrayList<>();
                ArrayList<Integer> quantity = new ArrayList<>();

                for (int i=0; i<c.size(); i++){
                    Coffee coffee = DB.getCoffeeByID(c.get(i));
                    coffees.add(coffee);
                    quantity.add(1);
                }

                Order order1 = new Order(coffees, quantity);
                order1.save();

                Intent intent = new Intent(favouriteActivity.this, PayActivity.class);
                intent.putExtra("OrderID", order1.getID());
                startActivity(intent);
            }
        });




        Button delete = findViewById(R.id.fav_cc_delete2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> coffees = favourite.getSelectedCoffees();
                for (int i=0; i< coffees.size(); i++){
                    User.favourites.remove((Integer) coffees.get(i));

                }
                favourite.notifyDataSetChanged();
                favourite.refreshTotal();

            }
        });





    }
}