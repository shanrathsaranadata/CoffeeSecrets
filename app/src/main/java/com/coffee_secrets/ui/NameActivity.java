package com.coffee_secrets.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.coffee_secrets.R;

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