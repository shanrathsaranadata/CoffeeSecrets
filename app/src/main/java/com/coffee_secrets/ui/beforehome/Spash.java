package com.coffee_secrets.ui.beforehome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.ui.basic.Home_Activity;
import com.coffee_secrets.ui.basic.MainActivity;

public class Spash extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spash);


        mButton = findViewById(R.id.da_update);
        new DB.startUp() {
            @Override
            public void done() {
                Intent i = new Intent(Spash.this,
                        MainActivity.class);
                startActivity(i);
            }
        };




    }
}