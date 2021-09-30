package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Promo;
import com.coffee_secrets.ui.basic.Home_Activity;
import com.coffee_secrets.ui.basic.MyOrderActivity;

public class PromosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promos);

        setUp();

    }

    void setUp(){
        findViewById(R.id.p_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PromosActivity.this, Home_Activity.class));
            }
        });

        ListView lv = findViewById(R.id.lv_promo);
        Promo promo = new Promo(this);
        lv.setAdapter(promo);





    }
}