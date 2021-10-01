package com.coffee_secrets.ui.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Order;
import com.coffee_secrets.obj.User;

public class Delivery_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_activity);

        int orderID = getIntent().getIntExtra("Order", -1);
        if (orderID==-1) finish();

        Order order = Order.get(orderID);


        EditText address = findViewById(R.id.da_address);
        EditText phone = findViewById(R.id.da_phone);
        EditText name = findViewById(R.id.da_name);
        EditText email = findViewById(R.id.da_email);

        address.setText(User.getAddress());
        phone.setText(User.ContactNum);
        email.setText(User.Email);
        name.setText(User.Name);

        Button edit = findViewById(R.id.da_edit);
        Button delete = findViewById(R.id.da_delete);
        Button confirm = findViewById(R.id.da_confirm);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                address.setEnabled(true);
                phone.setEnabled(true);
                email.setEnabled(true);
                //name.setEnabled(true);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order.delete();
                Toast.makeText(Delivery_Activity.this, "Order deleted successfully",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Delivery_Activity.this,
                        Home_Activity.class));

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!User.isEditTextContainEmail(email)){
                    email.setError("Invalid email");
                    return;
                };
                if (!User.isEditTextContainphone(phone)){
                    phone.setError("Invalid email");
                    return;
                };

                if (address.getText().toString().isEmpty()){
                    address.setError("Address can't be empty.");
                    return;
                }

                //TODO validate number

                if (address.isEnabled()){
                    order.setChangedDetails(
                            address.getText().toString(),
                            name.getText().toString(),
                            phone.getText().toString(),
                            email.getText().toString()
                            );
                }


                Toast.makeText(Delivery_Activity.this, "Order will be delivered ASAP",
                        Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Delivery_Activity.this,
                        Home_Activity.class));
            }
        });



    }
}