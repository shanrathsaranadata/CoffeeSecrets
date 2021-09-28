package com.coffee_secrets.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.coffee_secrets.R;
import com.coffee_secrets.adapters.CoffeeCats;
import com.coffee_secrets.obj.Coffee;
import com.coffee_secrets.obj.DB;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

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

        DB.load();

        drawerLayout = findViewById(R.id.drawer_layout1);
        navigationView = findViewById(R.id.nav1);
        mRe = findViewById(R.id.re);



        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<Coffee.Category> t = new ArrayList<>();

        for (int i=0; i<5; i++){
            Coffee.Category c = new Coffee.Category("Test "+i,
                    BitmapFactory.decodeResource(getResources(), R.drawable.espresso));

            t.add(c);
        }


        CoffeeCats coffeeCats =
                new CoffeeCats(t,this);

        ListView cardList = findViewById(R.id.mh_cat_list);
        cardList.setAdapter(coffeeCats);

        EditText search = findViewById(R.id.search_et);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Coffee.Category> newList = new ArrayList<>();
                for (int j=0; j<t.size(); j++){
                    if (charSequence.length()==0 || t.get(j).getName().startsWith(String.valueOf(charSequence))){
                        newList.add(t.get(j));
                    }
                    CoffeeCats coffeeCats = new CoffeeCats(newList,Home_Activity.this);
                    cardList.setAdapter(coffeeCats);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv = view.findViewById(R.id.cc_text);
                String catName = tv.getText().toString();

                Intent intent = new Intent(Home_Activity.this, NameActivity.class);
                intent.putExtra("CatName", catName);
                startActivity(intent);


            }
        });



//
//        mCata.setOnClickListener(new View.OnClickListener() {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(Home_Activity.this,NameActivity.class);
//                startActivity(i);
//
//            }
//        });
//
//
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