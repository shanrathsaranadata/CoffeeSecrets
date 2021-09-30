package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Cart;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Order;
import com.coffee_secrets.obj.User;
import com.coffee_secrets.ui.basic.PayActivity;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_new);

        TextView[] textViews = {
                findViewById(R.id.cn_page),
                findViewById(R.id.cn_pb_1),
                findViewById(R.id.cn_pb_2),
                findViewById(R.id.cn_pb_3),
                findViewById(R.id.cn_pb_4)
        };

        Cart cart = new Cart(this, textViews);
        ListView lv = findViewById(R.id.cart_list);
        lv.setAdapter(cart);


        findViewById(R.id.cart_cc_order2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> c = cart.getSelectedCoffees();

                if (c.size()==0){
                    Toast.makeText(CartActivity.this, "Please select at least one item.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<Coffee> coffees = new ArrayList<>();
                ArrayList<Integer> quantity = new ArrayList<>();

                for (int i=0; i<c.size(); i++){
                    Coffee coffee = DB.getCoffeeByID(c.get(i), CartActivity.this);
                    coffees.add(coffee);
                    quantity.add(User.getQuantityFromCart(c.get(i)));
                }

                Order order = new Order(coffees,quantity);
                order.save();
                Intent intent = new Intent(CartActivity.this, PayActivity.class);
                intent.putExtra("OrderID", order.getID());
                startActivity(intent);
            }
        });
        findViewById(R.id.cart_cc_delete2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> c = cart.getSelectedCoffees();
                for (int i=0; i<c.size(); i++){
                    User.removeFromCart(c.get(i));
                }
                cart.notifyDataSetChanged();
            }
        });


    }
}