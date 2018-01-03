package com.example.anshul.tpocampus.InterviewExperience;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.Student.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by ANSHUL KISHORE on 19-12-2017.
 */

public class InterviewExpAdapter extends RecyclerView.Adapter<InterviewExpAdapter.MyViewHolder> {

    private List<ItemExp> itemList;
    private Context cContext;
    SharedPreferences sharedPreferences1;
    private static final String DEFAULT = "N/A";
    private String logged_in_user_batch, logged_in_user_regno;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    String is_verified;

    UserInfo uInfo = null;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView exp_company_name, exp_student_name, exp_year;
        public String exp;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            exp_company_name = (TextView) view.findViewById(R.id.exp_company_name);
            exp_student_name = (TextView) view.findViewById(R.id.exp_student_name);
            exp_year = (TextView) view.findViewById(R.id.exp_year);
        }

        @Override
        public void onClick(View v) {
                Intent intent = new Intent(cContext, DisplayInterviewExp.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_name", exp_company_name.getText().toString());
                bundle.putString("student_name", exp_student_name.getText().toString());
                bundle.putString("year", exp_year.getText().toString());
                bundle.putString("exp", exp);
                intent.putExtras(bundle);
                cContext.startActivity(intent);

        }
    }

    public InterviewExpAdapter(List<ItemExp> itemList, Context cContext) {
        this.itemList = itemList;
        this.cContext = cContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.interview_exp_list, parent, false);

        return new InterviewExpAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InterviewExpAdapter.MyViewHolder holder, int position) {
        ItemExp itemExp = itemList.get(position);
        holder.exp_company_name.setText(itemExp.getCompany_name());
        holder.exp_student_name.setText(itemExp.getStudent_name());
        holder.exp_year.setText(itemExp.getYear());
        holder.exp = itemExp.getExperience_details();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
