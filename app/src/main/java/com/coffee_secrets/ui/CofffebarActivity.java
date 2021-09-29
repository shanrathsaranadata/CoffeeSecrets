package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Bar;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.ui.basic.CreateActivity;

import java.util.ArrayList;
import java.util.List;

public class CofffebarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cofffebar);

        ArrayList<Coffee> allCoffees = (ArrayList<Coffee>) DB.getAllCoffees();
        Bar bar = new Bar(allCoffees,this);
        ListView lv = findViewById(R.id.cb_list);
        lv.setAdapter(bar);



        Button mCrate = findViewById(R.id.crate);
        mCrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CofffebarActivity.this, CreateActivity.class);
                startActivity(i);

            }
        });





    }
}