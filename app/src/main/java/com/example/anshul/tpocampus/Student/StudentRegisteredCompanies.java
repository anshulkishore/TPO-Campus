package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentRegisteredCompanies extends AppCompatActivity {

    String company_name, logged_in_user_batch, logged_in_user_regno;
    ArrayList<String> registered_companies;

    private RecyclerView cRecyclerView;
    private List<StudentComapnyClass> cList_intern_company = new ArrayList<>();
    private List<StudentCompanyClassFinalYear> cList_fulltime_company = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String DEFAULT = "N/A";

    private DatabaseReference mDatabase, companyDatabase;

    private Student_company_adapter student_company_adapter;
    private Student_company_adapter_final_year student_company_adapter_final_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Registered Companies");

        //to remove "Register" option for company from adapter AND for the adapter class to check if the adapter
        //is opened from "StudentRegisteredCompanies" class.
        sharedPreferences = getSharedPreferences("view_registered_companies", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("is_to_view_registered_companies", "yes");
        editor.commit();

        SharedPreferences sharedPreferences1 = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);
        logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);

        if(logged_in_user_batch.equals("pre final year")) {
            setContentView(R.layout.student_companies);
            cRecyclerView = (RecyclerView) findViewById(R.id.student_company_list);
        }
        else {
            setContentView(R.layout.student_companies_final_year);
            cRecyclerView = (RecyclerView) findViewById(R.id.student_company_list_final_year);
        }

        student_company_adapter = new Student_company_adapter(cList_intern_company, this);
        student_company_adapter_final_year = new Student_company_adapter_final_year(cList_fulltime_company, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        cRecyclerView.setLayoutManager(mLayoutManager);
        cRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cRecyclerView.setHasFixedSize(true);

        if(logged_in_user_batch.equals("pre final year")) {
            cRecyclerView.setAdapter(student_company_adapter);
        }
        else {
            cRecyclerView.setAdapter(student_company_adapter_final_year);
        }

        registered_companies = new ArrayList<String>();

        //adding registered companies by the user
        mDatabase = FirebaseDatabase.getInstance().getReference(logged_in_user_batch).child("users").child(logged_in_user_regno).child("Registerd_companies");

        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails: dataSnapshot.getChildren())
                    registered_companies.add(userDetails.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.addListenerForSingleValueEvent(vel);

        companyDatabase = FirebaseDatabase.getInstance().getReference(logged_in_user_batch).child("companies");

        ValueEventListener vel2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int index = 0;

                if(registered_companies.size() == 0){
                    Toast.makeText(getApplicationContext(), "NOT REGISTERED IN ANY COMPANY YET!!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        if (index < registered_companies.size() && userDetails.getKey().equals(registered_companies.get(index))) {
                            index++;
                            if(logged_in_user_batch.equalsIgnoreCase("pre final year")) {
                                StudentComapnyClass obj = new StudentComapnyClass();
                                obj.setComp_name(userDetails.child("name").getValue().toString());
                                obj.setComp_profile(userDetails.child("profile").getValue().toString());
                                obj.setComp_stipend(userDetails.child("stipend").getValue().toString());
                                obj.setComp_location(userDetails.child("location").getValue().toString());
                                cList_intern_company.add(obj);
                            }
                            else{
                                StudentCompanyClassFinalYear obj = new StudentCompanyClassFinalYear();
                                obj.setComp_name(userDetails.child("name").getValue().toString());
                                obj.setComp_profile(userDetails.child("profile").getValue().toString());
                                obj.setComp_package(userDetails.child("salary").getValue().toString());
                                obj.setComp_location(userDetails.child("location").getValue().toString());
                                cList_fulltime_company.add(obj);
                            }
                        }
                    }

                    if(logged_in_user_batch.equalsIgnoreCase("pre final year"))
                        student_company_adapter.notifyDataSetChanged();
                    else
                        student_company_adapter_final_year.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        companyDatabase.addListenerForSingleValueEvent(vel2);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(logged_in_user_batch.equals("pre final year")) {
            student_company_adapter.getItemSelected(item);
        }
        else {
            student_company_adapter_final_year.getItemSelected(item);
        }
        student_company_adapter.getItemSelected(item);
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        editor.putString("is_to_view_registered_companies", "no");
        editor.commit();
        super.onDestroy();
    }
}
