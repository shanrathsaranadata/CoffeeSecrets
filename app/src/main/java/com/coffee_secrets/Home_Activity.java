package com.coffee_secrets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.coffee_secrets.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Home_Activity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout mCata;
    private ImageView mRe;



    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        drawerLayout = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.nav1);
        mRe = findViewById(R.id.re);



        navigationView.setNavigationItemSelectedListener(this);


        mCata = findViewById(R.id.cata);




        mCata.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Home_Activity.this,NameActivity.class);
                startActivity(i);

            }
        });


        mRe.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.START);

            }
        });




        //navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) Home_Activity.this);


     /*  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_navigation,
                R.string.close_navigation);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
*/

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_favourits:
                Intent i = new Intent(Home_Activity.this,favouriteActivity.class);
                startActivity(i);
                break;
            case R.id.oder_hi:
                Intent ii = new Intent(Home_Activity.this,OderHistoryActivity.class);
                startActivity(ii);
                break;

            case R.id.cart:
                Intent c = new Intent(Home_Activity.this,CartActivity.class);
                startActivity(c);
                break;

            case R.id.Coffebar:
                Intent co = new Intent(Home_Activity.this,CofffebarActivity.class);
                startActivity(co);
                break;

            case R.id.review:
                Intent re = new Intent(Home_Activity.this,ReviewActivity.class);
                startActivity(re);
                break;
            case R.id.app_review:
                Intent app = new Intent(Home_Activity.this,ProductActivity.class);
                startActivity(app);
                break;

            case R.id.praot:
                Intent pr = new Intent(Home_Activity.this,PromosActivity.class);
                startActivity(pr);
                break;


            case R.id.help:
                Intent he = new Intent(Home_Activity.this,HelpActivity.class);
                startActivity(he);
                break;

        }


        //drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}