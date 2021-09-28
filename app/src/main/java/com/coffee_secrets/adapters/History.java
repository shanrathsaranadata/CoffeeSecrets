package com.coffee_secrets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Order;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class History extends BaseAdapter {

    ArrayList<Order> orders = new ArrayList<>();
    Context context;
    LayoutInflater inflter;

    public History(ArrayList<Order> orders, Context context) {
        this.context = context;
        this.orders = orders;
        inflter = (LayoutInflater.from(context));
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        //Return order id
        return orders.get(i).getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.order_history_adapter, null);
        ImageView image = view.findViewById(R.id.oha_image);
        TextView name = view.findViewById(R.id.oha_name);
        TextView price = view.findViewById(R.id.oha_price);
        TextView order_date = view.findViewById(R.id.oha_date);

        Order order = orders.get(i);

        image.setImageBitmap(order.getIcon());




        return null;
    }
}
