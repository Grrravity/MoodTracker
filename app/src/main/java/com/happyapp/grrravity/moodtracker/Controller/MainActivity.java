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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mSmileyView;
    private RelativeLayout mRelativeLayout;
    GestureDetector gestureDetector;
    private MoodPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPref = MoodPreferences.getInstance(this);

        Log.d(TAG, "onCreate: ");
        initVars();
        gestureDetector = new GestureDetector(this, this);
        initMoodList();

    }

    private void initMoodList() {
        ArrayList<Moods> moods = new ArrayList<>();
        // TODO mettre les valeurs aux moods. (moods.add etc)
        Moods moods1 = new Moods();
    }

    private void initVars() {
        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
    }

    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        int counter = 2;
        if (motionEvent1.getY() - motionEvent2.getY() > 50) {
            if (counter > 3) {
                counter = 4;
            } else {
                counter++;
            }

            changeBackground();
            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > 50) {
            if (counter < 1) {
                counter = 0;
            } else {
                counter--;
            }

            changeBackground();
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

    public void changeBackground(int drawableId, int colorId) {
        mSmileyView.setImageResource(drawableId);
        mRelativeLayout.setBackgroundColor(colorId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause :");
    }


}