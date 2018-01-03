package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText login_Regno, login_Password;
    //private FirebaseAuth login_auth;
    private ProgressBar login_progressBar;
    private Button login_btnSignup, login_btnLogin, login_btnReset;
    private static final String DEFAULT = "N/A";
    String login_user_regno;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;

    UserInfo_personal uInfo_personal = null;
    UserInfo_password uInfo_password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Login");
        final SharedPreferences login_sharedPreferences = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        final SharedPreferences.Editor login_editor = login_sharedPreferences.edit();

        SharedPreferences sel_stu_sharedPreferences = getSharedPreferences("selected_student_profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor sel_stu_editor = sel_stu_sharedPreferences.edit();

        sel_stu_editor.putString("flag", "0");
        sel_stu_editor.commit();

        String login_session = login_sharedPreferences.getString("session", "0");

        if(login_session.equals("1")){
            startActivity(new Intent(LoginActivity.this, Navigation.class));
            finish();
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference();

        // set the view now
        setContentView(R.layout.activity_login);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        login_Regno = (EditText) findViewById(R.id.login_regno);
        login_Password = (EditText) findViewById(R.id.login_password);
        login_progressBar = (ProgressBar) findViewById(R.id.progressBar);
        login_btnSignup = (Button) findViewById(R.id.btn_signup);
        login_btnLogin = (Button) findViewById(R.id.btn_login);
        //login_btnReset = (Button) findViewById(R.id.btn_reset_password);

        //Get Firebase auth instance
        //login_auth = FirebaseAuth.getInstance();

        login_btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
            }
        });

        /*login_btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });*/

        login_btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_user_regno = login_Regno.getText().toString();
                //String login_email = login_sharedPreferences.getString(login_user_regno, DEFAULT);
                //String email = inputEmail.getText().toString();
                final String login_pass = login_Password.getText().toString();

                if (login_user_regno.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (login_pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                login_progressBar.setVisibility(View.VISIBLE);

                final int[] flag = {0};

                mDatabase.child("pre final year").child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                        for(DataSnapshot ds : dataSnapshot.getChildren()){

                                String retrieved_regno = ds.getKey();

                                Log.e("!_@@_Key::>", retrieved_regno);

                                if(retrieved_regno.equals(login_user_regno)){

                                    flag[0] = 1;

                                    uInfo_password = ds.getValue(UserInfo_password.class);

                                    String user_pass = uInfo_password.getPassword();

                                    if(login_pass.equals(user_pass)){
                                        login_editor.putString("logged_in", login_user_regno);
                                        login_editor.putString("session", "1");
                                        //login_editor.putString("logged_in_branch", login_user_branch);
                                        login_editor.putString("logged_in_batch", "pre final year");
                                        login_editor.commit();

                                        startActivity(new Intent(LoginActivity.this, Navigation.class));
                                        finish();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Invalid Registration number or Password", Toast.LENGTH_LONG).show();
                                        login_progressBar.setVisibility(View.GONE);
                                    }



                            }

                            //Log.e("!_@@_Key::>", ds.child("personal").getValue(AddUserPersonalData.class).getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                if(flag[0] == 0) {
                    mDatabase.child("final year").child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //showData(dataSnapshot);
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                    String retrieved_regno = ds.getKey();

                                    Log.e("!_@@_Key::>", retrieved_regno);

                                    if (retrieved_regno.equals(login_user_regno)) {

                                        flag[0] = 1;

                                        uInfo_password = ds.getValue(UserInfo_password.class);

                                        String user_pass = uInfo_password.getPassword();

                                        if (login_pass.equals(user_pass)) {
                                            login_editor.putString("logged_in", login_user_regno);
                                            login_editor.putString("session", "1");
                                            //login_editor.putString("logged_in_branch", login_user_branch);
                                            login_editor.putString("logged_in_batch", "final year");
                                            login_editor.commit();

                                            startActivity(new Intent(LoginActivity.this, Navigation.class));
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Invalid Registration number or Password", Toast.LENGTH_LONG).show();
                                            login_progressBar.setVisibility(View.GONE);
                                        }
                                    }

                            }
                            if(flag[0] == 0){
                                Toast.makeText(getApplicationContext(), "User doesn't exist!!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            ///Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                //Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
                //login_progressBar.setVisibility(View.GONE);

                //authenticate user
                /*login_auth.signInWithEmailAndPassword(login_email, login_pass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                login_progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (login_pass.length() < 6) {
                                        login_Password.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    login_editor .putString("logged_in", login_user_regno);
                                    login_editor .commit();

                                    Intent intent = new Intent(LoginActivity.this, Navigation.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });*/
            }
        });
    }
}