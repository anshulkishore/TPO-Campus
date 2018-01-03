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
import com.example.anshul.tpocampus.UserData.AddUserAcademicData;
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

public class frag_academic extends Fragment{
    Button aca_submit;
    FloatingActionButton aca_fab;
    EditText aca_e_curr_cpi, aca_e_10th_school, aca_e_10th_board, aca_e_10th_year, aca_e_10th_percentage,
             aca_e_12th_school, aca_e_12th_board, aca_e_12th_year, aca_e_12th_percentage;

    String is_for_editing, logged_in_user_regno, logged_in_user_batch;

    private static final String DEFAULT = "N/A";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase, pwd_mDatabase;

    UserInfo_academic uInfo_academic = null;

   // Communicator comm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_academic, container, false);
    }

    public void save_academic(View view){
        SharedPreferences aca_sharedPreferences = this.getActivity().getSharedPreferences("My_data_academic", Context.MODE_PRIVATE);
        SharedPreferences.Editor aca_editor = aca_sharedPreferences.edit();

        aca_editor.putString("curr_cpi", aca_e_curr_cpi.getText().toString());
        aca_editor.putString("10th_school", aca_e_10th_school.getText().toString());
        aca_editor.putString("10th_board", aca_e_10th_board.getText().toString());
        aca_editor.putString("10th_year", aca_e_10th_year.getText().toString());
        aca_editor.putString("10th_percentage", aca_e_10th_percentage.getText().toString());
        aca_editor.putString("12th_school", aca_e_12th_school.getText().toString());
        aca_editor.putString("12th_board", aca_e_12th_board.getText().toString());
        aca_editor.putString("12th_year", aca_e_12th_year.getText().toString());
        aca_editor.putString("12th_percentage", aca_e_12th_percentage.getText().toString());

        aca_editor.commit();
        Toast.makeText(this.getActivity(), "Data was saved successfully!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Enter Academic Details");

     //   comm = (Communicator) getActivity();

        aca_submit = (Button) getActivity().findViewById(R.id.aca_save);

        pwd_mDatabase = FirebaseDatabase.getInstance().getReference();
        //aca_fab = (FloatingActionButton)getActivity().findViewById(R.id.aca_fab);
        //submit.setOnClickListener(this);

        aca_e_curr_cpi = (EditText) getActivity().findViewById(R.id.aca_ecpi);
        aca_e_10th_school = (EditText) getActivity().findViewById(R.id.aca_e10school);
        aca_e_10th_board = (EditText) getActivity().findViewById(R.id.aca_e10board);
        aca_e_10th_year = (EditText) getActivity().findViewById(R.id.aca_e10year);
        aca_e_10th_percentage = (EditText) getActivity().findViewById(R.id.aca_e10per);
        aca_e_12th_school = (EditText) getActivity().findViewById(R.id.aca_e12school);
        aca_e_12th_board = (EditText) getActivity().findViewById(R.id.aca_e12board);
        aca_e_12th_year = (EditText) getActivity().findViewById(R.id.aca_e12year);
        aca_e_12th_percentage = (EditText) getActivity().findViewById(R.id.aca_e12per);

        sharedPreferences = getActivity().getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
        is_for_editing = sharedPreferences.getString("is_to_edit_profile", "no");

        if(is_for_editing.equalsIgnoreCase("yes")) {

            aca_submit.setText("Update");

            SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
            logged_in_user_regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
            logged_in_user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            uInfo_academic = new UserInfo_academic();

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    uInfo_academic = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("academic").getValue(UserInfo_academic.class);
                    //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                    aca_e_curr_cpi.setText(uInfo_academic.getCurrCpi());
                    aca_e_10th_school.setText(uInfo_academic.get_10th_school());
                    aca_e_10th_board.setText(uInfo_academic.get_10th_board());
                    aca_e_10th_year.setText(uInfo_academic.get_10th_year());
                    aca_e_10th_percentage.setText(uInfo_academic.get_10th_percentage());
                    aca_e_12th_school.setText(uInfo_academic.get_12th_school());
                    aca_e_12th_board.setText(uInfo_academic.get_12th_board());
                    aca_e_12th_year.setText(uInfo_academic.get_12th_year());
                    aca_e_12th_percentage.setText(uInfo_academic.get_12th_percentage());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }




        /*aca_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_academic(v);
            }
        });*/

        aca_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_for_editing.equalsIgnoreCase("yes")){

                    AddUserAcademicData addUserAcademicData = new AddUserAcademicData(aca_e_curr_cpi.getText().toString(),
                            aca_e_10th_school.getText().toString(),
                            aca_e_10th_board.getText().toString(),
                            aca_e_10th_year.getText().toString(),
                            aca_e_10th_percentage.getText().toString(),
                            aca_e_12th_school.getText().toString(),
                            aca_e_12th_board.getText().toString(),
                            aca_e_12th_year.getText().toString(),
                            aca_e_12th_percentage.getText().toString());

                    pwd_mDatabase.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("academic").setValue(addUserAcademicData).
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
                else
                    save_academic(v);
            }
        });

        /* String curr_cpi = e_curr_cpi.getText().toString().trim();
        String _10th_school = e_10th_school.getText().toString().trim();
        String _10th_board = e_10th_board.getText().toString().trim();
        String _10th_year = e_10th_year.getText().toString().trim();
        String _10th_percentage = e_10th_percentage.getText().toString().trim();
        String _12th_school = e_12th_school.getText().toString().trim();
        String _12th_board = e_12th_board.getText().toString().trim();
        String _12th_year = e_12th_year.getText().toString().trim();
        String _12th_percentage = e_12th_percentage.getText().toString().trim();

        String[] data = new String[9];

        data[0] = curr_cpi;
        data[1] = _10th_school;
        data[2] = _10th_board;
        data[3] = _10th_year;
        data[4] = _10th_percentage;
        data[5] = _12th_school;
        data[6] = _12th_board;
        data[7] = _12th_year;
        data[8] = _12th_percentage;

        comm.academic_to_project(data, 9); */

    }

}
