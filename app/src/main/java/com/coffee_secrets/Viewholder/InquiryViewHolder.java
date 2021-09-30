package com.coffee_secrets.Viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coffee_secrets.Interface.ItemClickListner;
import com.coffee_secrets.R;

public class InquiryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public EditText Inquiry_et;
    public Button Inquiry_updatebtn,Inquiry_deletebtn;
    public ItemClickListner listner;

    public InquiryViewHolder(@NonNull View itemView) {
        super(itemView);

        Inquiry_et = itemView.findViewById(R.id.inquiry_et);
        Inquiry_updatebtn = itemView.findViewById(R.id.inquiry_updatebtn);
        Inquiry_deletebtn = itemView.findViewById(R.id.inquiry_deletebtn);
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }

    public void setListner(ItemClickListner listner) {
        this.listner = listner;
    }

}
