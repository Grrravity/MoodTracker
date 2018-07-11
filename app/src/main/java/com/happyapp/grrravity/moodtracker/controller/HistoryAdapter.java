package com.happyapp.grrravity.moodtracker.controller;

import android.content.Context;
import android.content.res.Resources;
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

import com.happyapp.grrravity.moodtracker.R;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private ArrayList<Moods> mSavedMoods;

    public HistoryAdapter(ArrayList<Moods> mSavedMoods){
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

        holder.date.setText(moodDate);
        if (mSavedMoods.get(position).getComment() != null) {
            holder.commentButton.setVisibility(View.VISIBLE);
        }
        DisplayMetrics metrics = holder.itemView.getContext().getResources().getDisplayMetrics();
        float dp = (90 * (mSavedMoods.get(position).getIndex() + 1));
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        holder.barBackground.setBackgroundColor
                (holder.itemView.getResources().getColor(mSavedMoods.get(position).getColorId()));
        holder.barBackground.setLayoutParams
                (new RelativeLayout.LayoutParams( pixels, (int)((metrics.density * 80)+0.5f)));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        display(mSavedMoods, holder.getAdapterPosition(), holder);

        holder.commentButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), mSavedMoods.get
                        (holder.getAdapterPosition()).getComment(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private ImageButton commentButton;
        private RelativeLayout barBackground;


        private MyViewHolder(final View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.history_date);
            commentButton = itemView.findViewById(R.id.history_comment_imageButton);
            barBackground = (RelativeLayout) itemView;
        }


    }
}
