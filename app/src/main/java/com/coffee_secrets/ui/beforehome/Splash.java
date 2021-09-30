package com.coffee_secrets.ui.beforehome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.ProgressBarAnimation;
import com.coffee_secrets.ui.basic.MainActivity;

public class Splash extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spash);

        progressBar = findViewById(R.id.progress_bar);
        textView = findViewById(R.id.progress_bar_tv);

        ProgressBarAnimation PBA = new ProgressBarAnimation(Splash.this, progressBar, textView, 0f, 100f);
        PBA.setDuration(30000);
        progressBar.setAnimation(PBA);

        new DB.startUp() {
            @Override
            public void done() {
                startActivity(new Intent(Splash.this, MainActivity.class));

            }
        };



    }
}