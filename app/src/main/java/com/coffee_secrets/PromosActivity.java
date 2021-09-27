package com.coffee_secrets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PromosActivity extends AppCompatActivity {

    private Button mPle;
    private Button mPle2;

    ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promos);

        mPle = findViewById(R.id.ple);
        mPle2 = findViewById(R.id.ple2);


        mPle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PromosActivity.this,Myoders_Activity.class);
                startActivity(i);

            }
        });


        mPle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PromosActivity.this,Myoders_Activity.class);
                startActivity(i);

            }
        });




    }
}