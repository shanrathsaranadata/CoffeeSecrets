package com.coffee_secrets.ui.beforehome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.ui.basic.Home_Activity;

public class Spash extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spash);


        mButton = findViewById(R.id.da_update);
        DB.startUp start = new DB.startUp() {
            @Override
            public void done() {
                Intent i = new Intent(Spash.this,
                        Home_Activity.class);
                startActivity(i);
            }
        };


//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent i = new Intent(Spash.this,
//                        Home_Activity.class);
//                startActivity(i);
//
//            }
//        });



    }
}