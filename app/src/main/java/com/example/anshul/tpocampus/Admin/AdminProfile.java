package com.example.anshul.tpocampus.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Transformation;

import static java.lang.Thread.sleep;

public class AdminProfile extends AppCompatActivity {

    String cseGotf, itGotf, eceGotf, eeGotf, mechGotf, pieGotf, chemGotf, civilGotf, bioGotf, mcaGotf;
    String cseTotalf, itTotalf, eceTotalf, eeTotalf, mechTotalf, pieTotalf, chemTotalf, civilTotalf, bioTotalf, mcaTotalf;

    String stats_batchf;

    ImageView admin_iv_students, admin_iv_companies, admin_iv_fy_stats, admin_iv_pfy_stats, admin_iv_logout;
    Button admin_button_students, admin_button_companies, admin_button_fy_stats, admin_button_pfy_stats, admin_button_logout;;

    private DatabaseReference cseDatabasef, itDatabasef, eceDatabasef, eeDatabasef, mechDatabasef, pieDatabasef, chemDatabasef,
            civilDatabasef, bioDatabasef, mcaDatabasef;

    ChartInfoClass chartInfoClass = null;

    Transformation transformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_profile2);

        setTitle("Admin Profile");

        admin_iv_students = (ImageView) findViewById(R.id.admin_iv_students);
        admin_iv_companies = (ImageView) findViewById(R.id.admin_iv_companies);
        admin_iv_fy_stats = (ImageView) findViewById(R.id.admin_iv_fy_stats);
        admin_iv_pfy_stats = (ImageView) findViewById(R.id.admin_iv_pfy_stats);
        admin_iv_logout = (ImageView) findViewById(R.id.admin_iv_logout);

        admin_button_students = (Button) findViewById(R.id.admin_button_students);
        admin_button_companies = (Button) findViewById(R.id.admin_button_companies);
        admin_button_fy_stats = (Button) findViewById(R.id.admin_button_fy_stats);
        admin_button_pfy_stats = (Button) findViewById(R.id.admin_button_pfy_stats);
        admin_button_logout = (Button) findViewById(R.id.admin_button_logout);

        //for final year

        stats_batchf = "final year stats";

        cseDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("CSE");
        cseDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                cseGotf = chartInfoClass.getGot();
                cseTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        itDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("IT");
        itDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                itGotf = chartInfoClass.getGot();
                itTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eceDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("ECE");
        eceDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                eceGotf = chartInfoClass.getGot();
                eceTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        eeDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("EE");
        eeDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                eeGotf = chartInfoClass.getGot();
                eeTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mechDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("mechanical");
        mechDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                mechGotf = chartInfoClass.getGot();
                mechTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        pieDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("PIE");
        pieDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                pieGotf = chartInfoClass.getGot();
                pieTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        chemDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("chemical");
        chemDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                chemGotf = chartInfoClass.getGot();
                chemTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        civilDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("civil");
        civilDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                civilGotf = chartInfoClass.getGot();
                civilTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        bioDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("biotech");
        bioDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                bioGotf = chartInfoClass.getGot();
                bioTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mcaDatabasef = FirebaseDatabase.getInstance().getReference("admin").child(stats_batchf).child("MCA");
        mcaDatabasef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                chartInfoClass = dataSnapshot.getValue(ChartInfoClass.class);

                mcaGotf = chartInfoClass.getGot();
                mcaTotalf = chartInfoClass.getTotal();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        admin_iv_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfile.this, Students.class));
            }
        });

        admin_button_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfile.this, Students.class));
            }
        });

        admin_iv_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfile.this, Companies_admin.class));
            }
        });

        admin_button_companies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminProfile.this, Companies_admin.class));
            }
        });

        admin_iv_pfy_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(AdminProfile.this, YearStats.class));
                Intent intent = new Intent(AdminProfile.this, YearStats.class);
                Bundle bundle = new Bundle();
                bundle.putString("stats_batch", "pre final year stats");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        admin_button_pfy_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(AdminProfile.this, YearStats.class));
                Intent intent = new Intent(AdminProfile.this, YearStats.class);
                Bundle bundle = new Bundle();
                bundle.putString("stats_batch", "pre final year stats");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        admin_iv_fy_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(AdminProfile.this, BarChartActivity.class));
                Intent intent = new Intent(AdminProfile.this, YearStats.class);
                Bundle bundle = new Bundle();
                bundle.putString("stats_batch", "final year stats");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        admin_button_fy_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //startActivity(new Intent(AdminProfile.this, BarChartActivity.class));
                Intent intent = new Intent(AdminProfile.this, YearStats.class);
                Bundle bundle = new Bundle();
                bundle.putString("stats_batch", "final year stats");
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        admin_button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences admin_login_sharedPreferences = getSharedPreferences("current_admin_session", Context.MODE_PRIVATE);
                SharedPreferences.Editor admin_login_editor = admin_login_sharedPreferences.edit();

                admin_login_editor.putString("session", "0");
                admin_login_editor.commit();

                Intent i = new Intent(AdminProfile.this, AdminLoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        admin_iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences admin_login_sharedPreferences = getSharedPreferences("current_admin_session", Context.MODE_PRIVATE);
                SharedPreferences.Editor admin_login_editor = admin_login_sharedPreferences.edit();

                admin_login_editor.putString("session", "0");
                admin_login_editor.commit();

                Intent i = new Intent(AdminProfile.this, AdminLoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
