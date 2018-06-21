package com.happyapp.grrravity.moodtracker.Controller;

import android.annotation.SuppressLint;
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

import com.google.gson.Gson;

import com.happyapp.grrravity.moodtracker.Model.Moods;
import com.happyapp.grrravity.moodtracker.R;

import java.util.ArrayList;

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

        //method to add comment if clicked on comment button.
        commentListener();

    }

    private void initMoodList() {
        //Creating every moods with their assets (background, drawable, comment and name
        moods = new ArrayList<>();
                moods.add (new Moods("Sad",
                        R.drawable.smileysad, R.color.color_sad, 0,
                        ""));
                moods.add (new Moods("Disappointed",
                        R.drawable.smileydisappointed, R.color.color_disappointed, 1,
                        ""));
                moods.add (new Moods("Normal",
                        R.drawable.smileynormal, R.color.color_normal, 2,
                        ""));
                moods.add (new Moods("Happy",
                        R.drawable.smileyhappy, R.color.color_happy, 3,
                        ""));
                moods.add (new Moods("SuperHappy",
                        R.drawable.smileysuperhappy, R.color.color_super_happy, 4,
                        ""));

    }

    private void commentListener(){
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
                        mCommentText.setVisibility(View.VISIBLE);
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

    private void initVars() {
        //Connecting layout element by id
        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
        mCommentButton = findViewById(R.id.comment_button);
        mHistoryButton = findViewById(R.id.history_button);
        mCommentText = findViewById(R.id.main_comment_text);
    }

    // Method to change image and background when user moves up or down
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

        if (motionEvent1.getY() - motionEvent2.getY() > 50) {
            if (counter < 4) {
                counter++;
                changeBackground(counter);
            }
            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > 50) {
            if (counter > 0) {
                counter--;
                changeBackground(counter);
            }
            return true;
        }

        if (motionEvent1.getX() - motionEvent2.getX() > 50) {
            return true;
        }

        if (motionEvent2.getX() - motionEvent1.getX() > 50) {
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

    public void changeBackground(int counter) {
        //Display background and drawable when called from onFling method
        mMoods = moods.get(counter);
        mSmileyView.setImageResource(mMoods.getDrawableId());
        mRelativeLayout.setBackgroundColor(getResources().getColor(mMoods.getColorId()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause :");
    }


}