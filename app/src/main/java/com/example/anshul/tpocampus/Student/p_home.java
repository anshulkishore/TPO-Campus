package com.example.anshul.tpocampus.Student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class p_home extends AppCompatActivity {

    private TextView t_name, t_regno, t_course, t_branch, t_status, t_credits, t_intern;
    String logged_in_user_regno, logged_in_user_batch, logged_in_user_branch;
    private static final String DEFAULT = "N/A";

    TextView int_plc;

    private FirebaseDatabase mFirebaseDatabase;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private String userID;

    UserInfo uInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_home);

        /*setTitle("Home");

        t_name = (TextView) findViewById(R.id.home_t_name);
        t_regno = (TextView) findViewById(R.id.home_t_reg);
        t_course = (TextView) findViewById(R.id.home_t_course);
        t_branch = (TextView) findViewById(R.id.home_t_branch);
        t_status = (TextView) findViewById(R.id.home_t_status);
        t_credits = (TextView) findViewById(R.id.home_t_credits);
        t_intern = (TextView) findViewById(R.id.home_t_intern);
        int_plc = (TextView) findViewById(R.id.int_plc);

        SharedPreferences sharedPreferences1 = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);
        logged_in_user_branch = sharedPreferences1.getString("logged_in_branch", DEFAULT);
        logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);

        if(logged_in_user_batch.equals("final year"))
            int_plc.setText("Placement:");
        else
            int_plc.setText("Internship:");

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal");

        uInfo = new UserInfo();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                uInfo = dataSnapshot.getValue(UserInfo.class);

                t_name.setText(uInfo.getName());
                t_regno.setText(uInfo.getRegno());
                t_course.setText(uInfo.getCourse());
                t_branch.setText(uInfo.getBranch());
                t_status.setText(uInfo.getVerified());
                t_credits.setText(uInfo.getTpocredits());
                t_intern.setText(uInfo.getIntern_placement());

                Log.e("!_@@_Key::>", uInfo.getName());
                Log.e("!_@@_Key::>", uInfo.getRegno());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
