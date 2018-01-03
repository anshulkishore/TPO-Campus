package com.example.anshul.tpocampus.Student;

import android.content.Context;
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
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ANSHUL KISHORE on 11-10-2017.
 */

public class p_frag_change_pwd extends Fragment{

    EditText old_pwd, new_pwd, confirm_pwd;
    Button submit;

    String user_password;

    String logged_in_user_regno, logged_in_user_batch, logged_in_user_branch, flag;
    private static final String DEFAULT = "N/A";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase, pwdDatabase;

    UserInfo_password userInfo_password = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.p_frag_change_pwd, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        old_pwd = (EditText) getActivity().findViewById(R.id.f_eold_pwd);
        new_pwd = (EditText) getActivity().findViewById(R.id.f_enew_pwd);
        confirm_pwd = (EditText) getActivity().findViewById(R.id.f_econfirm_pwd);
        submit = (Button) getActivity().findViewById(R.id.bconfirm);

        SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
        logged_in_user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();
        pwdDatabase = FirebaseDatabase.getInstance().getReference();

        userInfo_password = new UserInfo_password();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInfo_password = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).getValue(UserInfo_password.class);
                //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);
                user_password = userInfo_password.getPassword();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(old_pwd.getText().toString().equals(user_password)){
                    if(new_pwd.getText().toString().equals(confirm_pwd.getText().toString())){
                        pwdDatabase.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("password").setValue(new_pwd.getText().toString()).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            Toast.makeText(getContext(), "Password changed", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getActivity(), "Error! Try again!", Toast.LENGTH_LONG).show();
                                        }
                                    }


                                });
                    }
                    else{
                        Toast.makeText(getActivity(), "Password missmatch!!", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Incorrect old password!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
