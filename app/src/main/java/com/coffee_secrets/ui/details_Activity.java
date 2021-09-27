package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffee_secrets.R;

public class details_Activity extends AppCompatActivity {

    private Button mAddtomy;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        mAddtomy = findViewById(R.id.addtomy);




        mAddtomy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(details_Activity.this,Myoders_Activity.class);
                startActivity(i);

            }
        });



    }
}