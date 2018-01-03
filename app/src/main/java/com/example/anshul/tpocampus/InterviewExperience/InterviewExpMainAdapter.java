package com.example.anshul.tpocampus.InterviewExperience;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.Student.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by ANSHUL KISHORE on 19-12-2017.
 */

public class InterviewExpMainAdapter extends RecyclerView.Adapter<InterviewExpMainAdapter.MyViewHolder>  {

    private List<ItemMain> itemList;
    private Context cContext;

    private static final String DEFAULT = "N/A";
    SharedPreferences sharedPreferences1;
    private String logged_in_user_batch, logged_in_user_regno;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    String is_verified;

    UserInfo uInfo = null;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title_main);
        }

        @Override
        public void onClick(View v) {
            if(title.getText().equals("Fulltime Interview Experiences")){
                cContext.startActivity(new Intent(cContext, InterviewExpFulltime.class));
            }
            else if(title.getText().equals("Internship Interview Experiences")){
                cContext.startActivity(new Intent(cContext, InterviewExpIntern.class));
            }
            else if(title.getText().equals("Add Internship Interview Experience")){

                sharedPreferences1 = cContext.getSharedPreferences("current_session", Context.MODE_PRIVATE);
                logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);
                logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);

                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabase = mFirebaseDatabase.getReference(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal");

                uInfo = new UserInfo();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uInfo = dataSnapshot.getValue(UserInfo.class);
                        is_verified = uInfo.getVerified().toString();

                        if(is_verified.equalsIgnoreCase("Verified"))
                            cContext.startActivity(new Intent(cContext, AddInternInterviewExperience.class));
                        else
                            Toast.makeText(cContext, "You are not verified yet!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            else if(title.getText().equals("Add Fulltime Interview Experience")){

                sharedPreferences1 = cContext.getSharedPreferences("current_session", Context.MODE_PRIVATE);
                logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);
                logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);

                mFirebaseDatabase = FirebaseDatabase.getInstance();
                mDatabase = mFirebaseDatabase.getReference(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal");

                uInfo = new UserInfo();

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uInfo = dataSnapshot.getValue(UserInfo.class);
                        is_verified = uInfo.getVerified().toString();

                        if(is_verified.equalsIgnoreCase("Verified"))
                            cContext.startActivity(new Intent(cContext, AddFulltimeInterviewExperience.class));
                        else
                            Toast.makeText(cContext, "You are not verified yet!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public InterviewExpMainAdapter(List<ItemMain> itemList, Context cContext) {
        this.itemList = itemList;
        this.cContext = cContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.int_exp_main_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ItemMain itemMain = itemList.get(position);
        holder.title.setText(itemMain.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
