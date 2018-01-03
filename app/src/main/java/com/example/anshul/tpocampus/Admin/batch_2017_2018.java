package com.example.anshul.tpocampus.Admin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anshul.tpocampus.Student.ModelClass;
import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.Student.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ANSHUL KISHORE on 13-10-2017.
 */

public class batch_2017_2018 extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<ModelClass> uList = new ArrayList<>();
    DatabaseReference mRef;
    private UserAdapter userAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.batch_2017_2018, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Students");

        //Recycler View
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.student_list_2);
        userAdapter = new UserAdapter(uList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(userAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

//        SharedPreferences sel_stu_sharedPreferences = getActivity().getSharedPreferences("selected_student_profile", Context.MODE_PRIVATE);
//        SharedPreferences.Editor sel_stu_editor = sel_stu_sharedPreferences.edit();
//
//        sel_stu_editor.putString("student_batch", "final year");
//        sel_stu_editor.commit();
        //send a query to database
        mRef = FirebaseDatabase.getInstance().getReference("final year").child("users");
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails: dataSnapshot.getChildren())
                {
                    ModelClass obj=new ModelClass();
                    obj.setName(userDetails.child("personal").child("name").getValue().toString());
                    obj.setRegno(userDetails.child("personal").child("regno").getValue().toString());
                    obj.setCourse(userDetails.child("personal").child("course").getValue().toString());
                    obj.setBranch(userDetails.child("personal").child("branch").getValue().toString());
                    uList.add(obj);
                }

                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mRef.addListenerForSingleValueEvent(vel);
    }
}
