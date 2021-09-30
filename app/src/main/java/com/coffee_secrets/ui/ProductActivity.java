package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.ProductReview;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.Review;
import com.coffee_secrets.obj.User;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        EditText title = findViewById(R.id.textView8);
        EditText comment = findViewById(R.id.b_name);
        TextView name = findViewById(R.id.dt_ingr);

        ImageView image = findViewById(R.id.imageView5);
        RatingBar rate = findViewById(R.id.dt_rating);

        Button edit = findViewById(R.id.pa_edit);
        Button delete = findViewById(R.id.pa_delete);

        Review review = DB.getUserReview();
        if (review!=null) {
            title.setText(review.getTitle());
            comment.setText(review.getComment());
            name.setText(User.Name);
            image.setImageBitmap(User.bitmap);
            rate.setRating((float) (review.getRating() / 2));
            disable(title);
            disable(comment);

        }else {
            title.setText("");
            title.setHint("Title");

            comment.setText("");
            comment.setHint("Comment");

            disable(title);
            disable(comment);

            name.setText(User.Name);
            image.setImageBitmap(User.bitmap);
            rate.setRating((float) (0f));
            edit.setText("Add");
        }
        rate.setIsIndicator(true);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText().toString().equals("Edit")){
                    enable(title);
                    enable(comment);
                    edit.setText("Save");
                    rate.setIsIndicator(false);

                }else if (edit.getText().toString().equals("Add")){
                    edit.setText("Save");
                    enable(title);
                    enable(comment);
                    rate.setIsIndicator(false);
                }else {
                    String t = title.getText().toString();
                    String c = comment.getText().toString();
                    int r = (int) (rate.getRating()*2);

                    if (t.length()<3 || c.length()<3){
                        Toast.makeText(ProductActivity.this, "Invalid input.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Review review1 = new Review(t, User.Name, (byte) r,User.bitmap, c );
                    DB.saveOrUpdateProductReview(review1);

                    disable(title);
                    disable(comment);
                    rate.setIsIndicator(true);
                    edit.setText("Edit");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("");
                title.setHint("Title");

                comment.setText("");
                comment.setHint("Comment");

                name.setText(User.Name);
                image.setImageBitmap(User.bitmap);
                rate.setRating((float) (0f));
                edit.setText("Add");

                disable(title);
                disable(comment);

                DB.saveOrUpdateProductReview(null);
            }
        });



        ProductReview productReview = new ProductReview(
                Review.getAllReviews(),
                this
        );

        ListView lv = findViewById(R.id.pa_list);
        lv.setAdapter(productReview);

    }

    public static void disable(EditText editText){
        editText.setEnabled(false);
////        editText.setClickable(false);
//        editText.setFocusable(false);
    }
    public static void enable(EditText editText){
        editText.setEnabled(true);
//  //      editText.setClickable(true);
//        editText.setFocusable(true);
    }
}