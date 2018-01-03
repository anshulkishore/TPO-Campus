package com.example.anshul.tpocampus.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshul.tpocampus.Admin.LongClickListener;
import com.example.anshul.tpocampus.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ANSHUL KISHORE on 11-12-2017.
 */

public class Student_company_adapter_final_year extends RecyclerView.Adapter<Student_company_adapter_final_year.MyViewHolder> {

    private List<StudentCompanyClassFinalYear> companyList;
    private Context cContext_1;
    private DatabaseReference mDatabase, pwdDatabase, companyDatabase, studentDatabase, nDatabase, companyDatabase2, personalDatabase;
    String name, company_name;
    private static final String DEFAULT = "N/A";
    String logged_in_user_batch, logged_in_user_regno, login_user_branch, login_user_branch2, cutoff_cpi_str, cutoff_cpi2_str, is_locked;
    Double cutoff_cpi, cutoff_cpi2;
    int flag, flag2 = 0, flag3 = 0, flag4 = 0;
    Date today_date, current_time;
    String today_date1, current_time1;
    Calendar calendar;
    Date comp_reg_deadline_date, comp_reg_deadline_time;

    UserInfo_personal uInfo_personal = null;
    UserInfo_password uInfo_password = null;
    UserInfo_academic uInfo_academic = null;
    CompanyInformationFulltimeClass companyInformationFulltimeClass = null;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener{

        public TextView c_name_3, c_profile_3, c_package_3, c_location_3, txtOptionDigit_3;

        public ImageView visited_4;

        LongClickListener longClickListener;

        public MyViewHolder(View view) {
            super(view);
            c_name_3 = (TextView) view.findViewById(R.id.company_name_1);
            c_profile_3 = (TextView) view.findViewById(R.id.company_profile_1);
            c_package_3 = (TextView) view.findViewById(R.id.company_stipend_1);
            c_location_3 = (TextView) view.findViewById(R.id.company_location_1);
            visited_4 = (ImageView) view.findViewById(R.id.visited_1);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            pwdDatabase = FirebaseDatabase.getInstance().getReference();
            companyDatabase = FirebaseDatabase.getInstance().getReference();
            studentDatabase = FirebaseDatabase.getInstance().getReference();
            nDatabase = FirebaseDatabase.getInstance().getReference();
            companyDatabase2 = FirebaseDatabase.getInstance().getReference();
            personalDatabase= FirebaseDatabase.getInstance().getReference();

            view.setOnLongClickListener(this);
            view.setOnCreateContextMenuListener(this);


            //txtOptionDigit2 = (TextView) view.findViewById(R.id.txtOptionDigit2);
        }

        public void setLongClickListener(LongClickListener lc){
            this.longClickListener = lc;
        }

        @Override
        public boolean onLongClick(View v) {
            this.longClickListener.onItemLongClick(getLayoutPosition());
            return false;
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select Action");

            //to check if this adapter is opened from "StudentRegisteredCompanies" class
            SharedPreferences sharedPreferences = cContext_1.getSharedPreferences("view_registered_companies", Context.MODE_PRIVATE);
            String is_to_view_registered_companies = sharedPreferences.getString("is_to_view_registered_companies", DEFAULT);

            if(is_to_view_registered_companies.equalsIgnoreCase("yes")){
                menu.add(0, 0, 0, "Company Information");
            }
            else{
                menu.add(0, 0, 0, "Register");
                menu.add(0, 1, 0, "Company Information");
            }
        }
    }

    public Student_company_adapter_final_year(List<StudentCompanyClassFinalYear> companyList, Context cContext_1) {
        this.companyList = companyList;
        this.cContext_1 = cContext_1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_companies_1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Student_company_adapter_final_year.MyViewHolder holder, int position) {
        final String cc_name_3;
        final String cc_profile_3;
        final String cc_package_3;
        final String cc_location_3;
        final String cc_txtOptionDigit_3;
        final String[] is_completed_3 = new String[1];

        StudentCompanyClassFinalYear studentCompanyClassFinalYear = companyList.get(position);

        cc_name_3 = studentCompanyClassFinalYear.getComp_name();
        cc_profile_3 = studentCompanyClassFinalYear.getComp_profile();
        cc_package_3 = studentCompanyClassFinalYear.getComp_package();
        cc_location_3 = studentCompanyClassFinalYear.getComp_location();

        mDatabase.child("final year").child("companies").child(cc_name_3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //setting green or red dot according to current date and time
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

                is_completed_3[0] = dataSnapshot.child("is_completed").getValue().toString();
                String reg_deadline_date = dataSnapshot.child("reg_deadline").getValue().toString();
                String reg_deadline_time = dataSnapshot.child("reg_deadline_time").getValue().toString();

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

                Log.e("!_@@_Key::>", is_completed_3[0]);
                if (comp_reg_deadline_date.compareTo(today_date) > 0) {
                    holder.visited_4.setImageResource(R.drawable.green_dot);
                }
                else if(comp_reg_deadline_date.compareTo(today_date) == 0 && comp_reg_deadline_time.compareTo(current_time) >= 0){
                    holder.visited_4.setImageResource(R.drawable.green_dot);
                }
                else{
                    holder.visited_4.setImageResource(R.drawable.red_dot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.c_name_3.setText(cc_name_3);
        holder.c_profile_3.setText(cc_profile_3);
        holder.c_package_3.setText(cc_package_3);
        holder.c_location_3.setText(cc_location_3);

        holder.setLongClickListener(new LongClickListener() {
            @Override
            public void onItemLongClick(int pos) {
                flag = 0;
                flag3 = 0;
                flag4 = 1;
                name = companyList.get(pos).getComp_name();
                company_name = companyList.get(pos).getComp_name(); //to be used while adding student in database
                Toast.makeText(cContext_1, name, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {return companyList.size();}

    public void getItemSelected(MenuItem item){
        if(name != null){
            //Toast.makeText(cContext, name + " : " + item.getTitle(), Toast.LENGTH_LONG).show();

            if((item.getTitle()).equals("Register")) {
                SharedPreferences sharedPreferences1 = cContext_1.getSharedPreferences("current_session", Context.MODE_PRIVATE);
                logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);
                logged_in_user_regno = sharedPreferences1.getString("logged_in", DEFAULT);

                //getting the logged in user branch
                nDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        uInfo_personal = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo_personal.class);
                        login_user_branch = uInfo_personal.getBranch();

                        uInfo_academic = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("academic").getValue(UserInfo_academic.class);
                        cutoff_cpi_str = uInfo_academic.getCurrCpi();

                        cutoff_cpi = Double.parseDouble(cutoff_cpi_str);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                companyDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        companyInformationFulltimeClass = dataSnapshot.child(logged_in_user_batch).child("companies").child(company_name).getValue(CompanyInformationFulltimeClass.class);

                        if (login_user_branch.equalsIgnoreCase("cse"))
                            login_user_branch2 = companyInformationFulltimeClass.getCse().toString();

                        else if (login_user_branch.equalsIgnoreCase("it"))
                            login_user_branch2 = companyInformationFulltimeClass.getIt().toString();

                        else if (login_user_branch.equalsIgnoreCase("ece"))
                            login_user_branch2 = companyInformationFulltimeClass.getEce().toString();

                        else if (login_user_branch.equalsIgnoreCase("ee"))
                            login_user_branch2 = companyInformationFulltimeClass.getEe().toString();

                        else if (login_user_branch.equalsIgnoreCase("biotech"))
                            login_user_branch2 = companyInformationFulltimeClass.getBio().toString();

                        else if (login_user_branch.equalsIgnoreCase("chemical"))
                            login_user_branch2 = companyInformationFulltimeClass.getChem().toString();

                        else if (login_user_branch.equalsIgnoreCase("civil"))
                            login_user_branch2 = companyInformationFulltimeClass.getCivil().toString();

                        else if (login_user_branch.equalsIgnoreCase("mca"))
                            login_user_branch2 = companyInformationFulltimeClass.getMca().toString();

                        else if (login_user_branch.equalsIgnoreCase("mechanical"))
                            login_user_branch2 = companyInformationFulltimeClass.getMech().toString();

                        else if (login_user_branch.equalsIgnoreCase("pie"))
                            login_user_branch2 = companyInformationFulltimeClass.getPie().toString();

                        cutoff_cpi2_str = companyInformationFulltimeClass.getCutoff_cpi().toString();
                        cutoff_cpi2 = Double.parseDouble(cutoff_cpi2_str);

                        personalDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                uInfo_personal = dataSnapshot.child(logged_in_user_batch).child("users").child(logged_in_user_regno).child("personal").getValue(UserInfo_personal.class);
                                is_locked = uInfo_personal.getLocked();

                                if(is_locked.equalsIgnoreCase("locked")){
                                    Toast.makeText(cContext_1, "Your profile is locked. You can't register for any company now!", Toast.LENGTH_LONG).show();
                                }
                                else if (login_user_branch2.equalsIgnoreCase("yes") && cutoff_cpi >= cutoff_cpi2) {
                                    View view = LayoutInflater.from(cContext_1).inflate(R.layout.activity_student_company_register, null);

                                    final EditText registration_reg_no = (EditText) view.findViewById(R.id.registration_reg_no);
                                    final EditText registration_pwd = (EditText) view.findViewById(R.id.registration_pwd);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(cContext_1, R.style.AlertDialogStyle);

                                    builder.setMessage("REGISTER HERE!")
                                            .setView(view)
                                            .setPositiveButton("Register", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    final String login_user_regno = registration_reg_no.getText().toString();
                                                    final String login_pass = registration_pwd.getText().toString();

                                                    //checking if password matches with actual password
                                                    pwdDatabase.child("final year").child("users").addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            //uInfo_personal = dataSnapshot.getValue(UserInfo_personal.class);

                                                            if(flag3 == 0 && flag4 == 1) {
                                                                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                                                                    String retrieved_regno = ds.getKey();

                                                                    Log.e("!_@@_Key::>", retrieved_regno);

                                                                    if (retrieved_regno.equals(login_user_regno)) {

                                                                        flag2 = 1; //used if entered reg. no. is not present

                                                                        //flag[0] = 1;

                                                                        uInfo_password = ds.getValue(UserInfo_password.class);

                                                                        String user_pass = uInfo_password.getPassword();

                                                                        if (login_pass.equals(user_pass)) {  //if password matches

                                                                            //adding reg. no. of student to "Registered Students" list of that company
                                                                            companyDatabase.child("final year").child("companies").child(company_name).child("Registerd_students").child(login_user_regno).setValue("0").
                                                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                // Toast.makeText(cContext_1, "Registered!!", Toast.LENGTH_LONG).show();
                                                                                            } else {
                                                                                                Toast.makeText(cContext_1, "some error occured. Try again!!", Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        }


                                                                                    });

                                                                            //adding company in which the stuent registered
                                                                            studentDatabase.child("final year").child("users").child(login_user_regno).child("Registerd_companies").child(company_name).setValue("0").
                                                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if (task.isSuccessful())
                                                                                                Toast.makeText(cContext_1, "Registered!!", Toast.LENGTH_LONG).show();
                                                                                            else {
                                                                                                Toast.makeText(cContext_1, "some error occured. Try again!!", Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        }


                                                                                    });
                                                                        } else {
                                                                            Toast.makeText(cContext_1, "Invalid Registration number or Password", Toast.LENGTH_LONG).show();
                                                                        }

                                                                        break;
                                                                    }

                                                                    //Log.e("!_@@_Key::>", ds.child("personal").getValue(AddUserPersonalData.class).getName());
                                                                }
                                                                flag3++;
                                                            }

                                                            if (flag2 == 0) {
                                                                Toast.makeText(cContext_1, "No such user exists", Toast.LENGTH_LONG).show();
                                                            }

                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {

                                                        }
                                                    });

                                                    //Toast.makeText(cContext, r + " : " + p, Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();
                                                }
                                            })
                                            .setNegativeButton("Cancel", null)
                                            .setCancelable(true);

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                } else {
                                    Toast.makeText(cContext_1, "You are not allowed to register for this company!", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            else  if((item.getTitle()).equals("Company Information")){
                Intent intent = new Intent(cContext_1, CompanyInformationStudentFulltime.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_name", company_name);
                intent.putExtras(bundle);
                cContext_1.startActivity(intent);
            }

            //if(flag > 1)
                name = null;
        }
    }
}
