package com.example.anshul.tpocampus.Admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.anshul.tpocampus.Student.ModelClass;
import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.Student.UserAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredStudents extends AppCompatActivity {

    String company_name, logged_in_user_batch;
    ArrayList<String> registered_students;

    private RecyclerView cRecyclerView;
    private List<ModelClass> cList = new ArrayList<>();

    private UserAdapter userAdapter;

    private static final String DEFAULT = "N/A";

    private DatabaseReference mDatabase, studentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Registered Students");

        Intent intentExtras = getIntent();
        Bundle bundleExtras = intentExtras.getExtras();

        company_name = bundleExtras.getString("company_name");
        logged_in_user_batch = bundleExtras.getString("batch");

        if(logged_in_user_batch.equals("pre final year")) {
            setContentView(R.layout.batch2_2018_2019);
            cRecyclerView = (RecyclerView) findViewById(R.id.student_list);
        }
        else {
            setContentView(R.layout.batch_2017_2018);
            cRecyclerView = (RecyclerView) findViewById(R.id.student_list_2);
        }

        userAdapter = new UserAdapter(cList, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        cRecyclerView.setLayoutManager(mLayoutManager);
        cRecyclerView.setItemAnimator(new DefaultItemAnimator());
        cRecyclerView.setHasFixedSize(true);

        cRecyclerView.setAdapter(userAdapter);

        registered_students = new ArrayList<String>();

        mDatabase = FirebaseDatabase.getInstance().getReference(logged_in_user_batch).child("companies").child(company_name).child("Registerd_students");

        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot userDetails: dataSnapshot.getChildren())
                        registered_students.add(userDetails.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mDatabase.addListenerForSingleValueEvent(vel);

        studentDatabase = FirebaseDatabase.getInstance().getReference(logged_in_user_batch).child("users");


        ValueEventListener vel2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int index = 0;

                if(registered_students.size() == 0){
                    Toast.makeText(getApplicationContext(), "NO STUDENT REGISTERED YET!!", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    for (DataSnapshot userDetails : dataSnapshot.getChildren()) {
                        if (index < registered_students.size() && userDetails.getKey().equals(registered_students.get(index))) {
                            index++;
                            ModelClass obj = new ModelClass();
                            obj.setName(userDetails.child("personal").child("name").getValue().toString());
                            obj.setRegno(userDetails.child("personal").child("regno").getValue().toString());
                            obj.setCourse(userDetails.child("personal").child("course").getValue().toString());
                            obj.setBranch(userDetails.child("personal").child("branch").getValue().toString());
                            cList.add(obj);
                        }
                    }

                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        studentDatabase.addListenerForSingleValueEvent(vel2);

    }
}
