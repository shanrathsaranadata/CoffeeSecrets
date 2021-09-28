package com.coffee_secrets.ui.beforehome;

import static com.coffee_secrets.obj.User.isEditTextContainEmail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.coffee_secrets.R;
import com.coffee_secrets.obj.DB;
import com.coffee_secrets.obj.User;
import com.coffee_secrets.ui.Home_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.theartofdev.edmodo.cropper.CropImage;


public class Login_2_Activity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    private Button mSave;

    private FirebaseAuth mAuth;
    private EditText mName;
    private EditText mMail;
    private EditText mStretch;
    private EditText mCity;
    private EditText mConnu;
    private ImageView imageView;
    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        mAuth = FirebaseAuth.getInstance();
        
        imageView = findViewById(R.id.dt_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity(imageuri)
                        .setAspectRatio(1,1)
                        .start(Login_2_Activity.this);
            }
        });


        mSave = findViewById(R.id.save);
        mName = findViewById(R.id.Name);
        mMail = findViewById(R.id.mail);
        mStretch = findViewById(R.id.stretch);
        mCity = findViewById(R.id.city);
        mConnu = findViewById(R.id.connu);
        EditText password = findViewById(R.id.password);




        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name =  mName.getText().toString().trim();
                String mail =  mMail.getText().toString().trim();
                String stretch =  mStretch.getText().toString().trim();
                String city =  mCity.getText().toString().trim();
                String con =  mConnu.getText().toString().trim();
                String pass = password.getText().toString();

                if (name.isEmpty()){

                    mName.setError("Name is required.");
                    mName.requestFocus();
                    return;
                }

                if(!(isEditTextContainEmail(mMail))){
                    mMail.setError("Enter Valid Email is required");
                    mMail.requestFocus();
                    return;
                }

                if (stretch.isEmpty()){

                    mStretch.setError("Street is required.");
                    mStretch.requestFocus();
                    return;
                }

                if (city.isEmpty()){

                    mCity.setError("City is required.");
                    mCity.requestFocus();
                    return;
                }

                if (con.isEmpty()){

                    mConnu.setError("Phone number is required.");
                    mConnu.requestFocus();
                    return;
                }

                if (imageuri==null){
                    Toast.makeText(Login_2_Activity.this,"Image can't br empty.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (DB.doesExists(mail)){
                    Toast.makeText(Login_2_Activity.this,"Email already in use.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pass.isEmpty()){
                    password.setError("Password is required.");
                    return;
                }

                if (pass.length()<8){
                    password.setError("Password should be at least 8 characters.");
                    return;
                }

                User.create(name,mail,stretch,city,con,imageuri,pass);
                DB.createOrUpdateUser(Login_2_Activity.this);
                Intent i = new Intent(Login_2_Activity.this, Home_Activity.class);
                startActivity(i);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data != null){

            CropImage.ActivityResult result =CropImage.getActivityResult(data);
            imageuri = result.getUri();
            imageView.setImageURI(imageuri);

        }
        else{

            Toast.makeText(this, "Error Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Login_2_Activity.this,Login_2_Activity.class));
            finish();

        }
    }
}