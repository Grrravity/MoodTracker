package com.happyapp.grrravity.moodtracker.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.GestureDetector.OnGestureListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import com.happyapp.grrravity.moodtracker.model.Moods;
import com.happyapp.grrravity.moodtracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    //layout-relative vars
    private ImageView mSmileyView;
    private ImageButton mCommentButton, mHistoryButton;
    private TextView mCommentText;
    private RelativeLayout mRelativeLayout;

    //Mood object relative vars
    private ArrayList<Moods> moods;
    private Moods mMoods;

    //SharedPrefs vars
    private MoodPreferences mPref;

    //gesture detector
    GestureDetector gestureDetector;
    final int ONFLING_SENSIBILITY = 50;

    //calendar
    Calendar mCalendar = Calendar.getInstance();


    //other vars
    String mComment;
    private int counter = 2;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mPref = MoodPreferences.getInstance(this);
        gestureDetector = new GestureDetector(this, this);


        Log.d(TAG, "onCreate: ");

        //initiation of xml vars
        initVars();

        //initiation of mood list
        initMoodList();

        //method to add comment or to launch History activity
        commentListener();
        historyButton();

        //save missing day if any
        saveMissingDays();

    }

    private void saveMissingDays() {
        // Current time
        Long d = mCalendar.getTimeInMillis();
        ArrayList<Moods> storedMood = mPref.getMoods();
        String lastDate = storedMood.get(storedMood.size() - 1).getDate();
        // Set your date format String
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date mDate = sdf.parse(lastDate);
            long timeInMilliseconds = mDate.getTime();
            long msDiff = d - timeInMilliseconds;
            long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
            if (daysDiff > 0) {
                for (long test = daysDiff; test > 0; test--) {
                    storedMood.add(new Moods(
                            (storedMood.get(storedMood.size() - 1).getName()),
                            (storedMood.get(storedMood.size() - 1).getDrawableId()),
                            (storedMood.get(storedMood.size() - 1).getColorId()),
                            (storedMood.get(storedMood.size() - 1).getIndex()),
                            (storedMood.get(storedMood.size() - 1).getComment())));
                }
                mPref.storeMoods(storedMood);
                Toast.makeText(this, "Humeurs mises à jour", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Humeurs à jour", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void initMoodList() {
        //Creating every moods with their assets (background, drawable, comment and name...)
        // TODO renome mMoods or qqch
        moods = new ArrayList<>();
        moods.add(new Moods("Sad",
                R.drawable.smileysad, R.color.color_sad, 0,
                ""));
        moods.add(new Moods("Disappointed",
                R.drawable.smileydisappointed, R.color.color_disappointed, 1,
                ""));
        moods.add(new Moods("Normal",
                R.drawable.smileynormal, R.color.color_normal, 2,
                ""));
        moods.add(new Moods("Happy",
                R.drawable.smileyhappy, R.color.color_happy, 3,
                ""));
        moods.add(new Moods("SuperHappy",
                R.drawable.smileysuperhappy, R.color.color_super_happy, 4,
                ""));

        mMoods = new Moods("Normal",
                R.drawable.smileynormal, R.color.color_normal, 2,
                "");
    }

    private void initVars() {
        //Connecting layout element by id
        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
        mCommentButton = findViewById(R.id.comment_button);
        mHistoryButton = findViewById(R.id.history_button);
        mCommentText = findViewById(R.id.main_comment_text);

    }

    private void commentListener() {
        //method to add a comment when comment button is clicked. Set the text visible on
        //MainActivity if it's not empty.
        mCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder
                        (MainActivity.this);
                @SuppressLint("InflateParams") final View mView = getLayoutInflater().inflate
                        (R.layout.dialog_input, null);
                final EditText mCommentInput = mView.findViewById(R.id.comment);

                final Button mOkButton = mView.findViewById(R.id.ok_button);
                Button mCancelButton = mView.findViewById(R.id.cancel_button);

                mBuilder.setView(mView);

                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mOkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mComment = mCommentInput.getText().toString();
                        mMoods = moods.get(counter);
                        mMoods.setComment(String.valueOf(mComment));
                        mCommentText.setText(mComment);
                        if (mComment != null) {
                            mCommentText.setVisibility(View.VISIBLE);
                        } else {
                            mCommentText.setVisibility(View.GONE);
                        }
                        dialog.cancel();
                    }
                });

                mCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });
    }

    private void historyButton() {
        saveData();
        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent historyActivity = new Intent
                        (MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }

        });
    }

    // Method to change image and background when user moves up or down
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

        if (motionEvent1.getY() - motionEvent2.getY() > ONFLING_SENSIBILITY) {
            if (counter < 4) {
                counter++;
                changeBackground(counter);
            }
            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > ONFLING_SENSIBILITY) {
            if (counter > 0) {
                counter--;
                changeBackground(counter);
            }
            return true;
        }

        if (motionEvent1.getX() - motionEvent2.getX() > ONFLING_SENSIBILITY) {
            return true;
        }

        if (motionEvent2.getX() - motionEvent1.getX() > ONFLING_SENSIBILITY) {
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void onLongPress(MotionEvent arg0) {

        // unused

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

        // unused

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

        // unused

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        // unused

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // unused

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {

        // unused

        return false;
    }

    // TODO renomer changebackground pour comprendre écran
    public void changeBackground(int counter) {
        //Display background and drawable when called from onFling method
        mMoods = moods.get(counter);
        mSmileyView.setImageResource(mMoods.getDrawableId());
        mRelativeLayout.setBackgroundColor(getResources().getColor(mMoods.getColorId()));
    }

    public void saveData() {
        //save the mood. If there's already another mood stored, add it without overwrite instead
        // TODO add calendar value to store only 1 mood a day

        Date d = mCalendar.getTime(); // Current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Set your date format String
        String currentDate = sdf.format(d);

        //save current date in the running mMoods list.
        mMoods.setDate(currentDate);

        ArrayList<Moods> storedMood = mPref.getMoods();


        if (storedMood != null && storedMood.size() > 0) {
            if (storedMood.size() < 8) {
                String storedDate = String.valueOf(storedMood.get(storedMood.size() - 1).getDate());
                if (storedDate.equals(currentDate)) {

                    storedMood.remove(storedMood.size() - 1);
                }
            }
            else {
                storedMood.remove(0);
            }
            // NPA managed in moodPref
            storedMood.add(mMoods);
            mPref.storeMoods(storedMood);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        Log.d(TAG, "onPause :");
    }


}