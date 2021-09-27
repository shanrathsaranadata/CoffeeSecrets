package com.coffee_secrets;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {

    private ImageView mItam;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);


        mItam = findViewById(R.id.itam);


        mItam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent mahara = new Intent(NameActivity.this,details_Activity.class);
                startActivity(mahara);

            }
        });

    }
}