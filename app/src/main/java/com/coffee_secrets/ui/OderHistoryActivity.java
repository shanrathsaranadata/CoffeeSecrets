package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.History;
import com.coffee_secrets.obj.Order;

import java.util.ArrayList;

public class OderHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oder_history);

        setUp();

    }

    void setUp(){
        ArrayList<Order> orders = Order.getAll();
        ListView lv = findViewById(R.id.oh_list);

        History history = new History(orders, this);
        lv.setAdapter(history);





    }
}