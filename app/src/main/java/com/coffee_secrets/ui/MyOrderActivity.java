package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Order;

public class MyOrderActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myoders);

        int ID = getIntent().getIntExtra("CoffeeID", -1);
        if (ID==-1){
            finish();
        }

        setUp(ID);
    }

    void setUp(int coffeeID){
        Coffee coffee = DB.getCoffeeByID(coffeeID, this);

        ImageView image = findViewById(R.id.order_img);
        TextView name = findViewById(R.id.order_name);
        TextView price = findViewById(R.id.order_price);
        TextView quantity = findViewById(R.id.order_quantity);
        TextView total = findViewById(R.id.mo_total);

        Button plus = findViewById(R.id.order_plus);
        Button minus = findViewById(R.id.order_minus);

        findViewById(R.id.order_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Order order = new Order(coffee);

        findViewById(R.id.order_place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyOrderActivity.this, PayActivity.class);
                order.save();
                intent.putExtra("Order", order.getID());
                startActivity(intent);
            }
        });

        //Basic
        image.setImageBitmap(coffee.getBitmap());
        name.       setText("Name      - "+coffee.getName());
        price.      setText("Price     - Rs. "+coffee.getPrice()+" /=");
        quantity.   setText("Quantity  - "+1);



        total.setText("Rs. "+order.getTotal()+" /=");

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.changeQuantity(coffee, 1);
                quantity.setText("Quantity  - "+order.getQuantity(coffee));
                total.setText("Rs. "+order.getTotal()+" /=");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.changeQuantity(coffee, -1);
                quantity.setText("Quantity  - "+order.getQuantity(coffee));
                total.setText("Rs. "+order.getTotal()+" /=");
            }
        });



    }
}