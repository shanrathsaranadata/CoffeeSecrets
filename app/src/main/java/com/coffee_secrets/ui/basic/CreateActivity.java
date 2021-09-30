package com.coffee_secrets.ui.basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Order;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create);

        int orderID = getIntent().getIntExtra("OrderID", -1);
        if (orderID==-1) finish();

        Order order = Order.get(orderID);
        setUpLayout(order.getCoffeeIDs());

        Button mPay = findViewById(R.id.pay);
        mPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(CreateActivity.this,PayActivity.class);
                i.putExtra("OrderID", orderID);
                startActivity(i);

            }
        });

        findViewById(R.id.order_del).setOnClickListener(new View.OnClickListener() { //Edit
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() { //Delete
            @Override
            public void onClick(View view) {
                order.delete();
                finish();
            }
        });





    }

    public void setUpLayout(ArrayList<Integer> coffees){

        ConstraintLayout cl = findViewById(R.id.cb_cl);

        int width_unit = getWidth()/12;
        ConstraintSet set = new ConstraintSet();

        boolean left = true;

        for (int i=0; i<coffees.size(); i++){
            Coffee coffee = DB.getCoffeeByID(coffees.get(i));

            View view = LayoutInflater.from(this).inflate(R.layout.coffee_items, null);
            view.setId(View.generateViewId());
            LinearLayout ll = view.findViewById(R.id.ci_main);
            ll.setId(View.generateViewId());
            ImageView iv = ll.findViewById(R.id.ci_image);
            iv.setId(View.generateViewId());

            TextView tv = ll.findViewById(R.id.ci_text);
            tv.setId(View.generateViewId());

            iv.setImageBitmap(coffee.getBitmap());
            tv.setText(coffee.getName());


            //Set on click listner
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CreateActivity.this, details_Activity.class);
                    intent.putExtra("CoffeeID", coffee.getID());
                    CreateActivity.this.startActivity(intent);
                }
            });


            cl.addView(view);
            set.constrainHeight(view.getId(),width_unit*6);
            set.constrainWidth(view.getId(), width_unit*5);


            set.connect(view.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP,
                    ((width_unit/8)+((int) i/2)*width_unit*5));

            if (left) {
                set.connect(view.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, width_unit);
            }else {
                set.connect(view.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, width_unit);

            }
            left = !left;
        }

        set.applyTo(cl);




    }


    int getWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);



        return metrics.widthPixels;
    }
}