package com.coffee_secrets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Login_2_Activity extends AppCompatActivity {

    private Button mSave;

    private FirebaseAuth mAuth;
    private EditText mName;
    private EditText mMail;
    private EditText mStretch;
    private EditText mCity;
    private EditText mConnu;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        mAuth = FirebaseAuth.getInstance();


        mSave = findViewById(R.id.save);
        mName = findViewById(R.id.Name);
        mMail = findViewById(R.id.mail);
        mStretch = findViewById(R.id.stretch);
        mCity = findViewById(R.id.city);
        mConnu = findViewById(R.id.connu);




        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Login_2_Activity.this,Home_Activity.class);
                startActivity(i);


            }
        });


        String name =  mName.getText().toString().trim();
        String mail =  mMail.getText().toString().trim();
        String stretch =  mStretch.getText().toString().trim();
        String city =  mCity.getText().toString().trim();
        String con =  mConnu.getText().toString().trim();

         if (name.isEmpty()){

             mName.setError("Name is reqired");
             mName.requestFocus();
             return;
         }

        if (mail.isEmpty()){

            mMail.setError("mail");
            mMail.requestFocus();
            return;
        }

        if (stretch.isEmpty()){

            mStretch.setError("Stretch");
            mStretch.requestFocus();
            return;
        }

        if (city.isEmpty()){

            mCity.setError("City");
            mCity.requestFocus();
            return;
        }

        if (con.isEmpty()){

            mConnu.setError("Connu");
            mConnu.requestFocus();
            return;
        }







    }
}