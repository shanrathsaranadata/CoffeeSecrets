package com.coffee_secrets.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;

import java.util.ArrayList;
import java.util.List;

public class NameActivity extends AppCompatActivity {

    private ImageView mItam;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name);

        String catName = getIntent().getStringExtra("CatName");
//        List<Coffee> coffees = DB.getAllCoffees(catName);
//
        List<Coffee> coffees = new ArrayList<>();
        Coffee coffee = new Coffee();

        for (int i=0; i<5; i++) {
            coffee.setName("Check");
            coffee.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.espresso));

            coffees.add(coffee);
        }


        ConstraintLayout cl = findViewById(R.id.name_cl);
        setUpLayout(coffees, cl);

//        mItam = findViewById(R.id.itam);


//        mItam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               Intent mahara = new Intent(NameActivity.this,details_Activity.class);
//                startActivity(mahara);
//
//            }
//        });

    }

    void setUpLayout(List<Coffee> coffees, ConstraintLayout cl){

        ConstraintSet set = new ConstraintSet();

        int width_unit = getWidth()/12;

        boolean left = true;
        int lastID = ConstraintSet.PARENT_ID;

        for (int i=0; i<coffees.size(); i++){
            Coffee coffee = coffees.get(i);
            //TODO Array Index OOB

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


    int getHeight(){
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;
    }

    int getWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);



        return metrics.widthPixels;
    }



    static int convertDpToPixel(float dp, Context context) {
        return (int) (dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}