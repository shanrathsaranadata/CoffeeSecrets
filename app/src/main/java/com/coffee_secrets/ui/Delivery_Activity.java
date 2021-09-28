package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.User;

public class Delivery_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_activity);

        TextView address = findViewById(R.id.da_address);
        TextView phone = findViewById(R.id.da_phone);
        TextView name = findViewById(R.id.da_name);
        TextView email = findViewById(R.id.da_email);

        address.setText(User.getAddress());
        phone.setText(User.ContactNum);
        email.setText(User.Email);
        name.setText(User.Name);

        Button update = findViewById(R.id.da_update);


    }
}