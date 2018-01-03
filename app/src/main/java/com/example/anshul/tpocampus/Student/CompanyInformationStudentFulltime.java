package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyInformationStudentFulltime extends AppCompatActivity {

    TextView cf_tv_name, cf_tv_profile, cf_tv_salary, cf_tv_location, cf_tv_cutoff_cpi, cf_tv_test_date, cf_tv_reg_deadline,
            cf_tv_reg_deadline_time, cf_tv_branches_allowed, cf_tv_additional_info;

    String company_name, logged_in_user_batch;
    private static final String DEFAULT = "N/A";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    CompanyInformationFulltimeClass companyInformationFulltimeClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_information_student_fulltime);

        setTitle("Company Information");

        cf_tv_name = (TextView) findViewById(R.id.cf_tv_name);
        cf_tv_profile = (TextView) findViewById(R.id.cf_tv_profile);
        cf_tv_salary = (TextView) findViewById(R.id.cf_tv_salary);
        cf_tv_location = (TextView) findViewById(R.id.cf_tv_location);
        cf_tv_cutoff_cpi = (TextView) findViewById(R.id.cf_tv_cutoff_cpi);
        cf_tv_test_date = (TextView) findViewById(R.id.cf_tv_test_date);
        cf_tv_reg_deadline = (TextView) findViewById(R.id.cf_tv_reg_deadline);
        cf_tv_reg_deadline_time = (TextView) findViewById(R.id.cf_tv_reg_deadline_time);
        cf_tv_branches_allowed = (TextView) findViewById(R.id.cf_tv_branches_allowed);
        cf_tv_additional_info  = (TextView) findViewById(R.id.cf_tv_additional_info);

        //intent to receive name of company selected
        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        company_name = bundleExtras.getString("company_name");

        //to check if admin has asked for this class or logged in user
        //when user asks for it, it sends the batch in bundle
        boolean hasBatch = bundleExtras.containsKey("batch");

        if(hasBatch){
            logged_in_user_batch = bundleExtras.getString("batch");
        }
        else {
            SharedPreferences sharedPreferences = getSharedPreferences("current_session", Context.MODE_PRIVATE);
            logged_in_user_batch = sharedPreferences.getString("logged_in_batch", DEFAULT);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        companyInformationFulltimeClass = new CompanyInformationFulltimeClass();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                companyInformationFulltimeClass = dataSnapshot.child(logged_in_user_batch).child("companies").child(company_name).getValue(CompanyInformationFulltimeClass.class);

                cf_tv_name.setText(companyInformationFulltimeClass.getName());
                cf_tv_profile.setText(companyInformationFulltimeClass.getProfile());
                cf_tv_salary.setText(companyInformationFulltimeClass.getSalary());
                cf_tv_location.setText(companyInformationFulltimeClass.getLocation());
                cf_tv_cutoff_cpi.setText(companyInformationFulltimeClass.getCutoff_cpi());
                cf_tv_test_date.setText(companyInformationFulltimeClass.getTest_date());
                cf_tv_reg_deadline.setText(companyInformationFulltimeClass.getReg_deadline());
                cf_tv_reg_deadline_time.setText(companyInformationFulltimeClass.getReg_deadline_time());
                cf_tv_additional_info.setText(companyInformationFulltimeClass.getAdditional_info());

                ArrayList<String> allowed_branches = new ArrayList<String>();

                int index = 0;

                //making a string of all allowed branches
                if(companyInformationFulltimeClass.getCse().equals("yes"))
                    allowed_branches.add("CSE");
                if(companyInformationFulltimeClass.getIt().equals("yes"))
                    allowed_branches.add("IT");
                if(companyInformationFulltimeClass.getEce().equals("yes"))
                    allowed_branches.add("ECE");
                if(companyInformationFulltimeClass.getEe().equals("yes"))
                    allowed_branches.add("EE");
                if(companyInformationFulltimeClass.getChem().equals("yes"))
                    allowed_branches.add("CHEMICAL");
                if(companyInformationFulltimeClass.getCivil().equals("yes"))
                    allowed_branches.add("CIVIL");
                if(companyInformationFulltimeClass.getMech().equals("yes"))
                    allowed_branches.add("MECHANICAL");
                if(companyInformationFulltimeClass.getPie().equals("yes"))
                    allowed_branches.add("PIE");
                if(companyInformationFulltimeClass.getMca().equals("yes"))
                    allowed_branches.add("MCA");
                if(companyInformationFulltimeClass.getBio().equals("yes"))
                    allowed_branches.add("BIOTECH");

                String str1 = allowed_branches.toString();
                //removing "[" and "]" from starting and end of string
                str1 = str1.substring(1, str1.length()-1);

                cf_tv_branches_allowed.setText(str1);

                //Log.e("!_@@_Key::>", uInfo_personal.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
