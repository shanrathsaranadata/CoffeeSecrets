package com.coffee_secrets.ui.beforehome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.ProgressBarAnimation;
import com.coffee_secrets.ui.basic.Home_Activity;

public class Spash extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spash);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.progress_bar_tv);


        DB.startUp start = new DB.startUp() {
            @Override
            public void done() {
                ProgressBarAnimation PBA = new ProgressBarAnimation(Spash.this,progressBar,textView,0f,100f);
                PBA.setDuration(5000);
                progressBar.setAnimation(PBA);
            }
        };



    }
}