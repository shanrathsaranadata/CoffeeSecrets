package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Favourite;
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
                Intent intent = new Intent(favouriteActivity.this, PayActivity.class);
                intent.putExtra("Total", favourite.refreshTotal());
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