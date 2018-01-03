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

public class companies_admin_2017_2018 extends Fragment {

    private RecyclerView cRecyclerView;
    private ArrayList<FulltimeCompanyClass> cList = new ArrayList<>();
    DatabaseReference cRef;
    private FulltimeCompanyAdapter fulltimeCompanyAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.companies_admin_2017_2018, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Companies");

        //Recycler View
        cRecyclerView = (RecyclerView) getActivity().findViewById(R.id.company_list_1);
        fulltimeCompanyAdapter = new FulltimeCompanyAdapter(cList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        cRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setAdapter(fulltimeCompanyAdapter);
        cRecyclerView.setLayoutManager(mLayoutManager);

        cRef = FirebaseDatabase.getInstance().getReference("final year").child("companies");
        ValueEventListener vel=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails: dataSnapshot.getChildren())
                {
                    FulltimeCompanyClass obj=new FulltimeCompanyClass();

                    String n = userDetails.child("name").getValue().toString();
                    obj.setComp_name_1(userDetails.child("name").getValue().toString());

                    String p = userDetails.child("profile").getValue().toString();
                    obj.setComp_profile_1(userDetails.child("profile").getValue().toString());

                    String s = userDetails.child("salary").getValue().toString();
                    obj.setComp_salary_1(userDetails.child("salary").getValue().toString());

                    String l = userDetails.child("location").getValue().toString();
                    obj.setComp_location_1(userDetails.child("location").getValue().toString());
                    cList.add(obj);
                }

                fulltimeCompanyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        cRef.addListenerForSingleValueEvent(vel);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        fulltimeCompanyAdapter.getItemSelected_1(item);
        return super.onContextItemSelected(item);
    }
}
