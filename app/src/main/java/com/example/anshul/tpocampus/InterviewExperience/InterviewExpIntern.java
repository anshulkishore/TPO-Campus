package com.example.anshul.tpocampus.InterviewExperience;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.anshul.tpocampus.Admin.InternCompanyClass;
import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InterviewExpIntern extends AppCompatActivity {

    private List<ItemExp> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InterviewExpAdapter mAdapter;

    private static final String DEFAULT = "N/A";
    private String logged_in_user_batch;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_int_exp);

        setTitle("Internship Interview Experiences");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_IntExp);

        SharedPreferences sharedPreferences1 = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);

        mAdapter = new InterviewExpAdapter(itemList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        prepareData();
    }

    private void prepareData() {

        mDatabase = FirebaseDatabase.getInstance().getReference("interview_experiences").child("internship");
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails: dataSnapshot.getChildren())
                {
                    ItemExp obj = new ItemExp();
                    obj.setCompany_name(userDetails.child("company_name").getValue().toString());
                    obj.setStudent_name(userDetails.child("name").getValue().toString());
                    obj.setYear(userDetails.child("year_of_interview").getValue().toString());
                    obj.setExperience_details(userDetails.child("exp").getValue().toString());
                    itemList.add(obj);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(vel);

    }
}
