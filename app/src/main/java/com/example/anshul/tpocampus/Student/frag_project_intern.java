package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;
import com.example.anshul.tpocampus.UserData.AddUserProjectData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ANSHUL KISHORE on 06-10-2017.
 */

public class frag_project_intern extends Fragment {

    Button pro_save;
    EditText pro_e_projects, pro_e_organisation, pro_e_description;

    public static final String DEFAULT = "N/A";
    private static final String TAG = frag_project_intern.class.getSimpleName();

    String is_for_editing, logged_in_user_regno, logged_in_user_batch;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase, pwd_mDatabase;

    UserInfo_projects uInfo_projects = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_project_intern, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Enter Project Details");

        pwd_mDatabase = FirebaseDatabase.getInstance().getReference();

        pro_save = (Button)getActivity().findViewById(R.id.pro_bsave);
        pro_e_description = (EditText) getActivity().findViewById(R.id.pro_edescription);
        pro_e_projects = (EditText) getActivity().findViewById(R.id.pro_eprojects);
        pro_e_organisation = (EditText) getActivity().findViewById(R.id.pro_eorganisation);

        sharedPreferences = getActivity().getSharedPreferences("edit_profile", Context.MODE_PRIVATE);
        is_for_editing = sharedPreferences.getString("is_to_edit_profile", "no");

        if(is_for_editing.equalsIgnoreCase("yes")){

            pro_save.setText("Update");

            SharedPreferences f_per_sharedPreferences1 = getActivity().getSharedPreferences("current_session", Context.MODE_PRIVATE);
            logged_in_user_regno = f_per_sharedPreferences1.getString("logged_in", DEFAULT);
            logged_in_user_batch = f_per_sharedPreferences1.getString("logged_in_batch", DEFAULT);

            mDatabase = FirebaseDatabase.getInstance().getReference();

            uInfo_projects = new UserInfo_projects();

            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    uInfo_projects = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("projects_intern").getValue(UserInfo_projects.class);
                    //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                    pro_e_projects.setText(uInfo_projects.getProjects());
                    pro_e_organisation.setText(uInfo_projects.getOrganisation());
                    pro_e_description.setText(uInfo_projects.getDescription());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        pro_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(is_for_editing.equalsIgnoreCase("yes")){
                    AddUserProjectData addUserProjectData = new AddUserProjectData(pro_e_projects.getText().toString(),
                            pro_e_description.getText().toString(),
                            pro_e_organisation.getText().toString());

                    pwd_mDatabase.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("projects_intern").setValue(addUserProjectData).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        //Toast.makeText(getContext(), "User added", Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }


                            });
                }
                else
                    save_projects(v);
            }
        });
    }

    public void save_projects(View view) {

        SharedPreferences pro_sharedPreferences = this.getActivity().getSharedPreferences("My_data_projects", Context.MODE_PRIVATE);
        SharedPreferences.Editor pro_editor = pro_sharedPreferences.edit();

        pro_editor.putString("projects", pro_e_projects.getText().toString());
        pro_editor.putString("description", pro_e_description.getText().toString());
        pro_editor.putString("organisation", pro_e_organisation.getText().toString());

        pro_editor.commit();
        Toast.makeText(this.getActivity(), "Data was saved successfully!", Toast.LENGTH_LONG).show();

    }
}
