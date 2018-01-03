package com.example.anshul.tpocampus.Admin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anshul.tpocampus.Student.CompanyInformationStudentIntern;
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
 * Created by ANSHUL KISHORE on 21-10-2017.
 */

public class InternCompanyAdapter extends RecyclerView.Adapter<InternCompanyAdapter.MyViewHolder> {

    private List<InternCompanyClass> companyList;
    private Context cContext;
    private DatabaseReference mDatabase, companyDatabase, studentDatabase, studentDatabase2, studentDatabase3, studentDatabase4, studentDatabase5;
    private String name, company_name;

    Date today_date, current_time;
    String today_date1, current_time1;
    Calendar calendar;
    Date comp_reg_deadline_date, comp_reg_deadline_time;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnCreateContextMenuListener{

        public TextView c_name, c_profile, c_stipend, c_location, txtOptionDigit2;

        public ImageView visited_2;

        LongClickListener longClickListener;

        public MyViewHolder(View view) {
            super(view);
            c_name = (TextView) view.findViewById(R.id.company_name);
            c_profile = (TextView) view.findViewById(R.id.company_profile);
            c_stipend = (TextView) view.findViewById(R.id.company_stipend);
            c_location = (TextView) view.findViewById(R.id.company_location);
            visited_2 = (ImageView) view.findViewById(R.id.visited_2);

            mDatabase = FirebaseDatabase.getInstance().getReference();
            companyDatabase = FirebaseDatabase.getInstance().getReference();
            studentDatabase = FirebaseDatabase.getInstance().getReference();
            studentDatabase2 = FirebaseDatabase.getInstance().getReference();
            studentDatabase3 = FirebaseDatabase.getInstance().getReference();
            studentDatabase4 = FirebaseDatabase.getInstance().getReference();
            studentDatabase5 = FirebaseDatabase.getInstance().getReference();

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
            menu.add(0, 0, 0, "Company Information");
            menu.add(0, 1, 0, "Registered Students");
            menu.add(0, 2, 0, "Selected Students");
            menu.add(0, 3, 0, "Edit");
            menu.add(0, 4, 0, "Delete");
        }
    }

    public InternCompanyAdapter(List<InternCompanyClass> companyList, Context cContext) {
        this.companyList = companyList;
        this.cContext = cContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_companies_2, parent, false);
            return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InternCompanyAdapter.MyViewHolder holder, int position) {
        final String cc_name;
        final String cc_profile;
        final String cc_stipend;
        final String cc_location;
        final String cc_txtOptionDigit2;
        final String[] is_completed = new String[1];

        InternCompanyClass internCompanyClass = companyList.get(position);

        cc_name = internCompanyClass.getComp_name();
        cc_profile = internCompanyClass.getComp_profile();
        cc_stipend = internCompanyClass.getComp_stipend();
        cc_location = internCompanyClass.getComp_location();

        mDatabase.child("pre final year").child("companies").child(cc_name).addListenerForSingleValueEvent(new ValueEventListener() {
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

                is_completed[0] = dataSnapshot.child("is_completed").getValue().toString();
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

                Log.e("!_@@_Key::>", is_completed[0]);

                if (comp_reg_deadline_date.compareTo(today_date) > 0) {
                    holder.visited_2.setImageResource(R.drawable.green_dot);
                }
                else if(comp_reg_deadline_date.compareTo(today_date) == 0 && comp_reg_deadline_time.compareTo(current_time) >= 0){
                    holder.visited_2.setImageResource(R.drawable.green_dot);
                }
                else{
                    holder.visited_2.setImageResource(R.drawable.red_dot);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.c_name.setText(cc_name);
        holder.c_profile.setText(cc_profile);
        holder.c_stipend.setText(cc_stipend);
        holder.c_location.setText(cc_location);

        holder.setLongClickListener(new LongClickListener() {
            @Override
            public void onItemLongClick(int pos) {
                name = companyList.get(pos).getComp_name();
                company_name = companyList.get(pos).getComp_name(); //to be used while adding student in database
                Toast.makeText(cContext, name, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public void getItemSelected(MenuItem item){
        if(name != null) {

            if((item.getTitle()).equals("Company Information")){
                Intent intent = new Intent(cContext, CompanyInformationStudentIntern.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_name", company_name);
                bundle.putString("batch", "pre final year");
                intent.putExtras(bundle);
                cContext.startActivity(intent);
            }
            else if((item.getTitle()).equals("Registered Students")){
                Intent intent = new Intent(cContext, RegisteredStudents.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_name", company_name);
                bundle.putString("batch", "pre final year");
                intent.putExtras(bundle);
                cContext.startActivity(intent);
            }
            else if((item.getTitle()).equals("Selected Students")){
                Intent intent = new Intent(cContext, SelectedStudents.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_name", company_name);
                bundle.putString("batch", "pre final year");
                intent.putExtras(bundle);
                cContext.startActivity(intent);
            }
            else if((item.getTitle()).equals("Edit")){
                Intent intent = new Intent(cContext, AddInternCompany.class);
                Bundle bundle = new Bundle();
                bundle.putString("company_name", company_name);
                bundle.putString("is_for_edit", "true");
                bundle.putString("batch", "pre final year");
                intent.putExtras(bundle);
                cContext.startActivity(intent);
            }
            else if((item.getTitle()).equals("Delete")){
                View view = LayoutInflater.from(cContext).inflate(R.layout.activity_delete_company, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(cContext, R.style.AlertDialogStyle);

                builder.setMessage("Delete " + company_name)
                        .setView(view)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //deleting company from "companies" node
                                companyDatabase.child("pre final year").child("companies").child(company_name).setValue(null).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(cContext, "Company deleted!!", Toast.LENGTH_LONG).show();
                                                }
                                                else {
                                                    Toast.makeText(cContext, "some error occurred. Try again!!", Toast.LENGTH_LONG).show();
                                                }
                                            }


                                        });

                                //deleting company from "registered_companies" node of users
                                ValueEventListener vel = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot userDetails: dataSnapshot.child("pre final year").child("users").getChildren())
                                        {
                                            if(userDetails.hasChild("Registerd_companies")) {
                                                if(userDetails.child("Registerd_companies").hasChild(company_name)){

                                                    studentDatabase2.child("pre final year").child("users").child(userDetails.getKey()).child("Registerd_companies").child(company_name).setValue(null).
                                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        //Toast.makeText(cContext, "Company deleted!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                    else {
                                                                        Toast.makeText(cContext, "some error occurred. Try again!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }


                                                            });
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                };

                                studentDatabase.addListenerForSingleValueEvent(vel);

                                //deleting company from "company_selected" node of users
                                ValueEventListener vel2 = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot userDetails: dataSnapshot.child("pre final year").child("users").getChildren())
                                        {
                                            if(userDetails.hasChild("company_selected")) {
                                                if(userDetails.child("company_selected").child("company_name").getValue().equals(company_name)){

                                                    //updating "company_selected" node if user was selected in this company
                                                    studentDatabase4.child("pre final year").child("users").child(userDetails.getKey()).child("company_selected").child("company_name").setValue("None").
                                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        //Toast.makeText(cContext, "Company deleted!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                    else {
                                                                        Toast.makeText(cContext, "some error occurred. Try again!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }


                                                            });

                                                    //unlocking the user if he was selected in this company
                                                    studentDatabase5.child("pre final year").child("users").child(userDetails.getKey()).child("personal").child("locked").setValue("unlocked").
                                                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        //Toast.makeText(cContext, "Company deleted!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                    else {
                                                                        Toast.makeText(cContext, "some error occurred. Try again!!", Toast.LENGTH_LONG).show();
                                                                    }
                                                                }


                                                            });
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                };

                                studentDatabase3.addListenerForSingleValueEvent(vel2);

                                //Toast.makeText(cContext, r + " : " + p, Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("NO", null)
                        .setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            name = null;
        }
    }
}
