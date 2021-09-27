package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffee_secrets.R;

public class PayActivity extends AppCompatActivity {

    private Button mComnfirm;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);


        mComnfirm = findViewById(R.id.comnfirm);


        mComnfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(PayActivity.this,Delivery_Activity.class);
                startActivity(i);

            }
        });







    }
}