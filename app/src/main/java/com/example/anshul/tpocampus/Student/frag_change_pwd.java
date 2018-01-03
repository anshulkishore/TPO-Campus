package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshul.tpocampus.Admin.ChartInfoClass;
import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.UserData.AddUserAcademicData;
import com.example.anshul.tpocampus.UserData.AddUserPersonalData;
import com.example.anshul.tpocampus.UserData.AddUserProjectData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ANSHUL KISHORE on 06-10-2017.
 */

public class frag_change_pwd extends Fragment {

    Button pwd_register;
    public static final String DEFAULT = "N/A";

    String pwd_batch, pwd_name, pwd_regno, pwd_course, pwd_branch, pwd_dob, pwd_email, pwd_gender, pwd_category,
           pwd_phy_challenged, pwd_res_status, pwd_guardian, pwd_present_addr, pwd_parmanent_addr, pwd_marital_status,
           pwd_state, pwd_country, pwd_tpo_credits, pwd_verified, pwd_intern_placement, pwd_locked;

    String pwd_curr_cpi, pwd_10th_school, pwd_10th_board, pwd_10th_year, pwd_10th_percentage, pwd_12th_school,
           pwd_12th_board, pwd_12th_year, pwd_12th_percentage;

    String pwd_projects, pwd_description, pwd_organisation;

    String pwd_password, pwd_confirm_password;

    EditText pwd_enter_pwd, pwd_confirm_pwd;
    TextView pwd_note;

    private DatabaseReference pwd_mDatabase, statsDatabase;

    private ProgressBar pwd_progressBar;
    private FirebaseAuth pwd_auth;

    ChartInfoClass chartInfoClass = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_change_pwd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pwd_mDatabase = FirebaseDatabase.getInstance().getReference();
        pwd_auth = FirebaseAuth.getInstance();

        pwd_register = (Button)getActivity().findViewById(R.id.pwd_register);
        pwd_enter_pwd = (EditText) getActivity().findViewById(R.id.pwd_enter_pwd);
        pwd_confirm_pwd = (EditText) getActivity().findViewById(R.id.pwd_confirm_pwd);
        pwd_note = (TextView) getActivity().findViewById(R.id.pwd_note);

        pwd_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pwd_password = pwd_enter_pwd.getText().toString().trim();
                pwd_confirm_password = pwd_confirm_pwd.getText().toString().trim();

               if(pwd_password.equals(pwd_confirm_password)){
                    addUser(v);

                   //String email = inputEmail.getText().toString().trim();
                   //String password = inputPassword.getText().toString().trim();

                   if (pwd_email.equals("")) {
                       Toast.makeText(getActivity(), "Enter email address!", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   if (pwd_password.equals("")) {
                       Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   if (pwd_password.length() < 6) {
                       Toast.makeText(getActivity(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                       return;
                   }

                   //pwd_progressBar.setVisibility(View.VISIBLE);

                   //create user
                   /*pwd_auth.createUserWithEmailAndPassword(pwd_email, pwd_password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                           pwd_progressBar.setVisibility(View.GONE);
                           // If sign in fails, display a message to the user. If sign in succeeds
                           // the auth state listener will be notified and logic to handle the
                           // signed in user can be handled in the listener.
                           if (!task.isSuccessful()) {
                               Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                       Toast.LENGTH_SHORT).show();
                           } else {
                               getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                               getActivity().finish();
                           }
                       }
                   });*/

//                   Intent intent = new Intent(getActivity(), Navigation.class);
//                   getActivity().startActivity(intent);
//                   getActivity().finish();
               }
               else{
                   Toast.makeText(getActivity(), "Password Mismatch!!", Toast.LENGTH_LONG).show();
               }

            }
        });
    }

    public void addUser(View view){

        SharedPreferences pwd_sharedPreferences1 = this.getActivity().getSharedPreferences("My_data_personal", Context.MODE_PRIVATE);
        SharedPreferences pwd_sharedPreferences2 = this.getActivity().getSharedPreferences("My_data_academic", Context.MODE_PRIVATE);
        SharedPreferences pwd_sharedPreferences3 = this.getActivity().getSharedPreferences("My_data_projects", Context.MODE_PRIVATE);
        SharedPreferences.Editor pwd_editor1 = pwd_sharedPreferences1.edit();
        final SharedPreferences login_sharedPreferences = this.getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
        final SharedPreferences.Editor login_editor = login_sharedPreferences.edit();

        pwd_editor1.putString("password", pwd_password);
        pwd_batch = pwd_sharedPreferences1.getString("batch", DEFAULT);
        pwd_name = pwd_sharedPreferences1.getString("name", DEFAULT);
        pwd_regno = pwd_sharedPreferences1.getString("regno", DEFAULT);
        pwd_course = pwd_sharedPreferences1.getString("course", DEFAULT);
        pwd_branch = pwd_sharedPreferences1.getString("branch", DEFAULT);
        pwd_dob = pwd_sharedPreferences1.getString("dob", DEFAULT);
        pwd_email = pwd_sharedPreferences1.getString("email", DEFAULT);
        pwd_gender = pwd_sharedPreferences1.getString("gender", DEFAULT);
        pwd_category = pwd_sharedPreferences1.getString("category", DEFAULT);
        pwd_phy_challenged = pwd_sharedPreferences1.getString("phy_challenged", DEFAULT);
        pwd_res_status = pwd_sharedPreferences1.getString("res_status", DEFAULT);
        pwd_guardian = pwd_sharedPreferences1.getString("guardian", DEFAULT);
        pwd_present_addr = pwd_sharedPreferences1.getString("present_addr", DEFAULT);
        pwd_parmanent_addr = pwd_sharedPreferences1.getString("parmanent_addr", DEFAULT);
        pwd_marital_status = pwd_sharedPreferences1.getString("marital_status", DEFAULT);
        pwd_state = pwd_sharedPreferences1.getString("state", DEFAULT);
        pwd_country = pwd_sharedPreferences1.getString("country", DEFAULT);
        pwd_tpo_credits = pwd_sharedPreferences1.getString("tpo_credits", DEFAULT);
        pwd_verified = pwd_sharedPreferences1.getString("verified", DEFAULT);
        pwd_intern_placement = pwd_sharedPreferences1.getString("intern_placement", DEFAULT);
        pwd_locked = pwd_sharedPreferences1.getString("locked", DEFAULT);

        pwd_curr_cpi = pwd_sharedPreferences2.getString("curr_cpi", DEFAULT);
        pwd_10th_school = pwd_sharedPreferences2.getString("10th_school", DEFAULT);
        pwd_10th_board = pwd_sharedPreferences2.getString("10th_board", DEFAULT);
        pwd_10th_year = pwd_sharedPreferences2.getString("10th_year", DEFAULT);
        pwd_10th_percentage = pwd_sharedPreferences2.getString("10th_percentage", DEFAULT);
        pwd_12th_school = pwd_sharedPreferences2.getString("12th_school", DEFAULT);
        pwd_12th_board = pwd_sharedPreferences2.getString("12th_board", DEFAULT);
        pwd_12th_year = pwd_sharedPreferences2.getString("12th_year", DEFAULT);
        pwd_12th_percentage = pwd_sharedPreferences2.getString("12th_percentage", DEFAULT);

        pwd_projects = pwd_sharedPreferences3.getString("projects", DEFAULT);
        pwd_description = pwd_sharedPreferences3.getString("description", DEFAULT);
        pwd_organisation = pwd_sharedPreferences3.getString("organisation", DEFAULT);

        statsDatabase = FirebaseDatabase.getInstance().getReference("admin").child(pwd_batch + " stats").child(pwd_branch);
        statsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                String total = chartInfoClass.getTotal();
                int total_int = Integer.parseInt(total);

                total_int++;

                pwd_mDatabase.child("admin").child(pwd_batch + " stats").child(pwd_branch).child("total").setValue(String.valueOf(total_int)).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        AddUserPersonalData addUserPersonalData = new AddUserPersonalData(pwd_batch, pwd_name, pwd_regno, pwd_course,
                pwd_branch, pwd_dob, pwd_email,
                pwd_gender, pwd_category, pwd_phy_challenged, pwd_res_status,
                pwd_guardian, pwd_present_addr, pwd_parmanent_addr,
                pwd_marital_status, pwd_state, pwd_country, pwd_tpo_credits, pwd_verified, pwd_intern_placement, pwd_locked);

        AddUserAcademicData addUserAcademicData = new AddUserAcademicData(pwd_curr_cpi, pwd_10th_school, pwd_10th_board,
                pwd_10th_year, pwd_10th_percentage, pwd_12th_school, pwd_12th_board,
                pwd_12th_year, pwd_12th_percentage);

        AddUserProjectData addUserProjectData = new AddUserProjectData(pwd_projects, pwd_description, pwd_organisation);

        pwd_mDatabase.child(pwd_batch).child("users").child(pwd_regno).child("personal").setValue(addUserPersonalData).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        }
                    }


                });

        pwd_mDatabase.child(pwd_batch).child("users").child(pwd_regno).child("academic").setValue(addUserAcademicData).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        }
                    }


                });

        pwd_mDatabase.child(pwd_batch).child("users").child(pwd_regno).child("projects_intern").setValue(addUserProjectData).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        }
                    }


                });

        pwd_mDatabase.child(pwd_batch).child("users").child(pwd_regno).child("password").setValue(pwd_password).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            login_editor.putString("logged_in", pwd_regno);
                            login_editor.putString("session", "1");
                            login_editor.putString("logged_in_branch", pwd_branch);
                            login_editor.putString("logged_in_batch", pwd_batch);
                            login_editor.commit();
                            //pwd_progressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "User added", Toast.LENGTH_LONG).show();
                            getActivity().startActivity(new Intent(getActivity(), Navigation.class));
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        }
                    }


                });

        pwd_mDatabase.child(pwd_batch).child("users").child(pwd_regno).child("company_selected").setValue("None").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                        }
                    }


                });
    }

}
