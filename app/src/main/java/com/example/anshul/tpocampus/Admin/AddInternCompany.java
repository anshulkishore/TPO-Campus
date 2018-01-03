package com.example.anshul.tpocampus.Admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anshul.tpocampus.Student.CompanyInformationInternClass;
import com.example.anshul.tpocampus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddInternCompany extends AppCompatActivity {

    EditText comp_name, comp_profile, comp_cpi, comp_stipend, comp_location, comp_reg_deadline, comp_reg_deadline_time, comp_test_date, comp_ppo, comp_additional_info;
    String c_comp_name, c_comp_profile, c_comp_cpi, c_comp_stipend, c_comp_location, c_comp_reg_deadline, c_comp_reg_deadline_time, c_comp_test_date, c_comp_ppo,
           c_rb_cse, c_rb_it, c_rb_ece, c_rb_ee, c_rb_mech,c_rb_pie, c_rb_civil, c_rb_chem, c_rb_bio, c_rb_mca, c_comp_additional_info;

    String company_name, logged_in_user_batch;

    CheckBox rb_cse, rb_it, rb_ece, rb_ee, rb_mech,rb_pie, rb_civil, rb_chem, rb_bio, rb_mca;

    Button add_company;

    private DatabaseReference mDatabase, userDatabase, companyDatabase;

    CompanyInformationInternClass companyInformationInternClass = null;
    CompanySelectionStats companySelectionStats = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_intern_company);

        setTitle("Add Internship Company");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabase = FirebaseDatabase.getInstance().getReference();
        companyDatabase = FirebaseDatabase.getInstance().getReference();

        comp_name = (EditText) findViewById(R.id.add_comp_name);
        comp_profile = (EditText) findViewById(R.id.add_comp_profile);
        comp_cpi = (EditText) findViewById(R.id.add_min_cpi);
        comp_stipend = (EditText) findViewById(R.id.add_comp_stipend);
        comp_location = (EditText) findViewById(R.id.add_comp_location);
        comp_reg_deadline = (EditText) findViewById(R.id.add_comp_deadline);
        comp_reg_deadline_time = (EditText) findViewById(R.id.add_comp_time_deadline);
        comp_test_date = (EditText) findViewById(R.id.add_test_date);
        comp_ppo = (EditText) findViewById(R.id.add_ppo_provision);
        comp_additional_info = (EditText) findViewById(R.id.add_extra_info);

        rb_cse = (CheckBox) findViewById(R.id.rb_cse);
        rb_it = (CheckBox) findViewById(R.id.rb_it);
        rb_ece = (CheckBox) findViewById(R.id.rb_ece);
        rb_ee = (CheckBox) findViewById(R.id.rb_ee);
        rb_mech = (CheckBox) findViewById(R.id.rb_mech);
        rb_pie = (CheckBox) findViewById(R.id.rb_pie);
        rb_civil = (CheckBox) findViewById(R.id.rb_civil);
        rb_chem = (CheckBox) findViewById(R.id.rb_chem);
        rb_bio = (CheckBox) findViewById(R.id.rb_bio);
        rb_mca = (CheckBox) findViewById(R.id.rb_mca);

        add_company = (Button) findViewById(R.id.add_company);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        //to check if admin has asked for this class for editing or not
        if(bundleExtras != null) {
            boolean isForEditing = bundleExtras.containsKey("is_for_edit");

            if (isForEditing) {
                setTitle("Edit Internship Company");

                company_name = bundleExtras.getString("company_name");
                logged_in_user_batch = bundleExtras.getString("batch");

                companyInformationInternClass = new CompanyInformationInternClass();

                companyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        companyInformationInternClass = dataSnapshot.child(logged_in_user_batch).child("companies").child(company_name).getValue(CompanyInformationInternClass.class);

                        comp_name.setText(companyInformationInternClass.getName());
                        comp_profile.setText(companyInformationInternClass.getProfile());
                        comp_stipend.setText(companyInformationInternClass.getStipend());
                        comp_location.setText(companyInformationInternClass.getLocation());
                        comp_cpi.setText(companyInformationInternClass.getCutoff_cpi());
                        comp_test_date.setText(companyInformationInternClass.getTest_date());
                        comp_reg_deadline.setText(companyInformationInternClass.getReg_deadline());
                        comp_reg_deadline_time.setText(companyInformationInternClass.getReg_deadline_time());
                        comp_additional_info.setText(companyInformationInternClass.getAdditional_info());
                        comp_ppo.setText(companyInformationInternClass.getPpo_provision());

                        ArrayList<String> allowed_branches = new ArrayList<String>();

                        int index = 0;

                        //making a string of all allowed branches
                        if (companyInformationInternClass.getCse().equals("yes"))
                            rb_cse.setChecked(true);
                        if (companyInformationInternClass.getIt().equals("yes"))
                            rb_it.setChecked(true);
                        if (companyInformationInternClass.getEce().equals("yes"))
                            rb_ece.setChecked(true);
                        if (companyInformationInternClass.getEe().equals("yes"))
                            rb_ee.setChecked(true);
                        if (companyInformationInternClass.getChem().equals("yes"))
                            rb_chem.setChecked(true);
                        if (companyInformationInternClass.getCivil().equals("yes"))
                            rb_civil.setChecked(true);
                        if (companyInformationInternClass.getMech().equals("yes"))
                            rb_mech.setChecked(true);
                        if (companyInformationInternClass.getPie().equals("yes"))
                            rb_pie.setChecked(true);
                        if (companyInformationInternClass.getMca().equals("yes"))
                            rb_mca.setChecked(true);
                        if (companyInformationInternClass.getBio().equals("yes"))
                            rb_bio.setChecked(true);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }

        add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_company();

                AddInternCompanyInfo addInternCompanyInfo = new AddInternCompanyInfo(c_comp_name, c_comp_profile, c_comp_cpi, c_comp_stipend,
                                                                   c_comp_location, c_comp_reg_deadline, c_comp_reg_deadline_time, c_comp_test_date,
                                                                   c_comp_ppo, c_rb_cse, c_rb_it, c_rb_ece, c_rb_ee,
                                                                   c_rb_mech, c_rb_pie, c_rb_civil, c_rb_chem, c_rb_bio, c_rb_mca, "0", c_comp_additional_info);

                companySelectionStats = new CompanySelectionStats("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

                mDatabase.child("pre final year").child("companies").child(c_comp_name).setValue(addInternCompanyInfo).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(getApplicationContext(), "Company added", Toast.LENGTH_LONG).show();
                                else{
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });

                mDatabase.child("pre final year").child("companies").child(c_comp_name).child("selection_stats").setValue(companySelectionStats).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                    Toast.makeText(getApplicationContext(), "Company added", Toast.LENGTH_LONG).show();
                                else{
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });

                /*userDatabase.child("pre final year").child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            ds.child("companies").child(c_comp_name).getRef().setValue("0");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/

                //startActivity(new Intent(AddInternCompany.this, Companies_admin.class));
                finish();

            }
        });


    }

    public void add_company(){

        c_comp_name = comp_name.getText().toString();
        c_comp_profile = comp_profile.getText().toString();
        c_comp_cpi = comp_cpi.getText().toString();
        c_comp_stipend = comp_stipend.getText().toString();
        c_comp_location = comp_location.getText().toString();
        c_comp_reg_deadline = comp_reg_deadline.getText().toString();
        c_comp_reg_deadline_time = comp_reg_deadline_time.getText().toString();
        c_comp_test_date = comp_test_date.getText().toString();
        c_comp_ppo = comp_ppo.getText().toString();
        c_comp_additional_info = comp_additional_info.getText().toString();

        if(rb_cse.isChecked())
            c_rb_cse = "yes";
        else
            c_rb_cse = "no";

        if(rb_it.isChecked())
            c_rb_it = "yes";
        else
            c_rb_it = "no";

        if(rb_ece.isChecked())
            c_rb_ece = "yes";
        else
            c_rb_ece = "no";

        if(rb_ee.isChecked())
            c_rb_ee = "yes";
        else
            c_rb_ee = "no";

        if(rb_mech.isChecked())
            c_rb_mech = "yes";
        else
            c_rb_mech = "no";

        if(rb_pie.isChecked())
            c_rb_pie = "yes";
        else
            c_rb_pie = "no";

        if(rb_civil.isChecked())
            c_rb_civil = "yes";
        else
            c_rb_civil = "no";

        if(rb_chem.isChecked())
            c_rb_chem = "yes";
        else
            c_rb_chem = "no";

        if(rb_bio.isChecked())
            c_rb_bio = "yes";
        else
            c_rb_bio = "no";

        if(rb_mca.isChecked())
            c_rb_mca = "yes";
        else
            c_rb_mca = "no";

    }
}
