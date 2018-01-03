package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anshul.tpocampus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ANSHUL KISHORE on 08-10-2017.
 */

public class profile_home extends Fragment{

    private TextView t_name, t_regno, t_course, t_branch, t_status, t_credits, t_intern;
    String logged_in_user_regno, logged_in_user_batch;
    private static final String DEFAULT = "N/A";

    private FirebaseDatabase mFirebaseDatabase;
    //private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    private String userID;

    UserInfo uInfo = null;

    public profile_home(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_home, container, false);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");

        t_name = (TextView) getActivity().findViewById(R.id.tname);
        t_regno = (TextView) getActivity().findViewById(R.id.treg);
        t_course = (TextView) getActivity().findViewById(R.id.tcourse);
        t_branch = (TextView) getActivity().findViewById(R.id.tbranch);
        t_status = (TextView) getActivity().findViewById(R.id.tstatus);
        t_credits = (TextView) getActivity().findViewById(R.id.tcredits);
        t_intern = (TextView) getActivity().findViewById(R.id.tintern);

        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);

        if(logged_in_user_regno.charAt(3) == '5')
            logged_in_user_batch = "pre final year";
        else if(logged_in_user_regno.charAt(3) == '4')
            logged_in_user_batch = "final year";

        //mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = mFirebaseDatabase.getReference("pre final year").child("users").child(logged_in_user_regno).child("personal");
        //FirebaseUser user = mAuth.getCurrentUser();
        //userID = user.getUid();

        /*mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Toast.makeText(getActivity().getApplicationContext(), "Successfully signed in!!", Toast.LENGTH_LONG).show();
                }
                else {
                    // user auth state is changed - user is null
                    // launch login com.example.anshul.tpocampus.activity
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                }
            }
        };*/

        uInfo = new UserInfo();

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                uInfo = dataSnapshot.getValue(UserInfo.class);
                Log.e("!_@@_Key::>", uInfo.getName());
                Log.e("!_@@_Key::>", uInfo.getRegno());

                //showData(dataSnapshot);

//                Map<String, String> td = (HashMap<String, String>) dataSnapshot.getValue();
//
//                t_name.setText(td.get("name"));
//                t_regno.setText(td.get("regno"));
//                t_course.setText(td.get("course"));
//                t_branch.setText(td.get("branch"));
//                t_status.setText(td.get("verified"));
//                t_credits.setText(td.get("tpo_credits"));
//
//                Log.e("!_@@_Key::>", td.get("name"));
//                Log.e("!_@@_Key::>", td.get("regno"));
//                Log.e("!_@@_Key::>", td.get("course"));
//                Log.e("!_@@_Key::>", td.get("branch"));
//                Log.e("!_@@_Key::>", td.get("verified"));
//                Log.e("!_@@_Key::>", td.get("tpo_credits"));

               /* for (Map.Entry<String, String> entry : td.entrySet()) {
                    Log.e("!_@@_Key::>", entry.getKey() + "---" + entry.getValue());
                    if((entry.getKey()).equals("name"))
                        t_name.setText(entry.get());
                    else if((entry.getKey()).equals("regno"))
                        t_regno.setText(entry.getValue());
                    else if((entry.getKey()).equals("course"))
                        t_course.setText(entry.getValue());
                    else if((entry.getKey()).equals("branch"))
                        t_branch.setText(entry.getValue());
                    else if((entry.getKey()).equals("verified"))
                        t_status.setText(entry.getValue());
                    else if((entry.getKey()).equals("tpo_credits"))
                        t_credits.setText(entry.getValue());

                    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                } */
                //for (DataSnapshot ds : dataSnapshot.getChildren()) {

                  //  Log.e("!_@@_Key::>", ds.getKey());



//                    uInfo.setName(ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getName()); //set the name
//                    uInfo.setRegno(ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getRegno());
//                    uInfo.setCourse(ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getCourse());
//                    uInfo.setBranch(ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getBranch());
//                    uInfo.setVerified(ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getVerified());
//                    uInfo.setTpocredits(ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getTpocredits());
/*                    uInfo.setName(ds.getValue(AddUserPersonalData.class).getName()); //set the name
                    uInfo.setRegno(ds.getValue(AddUserPersonalData.class).getRegno());
                    uInfo.setCourse(ds.getValue(AddUserPersonalData.class).getCourse());
                    uInfo.setBranch(ds.getValue(AddUserPersonalData.class).getBranch());
                    uInfo.setVerified(ds.getValue(AddUserPersonalData.class).getVerified());
                    uInfo.setTpoCredits(ds.getValue(AddUserPersonalData.class).getTpoCredits());
*/
                    //Log.e("!_@@_Key::::>", ds.child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo.class).getName());
//                    t_name.setText(uInfo.getName());
//                    t_regno.setText(uInfo.getRegno());
//                    t_course.setText(uInfo.getCourse());
//                    t_branch.setText(uInfo.getBranch());
//                    t_status.setText(uInfo.getVerified());
//                    t_credits.setText(uInfo.getTpocredits());

//                    Log.e("!_@@_Key::::>", uInfo.getRegno());
//                    if((uInfo.getRegno()).equals(logged_in_user_regno)){
//                        break;
//                    }

                //}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //private void showData(DataSnapshot dataSnapshot) {
      //  for(DataSnapshot ds : dataSnapshot.getChildren()){

        //    Log.e("!_@@_Key::>", ds.getKey());
            /*AddUserPersonalData uInfo = new AddUserPersonalData();

            uInfo.setName(ds.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal")
                          .getValue(AddUserPersonalData.class).getName()); //set the name
            uInfo.setRegno(ds.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal")
                    .getValue(AddUserPersonalData.class).getRegno());
            uInfo.setCourse(ds.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal")
                    .getValue(AddUserPersonalData.class).getCourse());
            uInfo.setBranch(ds.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal")
                    .getValue(AddUserPersonalData.class).getBranch());
            uInfo.setVerified(ds.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal")
                    .getValue(AddUserPersonalData.class).getVerified());
            uInfo.setTpoCredits(ds.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal")
                    .getValue(AddUserPersonalData.class).getTpoCredits());


            t_name.setText(uInfo.getName());
            t_regno.setText(uInfo.getRegno());
            t_course.setText(uInfo.getCourse());
            t_branch.setText(uInfo.getBranch());
            t_status.setText(uInfo.getVerified());
            t_credits.setText(uInfo.getTpoCredits());
            */
        //}
    //}
}
