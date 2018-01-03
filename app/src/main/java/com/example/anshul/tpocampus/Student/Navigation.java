package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.anshul.tpocampus.Admin.AdminProfile;
import com.example.anshul.tpocampus.Admin.YearStats;
import com.example.anshul.tpocampus.InterviewExperience.InterviewExpMain;
import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.UserData.UserPhoto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class Navigation extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView t_name, t_regno, t_course, t_branch, t_status, t_credits, t_intern;
    private ImageView status_image;
    private Button edit_profile_button;
    String logged_in_user_regno, logged_in_user_batch;
    private static final String DEFAULT = "N/A";
    int flag = 0;

    TextView int_plc;

    private FirebaseDatabase mFirebaseDatabase;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase, photoDatabase;
    private String userID;

    UserInfo uInfo = null;
    UserPhoto uPhoto = null;

    private ProgressBar progressBar;

    private String URL1 = null; //to display profile pic

    ImageView profile_pic, drawer_profile_pic;
    TextView drawer_name, drawer_regno, drawer_emailid;

    Transformation transformation;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //TextView nav_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("HOME");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);

        drawer_profile_pic = (ImageView) hView.findViewById(R.id.drawer_imageView);
        drawer_name = (TextView) hView.findViewById(R.id.drawer_name);
        drawer_regno = (TextView) hView.findViewById(R.id.drawer_regno);
        drawer_emailid = (TextView) hView.findViewById(R.id.drawer_emailid);

       // displaySelectedScreen(R.id.nav_home);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_home);

        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        progressBar.setVisibility(View.VISIBLE);

        //IT'S DESIGN IS DESCRIBED IN "app_bar_navigation.xml"
        t_name = (TextView) findViewById(R.id.home_t_name);
        t_regno = (TextView) findViewById(R.id.home_t_reg);
        t_course = (TextView) findViewById(R.id.home_t_course);
        t_branch = (TextView) findViewById(R.id.home_t_branch);
        t_status = (TextView) findViewById(R.id.home_t_status);
        t_credits = (TextView) findViewById(R.id.home_t_credits);
        t_intern = (TextView) findViewById(R.id.home_t_intern);
        int_plc = (TextView) findViewById(R.id.int_plc);
        profile_pic = (ImageView) findViewById(R.id.home_profile_pic);
        status_image = (ImageView) findViewById(R.id.status_image);
        edit_profile_button = (Button) findViewById(R.id.edit_profile_button);

        edit_profile_button.setVisibility(View.GONE);

        //profile_pic.setImageResource(R.drawable.profile2);

        //"RoundedImageView" Library used for circular image
        transformation = new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(1)
                .cornerRadiusDp(80)
                .oval(false)
                .build();

        SharedPreferences sharedPreferences1 = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);
        //logged_in_user_branch = sharedPreferences1.getString("logged_in_branch", DEFAULT);
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
                drawer_name.setText(uInfo.getName());
                drawer_regno.setText(uInfo.getRegno());
                drawer_emailid.setText(uInfo.get_Email());

                if(t_status.getText().toString().equalsIgnoreCase("Verified")) {
                    status_image.setImageResource(R.drawable.green_dot);
                }
                else {
                    status_image.setImageResource(R.drawable.red_dot);
                    edit_profile_button.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);

                Log.e("!_@@_Key::>", uInfo.getName());
                Log.e("!_@@_Key::>", uInfo.getRegno());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        photoDatabase = FirebaseDatabase.getInstance().getReference(logged_in_user_batch).child("users").child(logged_in_user_regno);

        photoDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                uPhoto = dataSnapshot.getValue(UserPhoto.class);

                URL1 = uPhoto.getPhoto().toString();

                /*Picasso.with(getApplicationContext())
                        .load(URL1)
                        .transform(transformation)
                        .transform(new CropCircleTransformation())
                        .into(profile_pic);*/

                Picasso.with(getApplicationContext())
                        .load(URL1)
                        .fit()
                        .transform(transformation)
                        .into(profile_pic);

                Picasso.with(getApplicationContext())
                        .load(URL1)
                        .fit()
                        .transform(transformation)
                        .into(drawer_profile_pic);

                // Create an object for subclass of AsyncTask
               // GetXMLTask task = new GetXMLTask();
                // Execute the task
               // task.execute(new String[] { URL1 });

//                Picasso.with(mContext).load(R.drawable.demo)
//                        .transform(transformation).into((ImageView) findViewById(R.id.image));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        edit_profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("is_to_edit_profile", "yes");
                editor.commit();
                flag = 1;

                Intent intent = new Intent(Navigation.this, RegActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen(int id){

        switch (id){
            /*case R.id.nav_home:
                //fragment = new profile_home();
                Intent i1 = new Intent(Navigation.this, p_home.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i1);
                break;*/
            case R.id.nav_profile:
                //fragment = new profile_profile();
                Intent i2 = new Intent(Navigation.this, p_profile.class);
                startActivity(i2);
                break;
            case R.id.nav_curr_openings:
                Intent i3 = new Intent(Navigation.this, p_companies.class);
                startActivity(i3);
                break;
            case R.id.nav_registered_companies:
                Intent i7 = new Intent(Navigation.this, StudentRegisteredCompanies.class);
                startActivity(i7);
                break;
            case R.id.nav_internships:
                Intent i4 = new Intent(Navigation.this, InterviewExpMain.class);
                startActivity(i4);
                break;
            case R.id.nav_stats:
                if(logged_in_user_batch.equalsIgnoreCase("pre final year")){
                    Intent intent = new Intent(Navigation.this, YearStats.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("stats_batch", "pre final year stats");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(Navigation.this, YearStats.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("stats_batch", "final year stats");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
            case R.id.nav_logout:
                SharedPreferences nav_sharedPreferences = getSharedPreferences("current_session", Context.MODE_PRIVATE);
                SharedPreferences.Editor nav_editor = nav_sharedPreferences.edit();

                nav_editor.putString("session", "0");
                nav_editor.commit();

                Intent i6 = new Intent(Navigation.this, LoginActivity.class);
                startActivity(i6);
                finish();
                //fragment = new profile_contacts();
                break;
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        displaySelectedScreen(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    protected void onDestroy() {
        if(flag == 1) {
            editor.putString("is_to_edit_profile", "no");
            editor.commit();
        }
        super.onDestroy();
    }
}
