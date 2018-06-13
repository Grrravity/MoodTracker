package com.happyapp.grrravity.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.GestureDetector.OnGestureListener;

import com.happyapp.grrravity.moodtracker.R;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mSmileyView;
    private RelativeLayout mRelativeLayout;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: ");
        initVars();
        gestureDetector = new GestureDetector(this, this);

    }

    private void initVars() {
        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
    }

    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {
        int screenID = Preferences.getInstance(this).getSCREENID();
        if (motionEvent1.getY() - motionEvent2.getY() > 50) {
            if (screenID > 3) {
                screenID = 4;
                Preferences.getInstance(this).setScreenID(screenID);
            } else {
                screenID++;
                Preferences.getInstance(this).setScreenID(screenID);
            }

            Preferences.getInstance(this).setImageMood();
            Preferences.getInstance(this).setBackgroundMood();
            changeBackground();
            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > 50) {
            if (screenID < 1) {
                screenID = 0;
                Preferences.getInstance(this).setScreenID(screenID);
            } else {
                screenID--;
                Preferences.getInstance(this).setScreenID(screenID);
            }
            Preferences.getInstance(this).setImageMood();
            Preferences.getInstance(this).setBackgroundMood();
            changeBackground();
            return true;

        }

        if (motionEvent1.getX() - motionEvent2.getX() > 50) {
            return true;
        }

        if (motionEvent2.getX() - motionEvent1.getX() > 50) {
            return true;
        }

        else
        {
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

    public void changeBackground() {
        mSmileyView.setImageResource(getResources().getIdentifier(
                "drawable/"+ Preferences.getInstance(this).getImage() ,
                null, getApplicationContext().getPackageName()));
        mRelativeLayout.setBackgroundColor(getResources().getIdentifier(
                "values/color/"+ Preferences.getInstance(this).getBackground(),
                null, getApplicationContext().getPackageName()));
        }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause :");
    }


}