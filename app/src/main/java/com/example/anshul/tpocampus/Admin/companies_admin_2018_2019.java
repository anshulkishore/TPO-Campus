package com.example.anshul.tpocampus.Admin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ANSHUL KISHORE on 21-10-2017.
 */

public class companies_admin_2018_2019 extends Fragment {

    private RecyclerView cRecyclerView;
    private ArrayList<InternCompanyClass> cList = new ArrayList<>();
    DatabaseReference cRef;
    private InternCompanyAdapter internCompanyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.companies_admin_2018_2019, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Companies");

        //Recycler View
        cRecyclerView = (RecyclerView) getActivity().findViewById(R.id.company_list_2);
        internCompanyAdapter = new InternCompanyAdapter(cList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        cRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setAdapter(internCompanyAdapter);
        cRecyclerView.setLayoutManager(mLayoutManager);

        cRef = FirebaseDatabase.getInstance().getReference("pre final year").child("companies");
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails: dataSnapshot.getChildren())
                {
                    InternCompanyClass obj = new InternCompanyClass();
                    obj.setComp_name(userDetails.child("name").getValue().toString());
                    obj.setComp_profile(userDetails.child("profile").getValue().toString());
                    obj.setComp_stipend(userDetails.child("stipend").getValue().toString());
                    obj.setComp_location(userDetails.child("location").getValue().toString());
                    cList.add(obj);
                }

                internCompanyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        cRef.addListenerForSingleValueEvent(vel);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        internCompanyAdapter.getItemSelected(item);
        return super.onContextItemSelected(item);
    }
}

