package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class p_companies extends AppCompatActivity {

    private RecyclerView cRecyclerView;
    private List<StudentComapnyClass> cList = new ArrayList<>();
    private List<StudentCompanyClassFinalYear> cList2 = new ArrayList<>();

    DatabaseReference cRef;

    private Student_company_adapter student_company_adapter;
    private Student_company_adapter_final_year student_company_adapter_final_year;
    private static final String DEFAULT = "N/A";
    String logged_in_user_batch;
    Date today_date, current_time;
    String today_date1, current_time1;
    Calendar calendar;
    Date comp_reg_deadline_date, comp_reg_deadline_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Current Openings");

        //to decide whether to display fulltime or internship companies
        SharedPreferences sharedPreferences1 = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);

        if(logged_in_user_batch.equals("pre final year")) {
            setContentView(R.layout.student_companies);
            cRecyclerView = (RecyclerView) findViewById(R.id.student_company_list);
        }
        else {
            setContentView(R.layout.student_companies_final_year);
            cRecyclerView = (RecyclerView) findViewById(R.id.student_company_list_final_year);
        }

        calendar = Calendar.getInstance();
        SimpleDateFormat df_date = new SimpleDateFormat("dd-MM-yyyy");
        today_date1 = df_date.format(calendar.getTime());
        try {
            today_date= new SimpleDateFormat("dd-MM-yyyy").parse(today_date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat df_time = new SimpleDateFormat("HH:mm:ss");
        current_time1 = df_time.format(calendar.getTime());
        try {
            current_time = new SimpleDateFormat("HH:mm:ss").parse(current_time1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        //Recycler View
        student_company_adapter = new Student_company_adapter(cList, this);
        student_company_adapter_final_year = new Student_company_adapter_final_year(cList2, this);

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


        cRef = FirebaseDatabase.getInstance().getReference(logged_in_user_batch).child("companies");

        if(logged_in_user_batch.equals("pre final year")) {
            ValueEventListener vel = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot userDetails: dataSnapshot.getChildren())
                    {
                        String reg_deadline_date = userDetails.child("reg_deadline").getValue().toString();
                        String reg_deadline_time = userDetails.child("reg_deadline_time").getValue().toString();

                        try {
                            comp_reg_deadline_date = new SimpleDateFormat("dd-MM-yyyy").parse(reg_deadline_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            comp_reg_deadline_time = new SimpleDateFormat("HH:mm:ss").parse(reg_deadline_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //displaying only that companies which are open for registration
                        if (comp_reg_deadline_date.compareTo(today_date) > 0 ||
                                (comp_reg_deadline_date.compareTo(today_date) == 0
                                        && comp_reg_deadline_time.compareTo(current_time) >= 0) ) {

                            StudentComapnyClass obj = new StudentComapnyClass();
                            obj.setComp_name(userDetails.child("name").getValue().toString());
                            obj.setComp_profile(userDetails.child("profile").getValue().toString());
                            obj.setComp_stipend(userDetails.child("stipend").getValue().toString());
                            obj.setComp_location(userDetails.child("location").getValue().toString());
                            cList.add(obj);
                        }

                    }

                    student_company_adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            cRef.addListenerForSingleValueEvent(vel);
        }
        else {
            ValueEventListener vel = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot userDetails: dataSnapshot.getChildren())
                    {
                        String reg_deadline_date = userDetails.child("reg_deadline").getValue().toString();
                        String reg_deadline_time = userDetails.child("reg_deadline_time").getValue().toString();

                        try {
                            comp_reg_deadline_date = new SimpleDateFormat("dd-MM-yyyy").parse(reg_deadline_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        try {
                            comp_reg_deadline_time = new SimpleDateFormat("HH:mm:ss").parse(reg_deadline_time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if(userDetails.child("is_completed").getValue().toString().equals("0")
                                && comp_reg_deadline_date.compareTo(today_date) >= 0
                                && comp_reg_deadline_time.compareTo(current_time) >= 0) {

                            StudentCompanyClassFinalYear obj = new StudentCompanyClassFinalYear();
                            obj.setComp_name(userDetails.child("name").getValue().toString());
                            obj.setComp_profile(userDetails.child("profile").getValue().toString());
                            obj.setComp_package(userDetails.child("salary").getValue().toString());
                            obj.setComp_location(userDetails.child("location").getValue().toString());
                            cList2.add(obj);
                        }
                    }

                    student_company_adapter_final_year.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            cRef.addListenerForSingleValueEvent(vel);
        }
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
}
