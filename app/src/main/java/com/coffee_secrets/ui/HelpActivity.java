package com.coffee_secrets.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.coffee_secrets.R;
import com.coffee_secrets.ui.basic.Home_Activity;

public class HelpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        findViewById(R.id.help_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, Home_Activity.class));
            }
        });

        findViewById(R.id.faq_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, FaqActivity.class));
            }
        });

        findViewById(R.id.contactus_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, ConnectUsActivity.class));
            }
        });
        findViewById(R.id.inquiry_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpActivity.this, InquiryActivity.class));
            }
        });


    }
}