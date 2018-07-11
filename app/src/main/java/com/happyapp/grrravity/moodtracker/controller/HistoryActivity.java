package com.happyapp.grrravity.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.happyapp.grrravity.moodtracker.R;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private ArrayList<Moods> moods = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<Moods> moods = MoodPreferences.getInstance(this).getMoods();

        mRecyclerView = (RecyclerView) findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new HistoryAdapter(moods));
    }

}
