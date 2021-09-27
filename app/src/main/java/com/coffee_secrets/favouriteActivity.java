package com.coffee_secrets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class favouriteActivity extends AppCompatActivity {

    private Button mDet;
    private Button mOder;
    private Button mDelet;
    private LinearLayout mLay;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);


        mDet = findViewById(R.id.det);
        mOder = findViewById(R.id.oder);
        mDelet = findViewById(R.id.delet);
        mLay = findViewById(R.id.lay);



        mDet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(favouriteActivity.this,details_Activity.class);
                startActivity(i);

            }
        });


        mOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(favouriteActivity.this,PayActivity.class);
                startActivity(i);

            }
        });



        mDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLay.setVisibility(View.GONE);

            }
        });




    }
}