package com.coffee_secrets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CofffebarActivity extends AppCompatActivity {

    private Button mCrate;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cofffebar);

        mCrate = findViewById(R.id.crate);


        mCrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CofffebarActivity.this,CreateActivity.class);
                startActivity(i);

            }
        });





    }
}