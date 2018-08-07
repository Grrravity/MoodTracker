package com.happyapp.grrravity.moodtracker.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.happyapp.grrravity.moodtracker.R;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Calendar calendar = Calendar.getInstance();
        ArrayList<Moods> moods = MoodPreferences.getInstance(this).getMoods();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Long currentDate = calendar.getTimeInMillis();

        if (moods.size() > 0) {
            moods.remove(moods.size() - 1);
        }
        for (int i = 0; i <= (moods.size() - 1); i++) {
            String moodDate = moods.get(i).getDate();
            try {
                Date moodDateSdf = sdf.parse(moodDate);
                long moodDateInMs = moodDateSdf.getTime();
                long timeDiff = currentDate - moodDateInMs;
                long daysDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);
                if (daysDiff < 0 || daysDiff > 7) {
                    moods.remove(i);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        RecyclerView recyclerView = findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new HistoryAdapter(moods));
    }

}
