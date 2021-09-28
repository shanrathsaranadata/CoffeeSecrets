package com.coffee_secrets.ui;

import static com.coffee_secrets.obj.User.isEditTextContainEmail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button mLogin;
    private Button mReg;
    private Button forgetpw;
    private FirebaseAuth imauth;
    private ProgressDialog lodingbar;
    private EditText siemail,sipassword;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mLogin = findViewById(R.id.login);
        mReg = findViewById(R.id.reg);
        siemail=findViewById(R.id.lemail);
        sipassword=findViewById(R.id.lpassword);
        forgetpw= findViewById(R.id.da_delete);

        imauth = FirebaseAuth.getInstance();
        lodingbar=new ProgressDialog(this);


        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginuser();

            }
        });


        mReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,Login_2_Activity.class);
                startActivity(i);

            }
        });

        forgetpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = siemail.getText().toString();

                if(!(isEditTextContainEmail(siemail))){
                    siemail.setError("Enter Valid Email is required");
                    siemail.requestFocus();
                    return;
                }
                else {

                    imauth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Check email to get your reset your password!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                                        finish();
                                    } else {

                                        Toast.makeText(MainActivity.this, "Fail to send your reset password email! try again", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });



    }


    private void loginuser() {

        final String email = siemail.getText().toString();
        final String password = sipassword.getText().toString();

        if(!(isEditTextContainEmail(siemail))){
            siemail.setError("Enter Valid Email is required");
            siemail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            sipassword.setError("Password is required.");
            return;
        }

        if (password.length()<8){
            sipassword.setError("Password should be at least 8 characters.");
            return;
        }

        else {


            lodingbar.setTitle("User Login");
            lodingbar.setMessage("please wait,while we are checking");
            lodingbar.setCanceledOnTouchOutside(false);
            lodingbar.show();

            imauth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this,Home_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                                Toast.makeText(MainActivity.this, "Your login is Successful", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                lodingbar.dismiss();
                                Toast.makeText(MainActivity.this, "Please Write Valid Email and Password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
    }
}