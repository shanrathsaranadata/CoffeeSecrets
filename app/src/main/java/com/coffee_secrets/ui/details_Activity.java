package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;

public class details_Activity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        int ID = getIntent().getIntExtra("CoffeeID", -1);
        if (ID ==-1){
            finish();
        }

        setLayout(ID);


    }

    void setLayout(int ID){
        Coffee coffee = DB.getCoffeeByID(ID, this);


        ImageView image = findViewById(R.id.dt_image);
        TextView name = findViewById(R.id.dt_name);
        TextView indr = findViewById(R.id.dt_ingr);

        ImageView fav = findViewById(R.id.fav_cc_image);
        RatingBar rate = findViewById(R.id.dt_rating);
        Button order = findViewById(R.id.dt_order);

        image.setImageBitmap(coffee.getBitmap());
        name.setText(coffee.getName());
        indr.setText(coffee.getIngredients());

        if (coffee.isFavourite()){
            fav.setImageResource((R.drawable.fav_true));
        }else {
            fav.setImageResource((R.drawable.fav_false));
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (coffee.isFavourite()) {
                    fav.setImageResource((R.drawable.fav_false));
                    coffee.setFavourite(false);
                }else {
                    fav.setImageResource((R.drawable.fav_true));
                    coffee.setFavourite(true);
                }
            }
        });

        rate.setRating(((float) coffee.getRating())/2);

        indr.setText(coffee.getIngredients());

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent i = new Intent(details_Activity.this, MyOrderActivity.class);
                        i.putExtra("CoffeeID", coffee.getID());
                        startActivity(i);
            }
        });

    }
}