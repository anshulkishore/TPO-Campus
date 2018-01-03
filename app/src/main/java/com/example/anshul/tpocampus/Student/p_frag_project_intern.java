package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ANSHUL KISHORE on 11-10-2017.
 */

public class p_frag_project_intern extends Fragment{

    TextView p_tv_projects, p_tv_organisation, p_tv_description;

    String logged_in_user_regno, logged_in_user_batch, logged_in_user_branch, flag;
    private static final String DEFAULT = "N/A";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    UserInfo_projects uInfo_projects = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.p_frag_project_intern, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        p_tv_projects = (TextView)getActivity().findViewById(R.id.f_tv_projects);
        p_tv_organisation = (TextView)getActivity().findViewById(R.id.f_tv_organisation);
        p_tv_description = (TextView)getActivity().findViewById(R.id.f_tv_description);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        SharedPreferences f_per_sharedPreferences2 = getActivity().getSharedPreferences("selected_student_profile", Context.MODE_PRIVATE);
        SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);

        flag = f_per_sharedPreferences2.getString("flag", "0");

        if(flag.equals("1")){
            logged_in_user_regno = f_per_sharedPreferences2.getString("student_regno", DEFAULT);
            //logged_in_user_batch = f_per_sharedPreferences2.getString("student_batch", DEFAULT);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        for(DataSnapshot ds2 : ds.getChildren()){
                            for(DataSnapshot ds3 : ds2.getChildren()){
                                if((ds3.getKey().toString()).equals(logged_in_user_regno)){
                                    logged_in_user_batch = ds.getKey().toString();
                                    break;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            logged_in_user_regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
            logged_in_user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);
        }

        mDatabase = mFirebaseDatabase.getReference();

        uInfo_projects = new UserInfo_projects();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                uInfo_projects = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("projects_intern").getValue(UserInfo_projects.class);

                p_tv_projects.setText(uInfo_projects.getProjects());
                p_tv_organisation.setText(uInfo_projects.getOrganisation());
                p_tv_description.setText(uInfo_projects.getDescription());

                Log.e("!_@@_Key::>", uInfo_projects.getProjects());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
