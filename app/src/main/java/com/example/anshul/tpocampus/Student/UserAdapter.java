package com.example.anshul.tpocampus.Student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.anshul.tpocampus.Admin.ManageStudent;
import com.example.anshul.tpocampus.R;

import java.util.List;

/**
 * Created by ANSHUL KISHORE on 15-10-2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<ModelClass> userList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView uu_regno, uu_name, uu_course, uu_branch, txtOptionDigit;

        public MyViewHolder(View view) {
            super(view);
            uu_regno = (TextView) view.findViewById(R.id.card_regno);
            uu_name = (TextView) view.findViewById(R.id.card_name);
            uu_course = (TextView) view.findViewById(R.id.card_course);
            uu_branch = (TextView) view.findViewById(R.id.card_branch);
            txtOptionDigit = (TextView) view.findViewById(R.id.txtOptionDigit);
        }
    }

    public UserAdapter(List<ModelClass> userList, Context mContext) {
        this.userList = userList;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        final String s_regno, s_name, s_branch, s_course, s_batch;

        ModelClass modelClass = userList.get(position);
        s_regno = modelClass.getRegno();
        s_name = modelClass.getName();
        s_branch = modelClass.getBranch();
        s_course = modelClass.getCourse();
        s_batch = modelClass.getBatch();

        holder.uu_regno.setText(s_regno);
        holder.uu_name.setText(s_name);
        holder.uu_branch.setText(s_branch);
        holder.uu_course.setText(s_course);

        holder.txtOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(mContext, holder.txtOptionDigit);
                popupMenu.inflate(R.menu.option_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.manage_student:

                                //Toast.makeText(mContext, "Manage student", Toast.LENGTH_LONG).show();
                                SharedPreferences man_stu_sharedPreferences = mContext.getSharedPreferences("manage_student_profile", Context.MODE_PRIVATE);
                                SharedPreferences.Editor man_stu_editor = man_stu_sharedPreferences.edit();

                                man_stu_editor.putString("student_regno", s_regno);
                                //man_stu_editor.putString("student_batch", s_batch);
                                man_stu_editor.commit();

                                Intent man_intent = new Intent(mContext, ManageStudent.class);
                                mContext.startActivity(man_intent);
                                break;

                            case R.id.profile_student:

                                SharedPreferences sel_stu_sharedPreferences = mContext.getSharedPreferences("selected_student_profile", Context.MODE_PRIVATE);
                                SharedPreferences.Editor sel_stu_editor = sel_stu_sharedPreferences.edit();

                                sel_stu_editor.putString("student_regno", s_regno);
                                sel_stu_editor.commit();

                                Intent intent = new Intent(mContext, p_profile.class);
                                mContext.startActivity(intent);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
