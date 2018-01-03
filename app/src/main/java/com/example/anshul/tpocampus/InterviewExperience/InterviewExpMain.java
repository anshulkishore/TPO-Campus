package com.example.anshul.tpocampus.InterviewExperience;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.anshul.tpocampus.R;

import java.util.ArrayList;
import java.util.List;

public class InterviewExpMain extends AppCompatActivity {

    private List<ItemMain> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private InterviewExpMainAdapter mAdapter;

    private static final String DEFAULT = "N/A";
    private String logged_in_user_batch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        setTitle("Interview Portal");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_IntExpMain);

        SharedPreferences sharedPreferences1 = getSharedPreferences("current_session", Context.MODE_PRIVATE);
        logged_in_user_batch = sharedPreferences1.getString("logged_in_batch", DEFAULT);

        mAdapter = new InterviewExpMainAdapter(itemList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        prepareData();
    }

    private void prepareData() {

        ItemMain item;

        if(logged_in_user_batch.equalsIgnoreCase("pre final year")) {
            item = new ItemMain("Internship Interview Experiences");
            itemList.add(item);
        }
        else{
            item = new ItemMain("Fulltime Interview Experiences");
            itemList.add(item);
        }

        if(logged_in_user_batch.equalsIgnoreCase("pre final year")) {
            item = new ItemMain("Add Internship Interview Experience");
            itemList.add(item);
        }
        else {
            item = new ItemMain("Add Fulltime Interview Experience");
            itemList.add(item);
        }

        mAdapter.notifyDataSetChanged();

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}
