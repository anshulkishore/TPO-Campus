package com.example.anshul.tpocampus.InterviewExperience;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddFulltimeInterviewExperience extends AppCompatActivity {

    EditText your_name, comp_name, int_year, int_exp;
    String name, c_name, year, exp;

    Button add_exp;

    private DatabaseReference mDatabase;
    InterviewExpInfo interviewExpInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fulltime_interview_experience);

        setTitle("Add Interview Experience");

        your_name = (EditText) findViewById(R.id.add_your_name2);
        comp_name = (EditText) findViewById(R.id.add_company_name2);
        int_year = (EditText) findViewById(R.id.add_year2);
        int_exp = (EditText) findViewById(R.id.add_fulltime_exp);
        add_exp = (Button) findViewById(R.id.add_fulltime_exp_button);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = your_name.getText().toString();
                c_name = comp_name.getText().toString();
                year = int_year.getText().toString();
                exp = int_exp.getText().toString();

                interviewExpInfo = new InterviewExpInfo(name, c_name, year, exp);

                mDatabase.child("interview_experiences").child("fulltime").child(c_name + " (" + year + ") by " + name).setValue(interviewExpInfo).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Interview experience added", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });
            }
        });

    }
}
