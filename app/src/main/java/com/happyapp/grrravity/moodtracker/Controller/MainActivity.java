package com.happyapp.grrravity.moodtracker.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.GestureDetector.OnGestureListener;

import com.happyapp.grrravity.moodtracker.R;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private ImageView mSmileyView;
    private RelativeLayout mRelativeLayout;
    private int mMood = 2;
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        out.println("MainActivity::onCreate");
        initVars();
        gestureDetector = new GestureDetector(MainActivity.this, MainActivity.this);

    }

    private void initVars() {
        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
    }

    public boolean onFling (MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

        if(motionEvent1.getY() - motionEvent2.getY() > 50){

            return true;
        }

        if(motionEvent2.getY() - motionEvent1.getY() > 50){

            return true;
        }
        if(motionEvent1.getX() - motionEvent2.getX() > 50){
            if (mMood > 3) {
                mMood = 4;
                changeBackground(mMood);
            }
            else
                mMood ++;
            changeBackground(mMood);
            return true;
        }

        if(motionEvent2.getX() - motionEvent1.getX() > 50) {
            if (mMood < 1){
                mMood = 0;
                changeBackground(mMood);
            }
            else
                mMood --;
            changeBackground(mMood);
            return true;
        }
        else {

            return true ;
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

    public void changeBackground(int mood){
        if (mood == 0){
            mSmileyView .setImageResource(R.drawable.smileysad);
            mRelativeLayout .setBackgroundColor(getResources().getColor(R.color.faded_red));
        }
        if (mood == 1){
            mSmileyView .setImageResource(R.drawable.smileydisappointed);
            mRelativeLayout .setBackgroundColor(getResources().getColor(R.color.warm_grey));
        }
        if (mood == 2){
            mSmileyView .setImageResource(R.drawable.smileynormal);
            mRelativeLayout .setBackgroundColor(getResources().getColor(R.color.cornflower_blue_65));
        }
        if (mood == 3 ){
            mSmileyView .setImageResource(R.drawable.smileyhappy);
            mRelativeLayout .setBackgroundColor(getResources().getColor(R.color.light_sage));
        }
        if (mood == 4){
            mSmileyView .setImageResource(R.drawable.smileysuperhappy);
            mRelativeLayout .setBackgroundColor(getResources().getColor(R.color.banana_yellow));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        out.println("MainActivity::onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        out.println("MainActivity::onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        out.println("MainActivity::onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        out.println("MainActivity::onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        out.println("MainActivity::onDestroy()");
    }

}