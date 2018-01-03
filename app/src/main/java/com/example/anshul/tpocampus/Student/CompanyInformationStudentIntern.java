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

public class CompanyInformationStudentIntern extends AppCompatActivity {

    TextView ci_tv_name, ci_tv_profile, ci_tv_stipend, ci_tv_location, ci_tv_cutoff_cpi, ci_tv_test_date, ci_tv_reg_deadline,
            ci_tv_reg_deadline_time, ci_tv_branches_allowed, ci_tv_provision_of_ppo, ci_tv_additional_info;

    String company_name, logged_in_user_batch;
    private static final String DEFAULT = "N/A";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    CompanyInformationInternClass companyInformationInternClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_information_student_intern);

        setTitle("Company Information");

        ci_tv_name = (TextView) findViewById(R.id.ci_tv_name);
        ci_tv_profile = (TextView) findViewById(R.id.ci_tv_profile);
        ci_tv_stipend = (TextView) findViewById(R.id.ci_tv_stipend);
        ci_tv_location = (TextView) findViewById(R.id.ci_tv_location);
        ci_tv_cutoff_cpi = (TextView) findViewById(R.id.ci_tv_cutoff_cpi);
        ci_tv_test_date = (TextView) findViewById(R.id.ci_tv_test_date);
        ci_tv_reg_deadline = (TextView) findViewById(R.id.ci_tv_reg_deadline);
        ci_tv_reg_deadline_time = (TextView) findViewById(R.id.ci_tv_reg_deadline_time);
        ci_tv_branches_allowed = (TextView) findViewById(R.id.ci_tv_branches_allowed);
        ci_tv_provision_of_ppo = (TextView) findViewById(R.id.ci_tv_provision_of_ppo);
        ci_tv_additional_info  = (TextView) findViewById(R.id.ci_tv_additional_info);

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

        companyInformationInternClass = new CompanyInformationInternClass();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                companyInformationInternClass = dataSnapshot.child(logged_in_user_batch).child("companies").child(company_name).getValue(CompanyInformationInternClass.class);

                ci_tv_name.setText(companyInformationInternClass.getName());
                ci_tv_profile.setText(companyInformationInternClass.getProfile());
                ci_tv_stipend.setText(companyInformationInternClass.getStipend());
                ci_tv_location.setText(companyInformationInternClass.getLocation());
                ci_tv_cutoff_cpi.setText(companyInformationInternClass.getCutoff_cpi());
                ci_tv_test_date.setText(companyInformationInternClass.getTest_date());
                ci_tv_reg_deadline.setText(companyInformationInternClass.getReg_deadline());
                ci_tv_reg_deadline_time.setText(companyInformationInternClass.getReg_deadline_time());
                ci_tv_provision_of_ppo.setText(companyInformationInternClass.getPpo_provision());
                ci_tv_additional_info.setText(companyInformationInternClass.getAdditional_info());

                ArrayList<String> allowed_branches = new ArrayList<String>();

                int index = 0;

                //making a string of all allowed branches
                if(companyInformationInternClass.getCse().equals("yes"))
                    allowed_branches.add("CSE");
                if(companyInformationInternClass.getIt().equals("yes"))
                    allowed_branches.add("IT");
                if(companyInformationInternClass.getEce().equals("yes"))
                    allowed_branches.add("ECE");
                if(companyInformationInternClass.getEe().equals("yes"))
                    allowed_branches.add("EE");
                if(companyInformationInternClass.getChem().equals("yes"))
                    allowed_branches.add("CHEMICAL");
                if(companyInformationInternClass.getCivil().equals("yes"))
                    allowed_branches.add("CIVIL");
                if(companyInformationInternClass.getMech().equals("yes"))
                    allowed_branches.add("MECHANICAL");
                if(companyInformationInternClass.getPie().equals("yes"))
                    allowed_branches.add("PIE");
                if(companyInformationInternClass.getMca().equals("yes"))
                    allowed_branches.add("MCA");
                if(companyInformationInternClass.getBio().equals("yes"))
                    allowed_branches.add("BIOTECH");

                String str1 = allowed_branches.toString();
                //removing "[" and "]" from starting and end of string
                str1 = str1.substring(1, str1.length()-1);

                ci_tv_branches_allowed.setText(str1);

                //Log.e("!_@@_Key::>", uInfo_personal.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
