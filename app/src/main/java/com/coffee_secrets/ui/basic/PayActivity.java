package com.coffee_secrets.ui.basic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.Order;

public class PayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        EditText name = findViewById(R.id.py_name);
        EditText number = findViewById(R.id.py_card);
        EditText cvc = findViewById(R.id.py_cvc);
        EditText expire = findViewById(R.id.py_ex_date);
        TextView total = findViewById(R.id.py_amount);

        int orderID =getIntent().getIntExtra("OrderID", -1);


        if (orderID==-1) {
            finish();
        }
        Order order = Order.get(orderID);
        total.setText("Rs. " + order.getTotal() + " /=");


        Button confirm = findViewById(R.id.py_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString();
                String Num = number.getText().toString();
                String CVC = cvc.getText().toString();
                String Expire = expire.getText().toString();

                if (Name.length()<3){
                    name.setError("Invalid name!");
                    return;
                }

                if (Num.length()!=16 || !Num.matches("[0-9]+")){
                    number.setError("Invalid number!");
                    return;
                }

                if (CVC.length()!=3 || !CVC.matches("[0-9]+")){
                    cvc.setError("Invalid CVC!");
                    return;
                }


                if (Expire.length()!=5 || !Expire.matches("((1[0-2])|(0[0-9])|(0-9))[/][2-3][0-9]")){
                    expire.setError("Invalid expiration date!");
                    return;
                }

                Intent i = new Intent(PayActivity.this,Delivery_Activity.class);
                i.putExtra("Order", orderID);
                order.setPayed(true);
                startActivity(i);

            }
        });

    }
}