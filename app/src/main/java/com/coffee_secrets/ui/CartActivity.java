package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Cart;
import com.coffee_secrets.ui.basic.PayActivity;

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

    }
}