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

import com.example.anshul.tpocampus.Student.CompanyInformationFulltimeClass;
import com.example.anshul.tpocampus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFulltimeCompany extends AppCompatActivity {

    EditText comp_name, comp_profile, comp_cpi, comp_salary, comp_location, comp_reg_deadline, comp_reg_deadline_time, comp_test_date, comp_additional_info;
    String c_comp_name, c_comp_profile, c_comp_cpi, c_comp_salary, c_comp_location, c_comp_reg_deadline, c_comp_reg_deadline_time, c_comp_test_date,
            c_rb_cse, c_rb_it, c_rb_ece, c_rb_ee, c_rb_mech,c_rb_pie, c_rb_civil, c_rb_chem, c_rb_bio, c_rb_mca, c_comp_additional_info;

    String company_name, logged_in_user_batch;
    CheckBox cb_cse, cb_it, cb_ece, cb_ee, cb_mech,cb_pie, cb_civil, cb_chem, cb_bio, cb_mca;

    Button add_company2;

    private DatabaseReference mDatabase, userDatabase, companyDatabase;

    CompanyInformationFulltimeClass companyInformationFulltimeClass = null;
    CompanySelectionStats companySelectionStats = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fulltime_company);

        setTitle("Add Fulltime Company");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userDatabase = FirebaseDatabase.getInstance().getReference();
        companyDatabase = FirebaseDatabase.getInstance().getReference();

        comp_name = (EditText) findViewById(R.id.add_comp_name2);
        comp_profile = (EditText) findViewById(R.id.add_comp_profile2);
        comp_cpi = (EditText) findViewById(R.id.add_min_cpi2);
        comp_salary = (EditText) findViewById(R.id.add_comp_salary);
        comp_location = (EditText) findViewById(R.id.add_comp_location2);
        comp_reg_deadline = (EditText) findViewById(R.id.add_comp_deadline2);
        comp_reg_deadline_time = (EditText) findViewById(R.id.add_comp_time_deadline2);
        comp_test_date = (EditText) findViewById(R.id.add_test_date2);
        comp_additional_info = (EditText) findViewById(R.id.add_extra_info2);

        cb_cse = (CheckBox) findViewById(R.id.cb_cse);
        cb_it = (CheckBox) findViewById(R.id.cb_it);
        cb_ece = (CheckBox) findViewById(R.id.cb_ece);
        cb_ee = (CheckBox) findViewById(R.id.cb_ee);
        cb_mech = (CheckBox) findViewById(R.id.cb_mech);
        cb_pie = (CheckBox) findViewById(R.id.cb_pie);
        cb_civil = (CheckBox) findViewById(R.id.cb_civil);
        cb_chem = (CheckBox) findViewById(R.id.cb_chem);
        cb_bio = (CheckBox) findViewById(R.id.cb_bio);
        cb_mca = (CheckBox) findViewById(R.id.cb_mca);

        add_company2 = (Button) findViewById(R.id.add_company2);

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        //to check if admin has asked for this class for editing or not
        if(bundleExtras != null) {
            boolean isForEditing = bundleExtras.containsKey("is_for_edit");

            if (isForEditing) {
                setTitle("Edit Fulltime Company");

                company_name = bundleExtras.getString("company_name");
                logged_in_user_batch = bundleExtras.getString("batch");

                companyInformationFulltimeClass = new CompanyInformationFulltimeClass();

                companyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        companyInformationFulltimeClass = dataSnapshot.child(logged_in_user_batch).child("companies").child(company_name).getValue(CompanyInformationFulltimeClass.class);

                        comp_name.setText(companyInformationFulltimeClass.getName());
                        comp_profile.setText(companyInformationFulltimeClass.getProfile());
                        comp_salary.setText(companyInformationFulltimeClass.getSalary());
                        comp_location.setText(companyInformationFulltimeClass.getLocation());
                        comp_cpi.setText(companyInformationFulltimeClass.getCutoff_cpi());
                        comp_test_date.setText(companyInformationFulltimeClass.getTest_date());
                        comp_reg_deadline.setText(companyInformationFulltimeClass.getReg_deadline());
                        comp_reg_deadline_time.setText(companyInformationFulltimeClass.getReg_deadline_time());
                        comp_additional_info.setText(companyInformationFulltimeClass.getAdditional_info());

                        ArrayList<String> allowed_branches = new ArrayList<String>();

                        int index = 0;

                        //making a string of all allowed branches
                        if (companyInformationFulltimeClass.getCse().equals("yes"))
                            cb_cse.setChecked(true);
                        if (companyInformationFulltimeClass.getIt().equals("yes"))
                            cb_it.setChecked(true);
                        if (companyInformationFulltimeClass.getEce().equals("yes"))
                            cb_ece.setChecked(true);
                        if (companyInformationFulltimeClass.getEe().equals("yes"))
                            cb_ee.setChecked(true);
                        if (companyInformationFulltimeClass.getChem().equals("yes"))
                            cb_chem.setChecked(true);
                        if (companyInformationFulltimeClass.getCivil().equals("yes"))
                            cb_civil.setChecked(true);
                        if (companyInformationFulltimeClass.getMech().equals("yes"))
                            cb_mech.setChecked(true);
                        if (companyInformationFulltimeClass.getPie().equals("yes"))
                            cb_pie.setChecked(true);
                        if (companyInformationFulltimeClass.getMca().equals("yes"))
                            cb_mca.setChecked(true);
                        if (companyInformationFulltimeClass.getBio().equals("yes"))
                            cb_bio.setChecked(true);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }

        add_company2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_company();

                AddFulltimeCompanyInfo addFulltimeCompanyInfo = new AddFulltimeCompanyInfo(c_comp_name, c_comp_profile, c_comp_cpi,
                        c_comp_salary, c_comp_location, c_comp_reg_deadline, c_comp_reg_deadline_time, c_comp_test_date,
                        c_rb_cse, c_rb_it, c_rb_ece, c_rb_ee,
                        c_rb_mech, c_rb_pie, c_rb_civil, c_rb_chem, c_rb_bio, c_rb_mca, "0", c_comp_additional_info);

                companySelectionStats = new CompanySelectionStats("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");

                mDatabase.child("final year").child("companies").child(c_comp_name).setValue(addFulltimeCompanyInfo).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    //Toast.makeText(getApplicationContext(), "Company added", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });

                mDatabase.child("final year").child("companies").child(c_comp_name).child("selection_stats").setValue(companySelectionStats).
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

                /*userDatabase.child("final year").child("users").addListenerForSingleValueEvent(new ValueEventListener() {
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

                //startActivity(new Intent(AddFulltimeCompany.this, Companies_admin.class));
                finish();

            }
        });

    }

    public void add_company(){

        c_comp_name = comp_name.getText().toString();
        c_comp_profile = comp_profile.getText().toString();
        c_comp_cpi = comp_cpi.getText().toString();
        c_comp_salary = comp_salary.getText().toString();
        c_comp_location = comp_location.getText().toString();
        c_comp_reg_deadline = comp_reg_deadline.getText().toString();
        c_comp_reg_deadline_time = comp_reg_deadline_time.getText().toString();
        c_comp_test_date = comp_test_date.getText().toString();
        c_comp_additional_info = comp_additional_info.getText().toString();

        if(cb_cse.isChecked())
            c_rb_cse = "yes";
        else
            c_rb_cse = "no";

        if(cb_it.isChecked())
            c_rb_it = "yes";
        else
            c_rb_it = "no";

        if(cb_ece.isChecked())
            c_rb_ece = "yes";
        else
            c_rb_ece = "no";

        if(cb_ee.isChecked())
            c_rb_ee = "yes";
        else
            c_rb_ee = "no";

        if(cb_mech.isChecked())
            c_rb_mech = "yes";
        else
            c_rb_mech = "no";

        if(cb_pie.isChecked())
            c_rb_pie = "yes";
        else
            c_rb_pie = "no";

        if(cb_civil.isChecked())
            c_rb_civil = "yes";
        else
            c_rb_civil = "no";

        if(cb_chem.isChecked())
            c_rb_chem = "yes";
        else
            c_rb_chem = "no";

        if(cb_bio.isChecked())
            c_rb_bio = "yes";
        else
            c_rb_bio = "no";

        if(cb_mca.isChecked())
            c_rb_mca = "yes";
        else
            c_rb_mca = "no";

    }
}
