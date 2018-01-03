package com.example.anshul.tpocampus.Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.UserData.AddUserPersonalData;
import com.example.anshul.tpocampus.Student.UserInfo_company_selected;
import com.example.anshul.tpocampus.Student.UserInfo_personal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageStudent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String DEFAULT = "N/A";

    String stu_reg_no,stu_batch, stu_name, stu_verify = "Not Verified", stu_lock = "unlocked", stu_credits, stu_intern_placement,
            company_selected = "None";

    String m_name, m_regno, m_course, m_branch, m_dob, m_email, m_gender, m_category, m_phy_challenged, m_res_status, m_tpo_credits,
           m_guardian, m_present_addr, m_parmanent_addr, m_mar_status, m_state, m_country, m_intern_placement, m_locked, m_verified,
           m_company_name;

    TextView man_stu_name, man_stu_regno;

    Switch man_stu_ver, man_stu_lock_profile;

    EditText man_stu_tpo_credits, man_stu_intern_placement;

    Button man_stu_save;

    Spinner spinner_company;

    String branch;

    int flag = 0;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase, companyDatabase, companyDatabase2, studentDatabase, studentDatabase2, studentDatabase3,
                              studentDatabase4, studentDatabase5, studentDatabase6, studentDatabase7,
                              studentDatabase8, studentDatabase9;

    private DatabaseReference statsDatabase, statsDatabase2, statsDatabase3;

    UserInfo_personal uInfo_personal = null;
    UserInfo_company_selected userInfo_company_selected = null;
    ChartInfoClass chartInfoClass = null;
    CompanySelectionStats companySelectionStats = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_student);

        setTitle("Manage Student");

        man_stu_name = (TextView) findViewById(R.id.man_stu_name);
        man_stu_regno = (TextView) findViewById(R.id.man_stu_regno);
        man_stu_ver = (Switch) findViewById(R.id.man_stu_ver);
        man_stu_lock_profile = (Switch) findViewById(R.id.man_stu_lock_profile);
        man_stu_tpo_credits = (EditText) findViewById(R.id.man_stu_tpo_credits);
        man_stu_intern_placement = (EditText) findViewById(R.id.man_stu_intern_placement);
        man_stu_save = (Button) findViewById(R.id.man_stu_save);
        spinner_company = (Spinner) findViewById(R.id.spinner_company);

        // Spinner click listener
        spinner_company.setOnItemSelectedListener(this);

        final List<String> company_list = new ArrayList<String>();
        final ArrayAdapter<String> companyAdapter;

        // Creating adapter for spinner
        companyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, company_list);

        // Drop down layout style - list view with radio button
        companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spinner_company.setAdapter(companyAdapter);

        mFirebaseDatabase = FirebaseDatabase.getInstance();

        SharedPreferences man_stu_sharedPreferences = getSharedPreferences("manage_student_profile", Context.MODE_PRIVATE);
        stu_reg_no = man_stu_sharedPreferences.getString("student_regno", DEFAULT);
        //stu_batch = man_stu_sharedPreferences.getString("student_batch", DEFAULT);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        companyDatabase = FirebaseDatabase.getInstance().getReference();
        companyDatabase2 = FirebaseDatabase.getInstance().getReference();
        studentDatabase = FirebaseDatabase.getInstance().getReference();
        studentDatabase2 = FirebaseDatabase.getInstance().getReference();
        studentDatabase3 = FirebaseDatabase.getInstance().getReference();
        studentDatabase4 = FirebaseDatabase.getInstance().getReference();
        studentDatabase5 = FirebaseDatabase.getInstance().getReference();
        studentDatabase6 = FirebaseDatabase.getInstance().getReference();
        studentDatabase7 = FirebaseDatabase.getInstance().getReference();
        studentDatabase8 = FirebaseDatabase.getInstance().getReference();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    for(DataSnapshot ds2 : ds.getChildren()){
                        for(DataSnapshot ds3 : ds2.getChildren()){
                            if((ds3.getKey().toString()).equals(stu_reg_no)){
                                stu_batch = ds.getKey().toString();
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Spinner Drop down elements
        companyDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                company_list.add("None");
                for(DataSnapshot ds: dataSnapshot.child(stu_batch).child("companies").getChildren()){
                        company_list.add(ds.getKey().toString());
                }

                // attaching data adapter to spinner
                spinner_company.setAdapter(companyAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        man_stu_ver.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //The toggle is enabled
                    man_stu_ver.setChecked(true);
                    stu_verify = "Verified";
                }
                else{ //The toggle is disabled
                    man_stu_ver.setChecked(false);
                    stu_verify = "Not Verified";
                }
            }
        });

        man_stu_lock_profile.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){ //The toggle is enabled
                    man_stu_lock_profile.setChecked(true);
                    stu_lock = "locked";
                }
                else{ //The toggle is disabled
                    man_stu_lock_profile.setChecked(false);
                    stu_lock = "unlocked";
                }
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        uInfo_personal = new UserInfo_personal();

        //getting personal info of student
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uInfo_personal = dataSnapshot.child(stu_batch).child("users").child(stu_reg_no).child("personal").getValue(UserInfo_personal.class);
                //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                String str = uInfo_personal.getName().toString();

                m_name = uInfo_personal.getName().toString();
                m_regno = uInfo_personal.getRegno().toString();
                m_course = uInfo_personal.getCourse().toString();
                m_branch = uInfo_personal.getBranch().toString();
                m_dob = uInfo_personal.getDob().toString();
                m_email = uInfo_personal.get_Email().toString();
                m_gender = uInfo_personal.getGender().toString();
                m_category = uInfo_personal.getCategory().toString();
                m_phy_challenged = uInfo_personal.getPhy_challenged().toString();
                m_res_status = uInfo_personal.getRes_status().toString();
                m_guardian = uInfo_personal.getGuardian().toString();
                m_present_addr = uInfo_personal.getPresent_addr().toString();
                m_parmanent_addr = uInfo_personal.getParmanent_addr().toString();
                m_mar_status = uInfo_personal.getMarital_status().toString();
                m_country = uInfo_personal.getCountry().toString();
                m_state = uInfo_personal.getState().toString();
                m_intern_placement = uInfo_personal.getIntern_placement().toString();
                m_locked = uInfo_personal.getLocked().toString();
                m_tpo_credits = uInfo_personal.getTpoCredits().toString();
                m_verified = uInfo_personal.getVerified().toString();

                man_stu_intern_placement.setText(m_intern_placement);
                man_stu_tpo_credits.setText(m_tpo_credits);
                man_stu_name.setText(m_name);
                man_stu_regno.setText(m_regno);

                if(m_locked.equals("locked"))
                    man_stu_lock_profile.setChecked(true);
                else
                    man_stu_lock_profile.setChecked(false);

                if(m_verified.equals("Verified"))
                    man_stu_ver.setChecked(true);
                else
                    man_stu_ver.setChecked(false);


                Log.e("!_@@_Key::>", uInfo_personal.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //name of company student is selected in
        studentDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userInfo_company_selected = dataSnapshot.child(stu_batch).child("users").child(stu_reg_no).child("company_selected").getValue(UserInfo_company_selected.class);
                //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                m_company_name = userInfo_company_selected.getCompany_name().toString();

                int spinnerPosition = companyAdapter.getPosition(m_company_name);
                spinner_company.setSelection(spinnerPosition);

                if(!m_company_name.equalsIgnoreCase("None"))
                    flag = 1;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        man_stu_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stu_credits = man_stu_tpo_credits.getText().toString();
                stu_intern_placement = man_stu_intern_placement.getText().toString();

                AddUserPersonalData addUserPersonalData = new AddUserPersonalData(stu_batch, m_name, m_regno, m_course,
                        m_branch, m_dob, m_email,
                        m_gender, m_category, m_phy_challenged, m_res_status,
                        m_guardian, m_present_addr, m_parmanent_addr,
                        m_mar_status, m_state, m_country, stu_credits, stu_verify, stu_intern_placement, stu_lock);

                //updating personal data of student
                mDatabase.child(stu_batch).child("users").child(stu_reg_no).child("personal").setValue(addUserPersonalData).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    //  Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });

                //updating name of company in which student is selected
                studentDatabase.child(stu_batch).child("users").child(stu_reg_no).child("company_selected").child("company_name").setValue(company_selected).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                }
                            }


                        });

                //updating name of company in which student is selected
                if(!company_selected.equalsIgnoreCase("None")) {
                    studentDatabase3.child(stu_batch).child("users").child(stu_reg_no).child("Registerd_companies").child(company_selected).setValue("1").
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }


                            });

                    //updating the "got" node of "admin"
                    statsDatabase = FirebaseDatabase.getInstance().getReference("admin").child(stu_batch + " stats").child(m_branch);
                    statsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {


                             chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                             String got = chartInfoClass.getGot();
                             int got_int = Integer.parseInt(got);

                             got_int++;

                             studentDatabase5.child("admin").child(stu_batch + " stats").child(m_branch).child("got").setValue(String.valueOf(got_int)).
                                     addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()) {
                                                 //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                             }
                                             else{
                                                 Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                             }
                                         }


                                     });

                             //updating the "got" node of "admin" for the previous company
                             statsDatabase3 = FirebaseDatabase.getInstance().getReference("admin").child(stu_batch + " stats").child(m_branch);
                             statsDatabase3.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(DataSnapshot dataSnapshot) {


                                     chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                                     String got = chartInfoClass.getGot();
                                     int got_int = Integer.parseInt(got);

                                     got_int--;

                                     studentDatabase6.child("admin").child(stu_batch + " stats").child(m_branch).child("got").setValue(String.valueOf(got_int)).
                                             addOnCompleteListener(new OnCompleteListener<Void>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<Void> task) {
                                                     if(task.isSuccessful()) {
                                                         //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                                     }
                                                     else{
                                                         Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                                     }
                                                 }


                                             });

                                 }

                                 @Override
                                 public void onCancelled(DatabaseError databaseError) {

                                 }
                             });

                         }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //updating the no. of students of that branch selected in the company
                    studentDatabase7 = FirebaseDatabase.getInstance().getReference(stu_batch).child("companies").child(company_selected).child("selection_stats");
                    studentDatabase7.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            companySelectionStats = dataSnapshot.getValue(CompanySelectionStats.class);

                            int num = 0;
                            if(m_branch.equalsIgnoreCase("cse")){
                                num = Integer.parseInt(companySelectionStats.getCSE_stats()); branch = "cse_stats";}
                            else if(m_branch.equalsIgnoreCase("it")){
                                num = Integer.parseInt(companySelectionStats.getIT_stats()); branch = "it_stats";}
                            else if(m_branch.equalsIgnoreCase("ece")){
                                num = Integer.parseInt(companySelectionStats.getECE_stats()); branch = "ece_stats";}
                            else if(m_branch.equalsIgnoreCase("ee")){
                                num = Integer.parseInt(companySelectionStats.getEE_stats()); branch = "ee_stats";}
                            else if(m_branch.equalsIgnoreCase("mechanical")){
                                num = Integer.parseInt(companySelectionStats.getMECHANICAL_stats()); branch = "mechanical_stats";}
                            else if(m_branch.equalsIgnoreCase("pie")){
                                num = Integer.parseInt(companySelectionStats.getPIE_stats()); branch = "pie_stats";}
                            else if(m_branch.equalsIgnoreCase("chemical")){
                                num = Integer.parseInt(companySelectionStats.getCHEMICAL_stats()); branch = "chemical_stats";}
                            else if(m_branch.equalsIgnoreCase("civil")){
                                num = Integer.parseInt(companySelectionStats.getCIVIL_stats()); branch = "civil_stats";}
                            else if(m_branch.equalsIgnoreCase("biotech")){
                                num = Integer.parseInt(companySelectionStats.getBIOTECH_stats()); branch = "biotech_stats";}
                            else if(m_branch.equalsIgnoreCase("mca")){
                                num = Integer.parseInt(companySelectionStats.getMCA_stats()); branch = "mca_stats";}

                            num++;

                            studentDatabase5.child(stu_batch).child("companies").child(company_selected).child("selection_stats").child(branch).setValue(String.valueOf(num)).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                            }
                                        }


                                    });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(flag == 1){

                        //if initially student was selected in a company and later made to another one, then
                        //updating value of student(as 0 --> representing that this student is not selected in this company)
                        companyDatabase2.child(stu_batch).child("companies").child(m_company_name).child("Registerd_students").child(stu_reg_no).setValue("0").
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            // Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                        }
                                    }


                                });

                        studentDatabase4.child(stu_batch).child("users").child(stu_reg_no).child("Registerd_companies").child(m_company_name).setValue("0").
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            //Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                        }
                                    }


                                });

                        //updating the no. of students of that branch selected in the company
                        studentDatabase9 = FirebaseDatabase.getInstance().getReference(stu_batch).child("companies").child(m_company_name).child("selection_stats");
                        studentDatabase9.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {


                                companySelectionStats = dataSnapshot.getValue(CompanySelectionStats.class);

                                int num = 0;
                                if(m_branch.equalsIgnoreCase("cse")){
                                    num = Integer.parseInt(companySelectionStats.getCSE_stats()); branch = "cse_stats";}
                                else if(m_branch.equalsIgnoreCase("it")){
                                    num = Integer.parseInt(companySelectionStats.getIT_stats()); branch = "it_stats";}
                                else if(m_branch.equalsIgnoreCase("ece")){
                                    num = Integer.parseInt(companySelectionStats.getECE_stats()); branch = "ece_stats";}
                                else if(m_branch.equalsIgnoreCase("ee")){
                                    num = Integer.parseInt(companySelectionStats.getEE_stats()); branch = "ee_stats";}
                                else if(m_branch.equalsIgnoreCase("mechanical")){
                                    num = Integer.parseInt(companySelectionStats.getMECHANICAL_stats()); branch = "mechanical_stats";}
                                else if(m_branch.equalsIgnoreCase("pie")){
                                    num = Integer.parseInt(companySelectionStats.getPIE_stats()); branch = "pie_stats";}
                                else if(m_branch.equalsIgnoreCase("chemical")){
                                    num = Integer.parseInt(companySelectionStats.getCHEMICAL_stats()); branch = "chemical_stats";}
                                else if(m_branch.equalsIgnoreCase("civil")){
                                    num = Integer.parseInt(companySelectionStats.getCIVIL_stats()); branch = "civil_stats";}
                                else if(m_branch.equalsIgnoreCase("biotech")){
                                    num = Integer.parseInt(companySelectionStats.getBIOTECH_stats()); branch = "biotech_stats";}
                                else if(m_branch.equalsIgnoreCase("mca")){
                                    num = Integer.parseInt(companySelectionStats.getMCA_stats()); branch = "mca_stats";}

                                num--;

                                studentDatabase5.child(stu_batch).child("companies").child(m_company_name).child("selection_stats").child(branch).setValue(String.valueOf(num)).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                                }
                                            }


                                        });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                }


                //updating value of student(as 1 --> representing that this student is selected in this company) for the particular company
                if(!company_selected.equalsIgnoreCase("None")){

                    companyDatabase2.child(stu_batch).child("companies").child(company_selected).child("Registerd_students").child(stu_reg_no).setValue("1").
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        // Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }


                            });

                }

                //if initially student was selected in a company and later made to none, then
                //updating value of student(as 0 --> representing that this student is not selected in this company)
                if(company_selected.equalsIgnoreCase("None") && flag == 1){
                    companyDatabase2.child(stu_batch).child("companies").child(m_company_name).child("Registerd_students").child(stu_reg_no).setValue("0").
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        // Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }


                            });

                    studentDatabase4.child(stu_batch).child("users").child(stu_reg_no).child("Registerd_companies").child(m_company_name).setValue("0").
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        //Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }


                            });

                    //updating the "got" node of "admin"
                    statsDatabase2 = FirebaseDatabase.getInstance().getReference("admin").child(stu_batch + " stats").child(m_branch);
                    statsDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                            String got = chartInfoClass.getGot();
                            int got_int = Integer.parseInt(got);

                            got_int--;

                            studentDatabase6.child("admin").child(stu_batch + " stats").child(m_branch).child("got").setValue(String.valueOf(got_int)).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                            }
                                        }


                                    });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //updating the no. of students of that branch selected in the company
                    studentDatabase8 = FirebaseDatabase.getInstance().getReference(stu_batch).child("companies").child(m_company_name).child("selection_stats");
                    studentDatabase8.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            companySelectionStats = dataSnapshot.getValue(CompanySelectionStats.class);

                            int num = 0;
                            if(m_branch.equalsIgnoreCase("cse")){
                                num = Integer.parseInt(companySelectionStats.getCSE_stats()); branch = "cse_stats";}
                            else if(m_branch.equalsIgnoreCase("it")){
                                num = Integer.parseInt(companySelectionStats.getIT_stats()); branch = "it_stats";}
                            else if(m_branch.equalsIgnoreCase("ece")){
                                num = Integer.parseInt(companySelectionStats.getECE_stats()); branch = "ece_stats";}
                            else if(m_branch.equalsIgnoreCase("ee")){
                                num = Integer.parseInt(companySelectionStats.getEE_stats()); branch = "ee_stats";}
                            else if(m_branch.equalsIgnoreCase("mechanical")){
                                num = Integer.parseInt(companySelectionStats.getMECHANICAL_stats()); branch = "mechanical_stats";}
                            else if(m_branch.equalsIgnoreCase("pie")){
                                num = Integer.parseInt(companySelectionStats.getPIE_stats()); branch = "pie_stats";}
                            else if(m_branch.equalsIgnoreCase("chemical")){
                                num = Integer.parseInt(companySelectionStats.getCHEMICAL_stats()); branch = "chemical_stats";}
                            else if(m_branch.equalsIgnoreCase("civil")){
                                num = Integer.parseInt(companySelectionStats.getCIVIL_stats()); branch = "civil_stats";}
                            else if(m_branch.equalsIgnoreCase("biotech")){
                                num = Integer.parseInt(companySelectionStats.getBIOTECH_stats()); branch = "biotech_stats";}
                            else if(m_branch.equalsIgnoreCase("mca")){
                                num = Integer.parseInt(companySelectionStats.getMCA_stats()); branch = "mca_stats";}

                            num--;

                            studentDatabase5.child(stu_batch).child("companies").child(m_company_name).child("selection_stats").child(branch).setValue(String.valueOf(num)).
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                            }
                                            else{
                                                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                                            }
                                        }


                                    });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        company_selected = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
