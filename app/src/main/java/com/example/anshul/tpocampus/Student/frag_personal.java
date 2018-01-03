package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.UserData.AddUserPersonalData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ANSHUL KISHORE on 06-10-2017.
 */

public class frag_personal extends Fragment {
    Button per_submit;

    FloatingActionButton per_fab;

    EditText per_e_batch, per_e_name, per_e_regno, per_e_course, per_e_branch, per_e_dob, per_e_email, per_e_gender,
             per_e_category, per_e_phy_challenged, per_e_res_status, per_e_guardian, per_e_present_addr,
             per_e_parmanent_addr, per_e_marital_status, per_e_state, per_e_country;

    String per_tpo_credits = "10", per_verified = "Not Verified", is_for_editing, logged_in_user_regno, logged_in_user_batch;

    String per_e_tpo_credits, per_e_verified, per_e_intern_placement, per_e_locked;

    private static final String DEFAULT = "N/A";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase, pwd_mDatabase;

    UserInfo_personal uInfo_personal = null;

    //Communicator comm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_personal, container, false);
    }

    public void save_personal(View view){
        SharedPreferences per_sharedPreferences1 = this.getActivity().getSharedPreferences("My_data_personal", Context.MODE_PRIVATE);
        SharedPreferences.Editor per_editor1 = per_sharedPreferences1.edit();

        SharedPreferences per_sharedPreferences2 = this.getActivity().getSharedPreferences("regno_email", Context.MODE_PRIVATE);
        SharedPreferences.Editor per_editor2 = per_sharedPreferences2.edit();

        per_editor1.putString("batch", per_e_batch.getText().toString());
        per_editor1.putString("name", per_e_name.getText().toString());
        per_editor1.putString("regno", per_e_regno.getText().toString());
        per_editor1.putString("course", per_e_course.getText().toString());
        per_editor1.putString("branch", per_e_branch.getText().toString());
        per_editor1.putString("dob", per_e_dob.getText().toString());
        per_editor1.putString("email", per_e_email.getText().toString());
        per_editor1.putString("gender", per_e_gender.getText().toString());
        per_editor1.putString("category", per_e_category.getText().toString());
        per_editor1.putString("phy_challenged", per_e_phy_challenged.getText().toString());
        per_editor1.putString("res_status", per_e_res_status.getText().toString());
        per_editor1.putString("guardian", per_e_guardian.getText().toString());
        per_editor1.putString("present_addr", per_e_present_addr.getText().toString());
        per_editor1.putString("parmanent_addr", per_e_parmanent_addr.getText().toString());
        per_editor1.putString("marital_status", per_e_marital_status.getText().toString());
        per_editor1.putString("state", per_e_state.getText().toString());
        per_editor1.putString("country", per_e_country.getText().toString());
        per_editor1.putString("tpo_credits", per_tpo_credits);
        per_editor1.putString("verified", per_verified);
        per_editor1.putString("intern_placement", "Not Yet!!");
        per_editor1.putString("locked", "unlocked");

        per_editor1.commit();

        per_editor2.putString(per_e_regno.getText().toString(), per_e_email.getText().toString());
        per_editor2.commit();

        Toast.makeText(this.getActivity(), "Data was saved successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Enter Personal Details");

        per_submit = (Button) getActivity().findViewById(R.id.per_save);
        //per_fab = (FloatingActionButton)getActivity().findViewById(R.id.per_fab);
        //submit.setOnClickListener(this);
        pwd_mDatabase = FirebaseDatabase.getInstance().getReference();
       // comm = (Communicator) getActivity();

        per_e_batch = (EditText) getActivity().findViewById(R.id.per_ebatch);
        per_e_name = (EditText) getActivity().findViewById(R.id.per_ename);
        per_e_regno = (EditText) getActivity().findViewById(R.id.per_ereg);
        per_e_course = (EditText) getActivity().findViewById(R.id.per_ecourse);
        per_e_branch = (EditText) getActivity().findViewById(R.id.per_ebranch);
        per_e_dob = (EditText) getActivity().findViewById(R.id.per_eDOB);
        per_e_email = (EditText) getActivity().findViewById(R.id.per_eemail);
        per_e_gender = (EditText) getActivity().findViewById(R.id.per_egender);
        per_e_category = (EditText) getActivity().findViewById(R.id.per_ecategory);
        per_e_phy_challenged = (EditText) getActivity().findViewById(R.id.per_eph);
        per_e_res_status = (EditText) getActivity().findViewById(R.id.per_eres);
        per_e_guardian = (EditText) getActivity().findViewById(R.id.per_eguardian);
        per_e_present_addr = (EditText) getActivity().findViewById(R.id.per_epre_addr);
        per_e_parmanent_addr = (EditText) getActivity().findViewById(R.id.per_epar_addr);
        per_e_marital_status = (EditText) getActivity().findViewById(R.id.per_emar_status);
        per_e_state = (EditText) getActivity().findViewById(R.id.per_estate);
        per_e_country = (EditText) getActivity().findViewById(R.id.per_ecountry);

        sharedPreferences = getActivity().getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
        is_for_editing = sharedPreferences.getString("is_to_edit_profile", "no");

        if(is_for_editing.equalsIgnoreCase("yes")){

            per_submit.setText("Update");

            SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
            logged_in_user_regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
            logged_in_user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            uInfo_personal = new UserInfo_personal();

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    uInfo_personal = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo_personal.class);
                    //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                    per_e_batch.setText(uInfo_personal.getBatch());
                    per_e_name.setText(uInfo_personal.getName());
                    per_e_regno.setText(uInfo_personal.getRegno());
                    per_e_course.setText(uInfo_personal.getCourse());
                    per_e_branch.setText(uInfo_personal.getBranch());
                    per_e_dob.setText(uInfo_personal.getDob());
                    per_e_email.setText(uInfo_personal.get_Email());
                    per_e_gender.setText(uInfo_personal.getGender());
                    per_e_category.setText(uInfo_personal.getCategory());
                    per_e_phy_challenged.setText(uInfo_personal.getPhy_challenged());
                    per_e_res_status.setText(uInfo_personal.getRes_status());
                    per_e_guardian.setText(uInfo_personal.getGuardian());
                    per_e_present_addr.setText(uInfo_personal.getPresent_addr());
                    per_e_parmanent_addr.setText(uInfo_personal.getParmanent_addr());
                    per_e_marital_status.setText(uInfo_personal.getMarital_status());
                    per_e_state.setText(uInfo_personal.getState());
                    per_e_country.setText(uInfo_personal.getCountry());

                    per_e_tpo_credits = uInfo_personal.getTpoCredits();
                    per_e_verified = uInfo_personal.getVerified();
                    per_e_intern_placement = uInfo_personal.getIntern_placement();
                    per_e_locked = uInfo_personal.getLocked();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        /*per_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_personal(v);
            }
        });*/

        per_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_for_editing.equalsIgnoreCase("yes")){
                    AddUserPersonalData addUserPersonalData = new AddUserPersonalData(
                            per_e_batch.getText().toString(),
                            per_e_name.getText().toString(),
                            per_e_regno.getText().toString(),
                            per_e_course.getText().toString(),
                            per_e_branch.getText().toString(),
                            per_e_dob.getText().toString(),
                            per_e_email.getText().toString(),
                            per_e_gender.getText().toString(),
                            per_e_category.getText().toString(),
                            per_e_phy_challenged.getText().toString(),
                            per_e_res_status.getText().toString(),
                            per_e_guardian.getText().toString(),
                            per_e_present_addr.getText().toString(),
                            per_e_parmanent_addr.getText().toString(),
                            per_e_marital_status.getText().toString(),
                            per_e_state.getText().toString(),
                            per_e_country.getText().toString(),
                            per_e_tpo_credits,
                            per_e_verified,
                            per_e_intern_placement,
                            per_e_locked);

                    pwd_mDatabase.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal").setValue(addUserPersonalData).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }


                            });
                }
                else
                    save_personal(v);
            }
        });
    }
}


