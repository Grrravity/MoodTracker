package com.happyapp.grrravity.moodtracker.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.happyapp.grrravity.moodtracker.R;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private ArrayList<Moods> mSavedMoods;

    public HistoryAdapter(ArrayList<Moods> mSavedMoods) {
        this.mSavedMoods = mSavedMoods;
    }

    @Override
    public int getItemCount() {
        return mSavedMoods.size();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    private void display(ArrayList<Moods> mSavedMoods, int position, MyViewHolder holder) {

        String moodDate = mSavedMoods.get(position).getDate();
        Long currentDate = holder.mCalendar.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date lastDate = sdf.parse(moodDate);
            long lastDateInMs = lastDate.getTime();
            long timeDiff = currentDate - lastDateInMs;
            long daysDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);
            String dayShown[] = {"Aujourd'hui", "Hier", "Avant-hier", "Il y a trois jours",
                    "Il y a quatre jours", "Il y a cinq jours", "Il y a six jours",
                    "Il y a une semaine"};
            holder.date.setText(dayShown[(int) daysDiff]);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        // TODO changer nom variable pour comprendre mieux.
        DisplayMetrics metrics = holder.itemView.getContext().getResources().getDisplayMetrics();
        float dp = (90 * (mSavedMoods.get(position).getIndex() + 1));
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5);
        holder.barBackground.setBackgroundColor
                (holder.itemView.getResources().getColor(mSavedMoods.get(position).getColorId()));
        holder.barBackground.setLayoutParams
                (new RelativeLayout.LayoutParams(pixels, (int) ((metrics.density * 80) + 0.5)));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        display(mSavedMoods, holder.getAdapterPosition(), holder);
        if (mSavedMoods.get(position).getComment().equals("")) {
            holder.commentButton.setVisibility(View.GONE);
        } else {
            holder.commentButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), mSavedMoods.get
                            (holder.getAdapterPosition()).getComment(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageButton commentButton;
        private RelativeLayout barBackground;
        private Calendar mCalendar = Calendar.getInstance();


        private MyViewHolder(final View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.history_date);
            commentButton = itemView.findViewById(R.id.history_comment_imageButton);
            barBackground = (RelativeLayout) itemView;
        }


    }
}
