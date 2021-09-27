package com.coffee_secrets.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
        coffee.setName("Check");
        coffee.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.espresso));

        coffees.add(coffee);



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

        for (int i=0; i<coffees.size(); i++){
            Coffee coffee = coffees.get(i);
            //TODO Array Index OOB
//            Coffee coffee2 = coffees.get(i+1);


            View view = LayoutInflater.from(this).inflate(R.layout.coffee_items, null);
            LinearLayout ll = view.findViewById(R.id.ci_main);
            ll.setId(View.generateViewId());
            ImageView iv = ll.findViewById(R.id.ci_image);
            iv.setId(View.generateViewId());

            TextView tv = ll.findViewById(R.id.ci_text);
            tv.setId(View.generateViewId());

            iv.setImageBitmap(coffee.getBitmap());
            tv.setText(coffee.getName());

            cl.addView(view);
            set.constrainHeight(view.getId(),ConstraintSet.WRAP_CONTENT);
            set.constrainWidth(view.getId(), ConstraintSet.WRAP_CONTENT);

            set.connect(view.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
            set.connect(view.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT);
            set.connect(view.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT);

        }

        set.applyTo(cl);




    }
}