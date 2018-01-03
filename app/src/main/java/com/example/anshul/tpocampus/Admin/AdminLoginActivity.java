package com.example.anshul.tpocampus.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    EditText admin_password;
    Button admin_login_button;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        setTitle("Login");

        final SharedPreferences admin_login_sharedPreferences = getSharedPreferences("current_admin_session", Context.MODE_PRIVATE);
        final SharedPreferences.Editor admin_login_editor = admin_login_sharedPreferences.edit();

        SharedPreferences sel_stu_sharedPreferences = getSharedPreferences("selected_student_profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor sel_stu_editor = sel_stu_sharedPreferences.edit();

        sel_stu_editor.putString("flag", "1");
        sel_stu_editor.commit();

        String admin_login_session = admin_login_sharedPreferences.getString("session", "0");

        if(admin_login_session.equals("1")){
            startActivity(new Intent(AdminLoginActivity.this, AdminProfile.class));
            finish();
        }

        admin_password = (EditText) findViewById(R.id.admin_login_password);
        admin_login_button = (Button) findViewById(R.id.admin_login_button);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();


        admin_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String admin_pass = admin_password.getText().toString();

                if (admin_pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String p = dataSnapshot.child("admin").child("password").getValue().toString();
                        if(p.equals(admin_pass)){
                            admin_login_editor.putString("session", "1");
                            admin_login_editor.commit();

                            startActivity(new Intent(AdminLoginActivity.this, AdminProfile.class));
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
