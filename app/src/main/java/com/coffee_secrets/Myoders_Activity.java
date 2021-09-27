package com.coffee_secrets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Myoders_Activity extends AppCompatActivity {

    private Button mOderr;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myoders);


        mOderr = findViewById(R.id.oderr);

        mOderr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Myoders_Activity.this,PayActivity.class);
                startActivity(i);

            }
        });

    }
}