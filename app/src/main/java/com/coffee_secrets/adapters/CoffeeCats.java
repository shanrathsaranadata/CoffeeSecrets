package com.coffee_secrets.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Coffee;

import java.util.ArrayList;

public class CoffeeCats extends BaseAdapter {

    ArrayList<Coffee.Category> categories;
    LayoutInflater inflter;
    Context context;

    public CoffeeCats(ArrayList<Coffee.Category> categories, Context context) {
        this.categories = categories;
        inflter = (LayoutInflater.from(context));
        this.context = context;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.coffee_cats, null);
        Coffee.Category category = categories.get(i);

        CardView card = view.findViewById(R.id.cc_card);
        TextView text = view.findViewById(R.id.cc_text);

        card.setBackground(new BitmapDrawable(context.getResources(),
                category.getBitmap()));
        text.setText(category.getName());

        return view;
    }
}
