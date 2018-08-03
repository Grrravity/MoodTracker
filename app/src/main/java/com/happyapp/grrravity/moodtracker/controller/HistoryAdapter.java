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

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private ArrayList<Moods> mSavedMoods;

    public HistoryAdapter(ArrayList<Moods> mSavedMoods) {
        this.mSavedMoods = mSavedMoods;
    }

    @Override
    public int getItemCount() {
        return (mSavedMoods.size());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Display each mood element for each view.
     * Show date substitution name for each day (from dayShown list)
     * <p>
     * List width and height are set with a % of the device total pixel regarding mood value.
     * (happier the mood is, longer will be the width.
     * Color also depends on each mood value.
     *
     * @param mSavedMoods : Mood list saved in SharedPreferences.
     * @param position    : position in the list generation. Used as index in the Mood list to
     *                    connect the right elements for each view.
     * @param holder      : ViewHolder from inner MyViewOlder class.
     */
    private void display(ArrayList<Moods> mSavedMoods, int position, MyViewHolder holder) {

        String moodDate = mSavedMoods.get(position).getDate();
        Long currentDate = holder.mCalendar.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date lastDate = sdf.parse(moodDate);
            long lastDateInMs = lastDate.getTime();
            long timeDiff = currentDate - lastDateInMs;
            long daysDiff = TimeUnit.MILLISECONDS.toDays(timeDiff);
            String dayShown[] = {"Hier", "Avant-hier", "Il y a trois jours",
                    "Il y a quatre jours", "Il y a cinq jours", "Il y a six jours",
                    "Il y a une semaine"};
            holder.date.setText(dayShown[(int) daysDiff - 1]);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        DisplayMetrics metrics = holder.itemView.getContext().getResources().getDisplayMetrics();
        double sizeList[] = {0.25, 0.375, 0.5, 0.75, 1};
        int deviceWidth = metrics.widthPixels;
        int deviceHeight = metrics.heightPixels;
        int moodHeight = deviceHeight / 10;
        int moodWidth = (int) (deviceWidth * sizeList[mSavedMoods.get(position).getIndex()]);
        holder.barBackground.setBackgroundColor
                (holder.itemView.getResources().getColor(mSavedMoods.get(position).getColorId()));
        holder.barBackground.setLayoutParams
                (new RelativeLayout.LayoutParams(moodWidth, moodHeight));

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
