package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.CoffeeReview;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.ui.basic.Home_Activity;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review);
        int coffeeID = getIntent().getIntExtra("CoffeeID", -1);
        if (coffeeID==-1) return;

    }

    void setUp(int id){
        Coffee coffee = DB.getCoffeeByID(id, null);

        ImageView image = findViewById(R.id.imageView2);
        TextView title = findViewById(R.id.textView2);

        RatingBar quality = findViewById(R.id.quality_rb);
        RatingBar price = findViewById(R.id.price_rb);
        RatingBar support = findViewById(R.id.support_rb);
        EditText comment = findViewById(R.id.comment_rv);

        image.setImageBitmap(coffee.getBitmap());
        title.setText(coffee.getName());

        findViewById(R.id.rv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CoffeeReview(coffee.getID(),
                        (byte) (quality.getRating() * 2),
                        (byte) (support.getRating() * 2),
                        (byte) (price.getRating() * 2),
                        comment.getText().toString()
                ).save();
                startActivity(new Intent(ReviewActivity.this, Home_Activity.class));
            }
        });



    }
}