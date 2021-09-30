package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Bar;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Order;
import com.coffee_secrets.ui.basic.CreateActivity;

import java.util.ArrayList;

public class CofffebarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cofffebar);

        ArrayList<Coffee> allCoffees = (ArrayList<Coffee>) Coffee.getAllCoffees();
        Bar bar = new Bar(allCoffees,this);
        ListView lv = findViewById(R.id.cb_list);
        lv.setAdapter(bar);




        Button mCrate = findViewById(R.id.crate);
        mCrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> selected = bar.getChecked();
                if (selected.size()==0){
                    Toast.makeText(CofffebarActivity.this, "Select at least one coffee", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<Coffee> coffees = new ArrayList<>();
                ArrayList<Integer> quantity = new ArrayList<>();

                for (int i=0; i<selected.size(); i++){
                    Coffee coffee = DB.getCoffeeByID(selected.get(i));
                    coffees.add(coffee);
                    quantity.add(coffee.getID());
                }

                Intent i = new Intent(CofffebarActivity.this, CreateActivity.class);
                Order order = new Order(coffees, quantity);
                order.save();

                i.putExtra("OrderID", order.getID());

                startActivity(i);

            }
        });





    }
}