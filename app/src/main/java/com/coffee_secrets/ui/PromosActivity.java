package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffee_secrets.R;
import com.coffee_secrets.ui.basic.MyOrderActivity;

public class PromosActivity extends AppCompatActivity {

    ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promos);

        Button mPle = findViewById(R.id.ple);
        Button mPle2 = findViewById(R.id.ple2);


        mPle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PromosActivity.this, MyOrderActivity.class);
                startActivity(i);

            }
        });


        mPle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PromosActivity.this, MyOrderActivity.class);
                startActivity(i);

            }
        });




    }
}