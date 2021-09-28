package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.Favourite;

public class favouriteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite);

        ListView lv = findViewById(R.id.fav_list);
        TextView totalTV = findViewById(R.id.fav_total);

        Favourite favourite = new Favourite(this) {
            @Override
            public void updateTotal(float total) {
                totalTV.setText("Rs. "+total+" /=");
            }
        };
        lv.setAdapter(favourite);



        Button order = findViewById(R.id.fav_cc_order2);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(favouriteActivity.this, PayActivity.class);
                intent.putExtra("Total", favourite.refreshTotal());
                startActivity(intent);
            }
        });




        Button delete = findViewById(R.id.fav_cc_delete2);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



//        mDet = findViewById(R.id.fav_cc_details);
//        mOder = findViewById(R.id.fav_cc_order);
//        mDelet = findViewById(R.id.fav_cc_delete);
//        mLay = findViewById(R.id.lay);
//
//
//
//        mDet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(favouriteActivity.this,details_Activity.class);
//                startActivity(i);
//
//            }
//        });
//
//
//        mOder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(favouriteActivity.this,PayActivity.class);
//                startActivity(i);
//
//            }
//        });
//
//
//
//        mDelet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                mLay.setVisibility(View.GONE);
//
//            }
//        });




    }
}