package com.coffee_secrets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Review;

import java.util.ArrayList;

public class ProductReview extends BaseAdapter {

    ArrayList<Review> reviews;
    Context context;
    LayoutInflater inflter;

    public ProductReview(ArrayList<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.pror, null);

        TextView title = view.findViewById(R.id.textView8);
        TextView comment = view.findViewById(R.id.b_name);
        TextView name = view.findViewById(R.id.dt_ingr);

        ImageView image = view.findViewById(R.id.imageView5);
        RatingBar rate = view.findViewById(R.id.dt_rating);

        Review review = reviews.get(i);
        title.setText(review.getTitle());
        comment.setText(review.getComment());
        name.setText(review.getName());
        image.setImageBitmap(review.getImage());
        rate.setRating((float) (review.getRating()/2));

        return view;
    }
}
