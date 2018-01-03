package com.example.anshul.tpocampus.InterviewExperience;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;

public class DisplayInterviewExp extends AppCompatActivity {

    TextView display_comp_name, display_student_name, display_year, display_exp_details;
    String company_name, student_name, year, experience_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_interview_exp);

        setTitle("Interview Experience");

        display_comp_name = (TextView) findViewById(R.id.display_comp_name);
        display_student_name = (TextView) findViewById(R.id.display_student_name);
        display_year = (TextView) findViewById(R.id.display_year);
        display_exp_details = (TextView) findViewById(R.id.display_exp_details);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        company_name = bundleExtras.getString("company_name");
        student_name = bundleExtras.getString("student_name");
        year = bundleExtras.getString("year");
        experience_details = bundleExtras.getString("exp" );

        display_comp_name.setText(company_name);
        display_student_name.setText(student_name);
        display_year.setText(year);
        display_exp_details.setText(experience_details);
    }
}
