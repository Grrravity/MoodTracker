package com.happyapp.grrravity.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.GestureDetector.OnGestureListener;

import com.happyapp.grrravity.moodtracker.Model.Moods;
import com.happyapp.grrravity.moodtracker.R;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mSmileyView;
    private RelativeLayout mRelativeLayout;
    private Moods[] mMoodTable;
    GestureDetector gestureDetector;
    private Moods mMoods;
    private MoodPreferences mPref;

    private int counter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = MoodPreferences.getInstance(this);

        Log.d(TAG, "onCreate: ");
        initVars();
        initMoodList();
        gestureDetector = new GestureDetector(this, this);


    }

    private void initMoodList() {
        mMoodTable = new Moods[]{
                new Moods("Sad",
                        R.drawable.smileysad, R.color.color_sad, 0,
                        ""),
                new Moods("Disappointed",
                        R.drawable.smileydisappointed, R.color.color_disappointed, 1,
                        ""),
                new Moods("Normal",
                        R.drawable.smileynormal, R.color.color_normal, 2,
                        ""),
                new Moods("Happy",
                        R.drawable.smileyhappy, R.color.color_happy, 3,
                        ""),
                new Moods("SuperHappy",
                        R.drawable.smileysuperhappy, R.color.color_super_happy, 4,
                        ""),
        };

    }

    private void initVars() {
        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
    }

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

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {

        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        // TODO Auto-generated method stub

        return gestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {

        // TODO Auto-generated method stub

        return false;
    }

    public void changeBackground(int counter) {
        mMoods = mMoodTable[counter];
        mSmileyView.setImageResource(mMoods.getDrawableId());
        mRelativeLayout.setBackgroundColor(getResources().getColor(mMoods.getColorId()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause :");
    }


}