package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffee_secrets.R;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mReg;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mLogin = findViewById(R.id.login);
        mReg = findViewById(R.id.reg);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Login_2_Activity.class);
                startActivity(i);

            }
        });


        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Login_2_Activity.class);
                startActivity(i);

            }
        });



    }
}