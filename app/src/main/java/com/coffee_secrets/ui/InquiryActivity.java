package com.coffee_secrets.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.coffee_secrets.R;
import com.coffee_secrets.Viewholder.InquiryViewHolder;
import com.coffee_secrets.obj.Inquiry;
import com.coffee_secrets.obj.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class InquiryActivity extends AppCompatActivity {

    private DatabaseReference Inquiryref;
    private RecyclerView Inquirylist;
    RecyclerView.LayoutManager layoutManager;
    private EditText inquriysendet;
    private Button inquriysendbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);

        Inquirylist=findViewById(R.id.rcvinquiry);
        Inquirylist.setHasFixedSize(true);
        layoutManager =new GridLayoutManager(getApplicationContext(),1);
        Inquirylist.setLayoutManager(layoutManager);

        Inquiryref = FirebaseDatabase.getInstance().getReference();
        inquriysendet = findViewById(R.id.inquriysend_et);
        inquriysendbtn = findViewById(R.id.inquriysend_btn);

        inquriysendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInquiryinfo(inquriysendet,User.ID);
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();

        inquirydetelis();



    }

    private void inquirydetelis(){

        FirebaseRecyclerOptions<Inquiry> options =
                new FirebaseRecyclerOptions.Builder<Inquiry>().setQuery(Inquiryref.child("Inquiry").orderByChild("inquiry"), Inquiry.class)
                        .build();
        FirebaseRecyclerAdapter<Inquiry, InquiryViewHolder> adapter =
                new FirebaseRecyclerAdapter<Inquiry, InquiryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull InquiryViewHolder holder, int position, @NonNull Inquiry model) {

                        holder.Inquiry_et.setText(model.getInquiry());

                        holder.Inquiry_updatebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "yes",
                                                "no"

                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(InquiryActivity.this);
                                builder.setTitle("Do you want to Update this Inquiry. Are you");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if(i==0){

                                            UpdateInquiryinfo(holder.Inquiry_et, model.getUserid());
                                            dialogInterface.dismiss();

                                        }

                                        if(i==1){

                                            dialogInterface.dismiss();


                                        }


                                    }
                                });
                                builder.show();
                            }
                        });

                        holder.Inquiry_deletebtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "yes",
                                                "no"

                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(InquiryActivity.this);
                                builder.setTitle("Do you want to Delete this Inquiry. Are you");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i)
                                    {
                                        if(i==0){

                                            DeleteInquiryinfo(model.getUserid());
                                            dialogInterface.dismiss();


                                        }

                                        if(i==1){

                                            dialogInterface.dismiss();


                                        }


                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public InquiryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inquiry, viewGroup, false);
                        InquiryViewHolder holder = new InquiryViewHolder(view);
                        return holder;
                    }
                };

        Inquirylist.setAdapter(adapter);
        adapter.startListening();
    }

    private void DeleteInquiryinfo(String id) {


        Inquiryref.child("Inquiry").child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override

            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(InquiryActivity.this, "Inquiry is Deleted successfully", Toast.LENGTH_SHORT).show();
                inquirydetelis();
            }
        });

    }

    private void UpdateInquiryinfo(EditText inquiry_et, String id) {

        if(inquiry_et.getText().toString().equals("")){
            Toast.makeText(this, "Write down Inquiry", Toast.LENGTH_SHORT).show();
        }

        else{

            HashMap<String,Object> usermap = new HashMap<>();
            usermap.put("userid",id);
            usermap.put("inquiry",inquiry_et.getText().toString());

            Inquiryref.child("Inquiry").child(String.valueOf(id)).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(InquiryActivity.this, "Inquiry is Update successfully", Toast.LENGTH_SHORT).show();
                        inquirydetelis();

                    }

                }
            });

        }

    }

    private void SaveInquiryinfo(EditText inquiry_et, String id) {

        if(inquiry_et.getText().toString().equals("")){
            Toast.makeText(this, "Write down Inquiry", Toast.LENGTH_SHORT).show();
        }

        else{

            Random random = new Random();
            int x = random.nextInt(55);

            HashMap<String,Object> usermap = new HashMap<>();
            usermap.put("userid",String.valueOf(id+x));
            usermap.put("inquiry",inquiry_et.getText().toString());


            Inquiryref.child("Inquiry").child(String.valueOf(id+x)).updateChildren(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        inquriysendet.setText("");
                        Toast.makeText(InquiryActivity.this, "Inquiry is Update successfully", Toast.LENGTH_SHORT).show();
                        inquirydetelis();

                    }

                }
            });

        }

    }


    }