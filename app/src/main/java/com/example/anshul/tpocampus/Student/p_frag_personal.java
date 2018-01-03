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
import android.widget.ProgressBar;
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

public class p_frag_personal extends Fragment {

    TextView p_tv_batch, p_tv_name, p_tv_regno, p_tv_course, p_tv_branch, p_tv_dob, p_tv_email, p_tv_gender, p_tv_category,
            p_tv_phy_challenged, p_tv_res_status, p_tv_guardian, p_tv_present_addr, p_tv_parmanent_addr, p_tv_mar_status,
            p_tv_state, p_tv_country;

    String logged_in_user_regno, logged_in_user_batch = "pre final year", logged_in_user_branch, flag;
    private static final String DEFAULT = "N/A";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    UserInfo_personal uInfo_personal = null;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.p_frag_personal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Profile");

        p_tv_batch = (TextView) getActivity().findViewById(R.id.f_tv_batch);
        p_tv_name = (TextView) getActivity().findViewById(R.id.f_tv_name);
        p_tv_regno = (TextView) getActivity().findViewById(R.id.f_tv_regno);
        p_tv_course = (TextView) getActivity().findViewById(R.id.f_tv_course);
        p_tv_branch = (TextView) getActivity().findViewById(R.id.f_tv_branch);
        p_tv_dob = (TextView) getActivity().findViewById(R.id.f_tv_dob);
        p_tv_email = (TextView) getActivity().findViewById(R.id.f_tv_email);
        p_tv_gender = (TextView) getActivity().findViewById(R.id.f_tv_gender);
        p_tv_category = (TextView) getActivity().findViewById(R.id.f_tv_category);
        p_tv_phy_challenged = (TextView) getActivity().findViewById(R.id.f_tv_phy_challenged);
        p_tv_res_status = (TextView) getActivity().findViewById(R.id.f_tv_res_status);
        p_tv_guardian = (TextView) getActivity().findViewById(R.id.f_tv_guardian);
        p_tv_present_addr = (TextView) getActivity().findViewById(R.id.f_tv_present_addr);
        p_tv_parmanent_addr = (TextView) getActivity().findViewById(R.id.f_tv_parmanent_addr);
        p_tv_mar_status = (TextView) getActivity().findViewById(R.id.f_tv_mar_status);
        p_tv_state = (TextView) getActivity().findViewById(R.id.f_tv_state);
        p_tv_country = (TextView) getActivity().findViewById(R.id.f_tv_country);

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

        //mDatabase = mFirebaseDatabase.getReference(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal");
        mDatabase = mFirebaseDatabase.getReference();

        uInfo_personal = new UserInfo_personal();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uInfo_personal = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo_personal.class);
                //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                String str = uInfo_personal.getName().toString();
                p_tv_batch.setText(uInfo_personal.getBatch());
                p_tv_name.setText(uInfo_personal.getName());
                p_tv_regno.setText(uInfo_personal.getRegno());
                p_tv_course.setText(uInfo_personal.getCourse());
                p_tv_branch.setText(uInfo_personal.getBranch());
                p_tv_dob.setText(uInfo_personal.getDob());
                p_tv_email.setText(uInfo_personal.get_Email());
                p_tv_gender.setText(uInfo_personal.getGender());
                p_tv_category.setText(uInfo_personal.getCategory());
                p_tv_phy_challenged.setText(uInfo_personal.getPhy_challenged());
                p_tv_res_status.setText(uInfo_personal.getRes_status());
                p_tv_guardian.setText(uInfo_personal.getGuardian());
                p_tv_present_addr.setText(uInfo_personal.getPresent_addr());
                p_tv_parmanent_addr.setText(uInfo_personal.getParmanent_addr());
                p_tv_mar_status.setText(uInfo_personal.getMarital_status());
                p_tv_state.setText(uInfo_personal.getState());
                p_tv_country.setText(uInfo_personal.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
