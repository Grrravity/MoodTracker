package com.happyapp.grrravity.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.happyapp.grrravity.moodtracker.R;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ArrayList<Moods> moods = MoodPreferences.getInstance(this).getMoods();
        if (moods.size() > 0) {
            moods.remove(moods.size() - 1);
        }

        RecyclerView recyclerView = findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HistoryAdapter(moods));
    }

}
