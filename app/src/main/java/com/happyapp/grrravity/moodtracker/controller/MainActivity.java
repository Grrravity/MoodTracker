package com.happyapp.grrravity.moodtracker.controller;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
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

import com.happyapp.grrravity.moodtracker.controller.helper.SaveHelper;
import com.happyapp.grrravity.moodtracker.model.Moods;
import com.happyapp.grrravity.moodtracker.R;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnGestureListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    //Layout vars
    private ImageView mSmileyView;
    private ImageButton mCommentButton;
    private ImageButton mHistoryButton;
    private ImageButton mShareButton;
    private RelativeLayout mRelativeLayout;

    //Mood object vars
    private ArrayList<Moods> moods;
    private Moods mMoods;

    //Gesture detector (change OnFling_Sensibility to change in app gesture sensibility)
    GestureDetector gestureDetector;
    final int ONFLING_SENSIBILITY = 50;


    //other vars
    String mComment;
    private int counter = 3;
    private SaveHelper mSaveHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        gestureDetector = new GestureDetector(this, this);
        Log.d(TAG, "onCreate: ");
        mSaveHelper = new SaveHelper(this);

        initVars();
        initMoodList();
        commentListener();
        shareListener();
        historyButton();
        initAlarmManager();
    }

    /**
     * Connecting layout xml element by id
     */
    private void initVars() {

        mSmileyView = findViewById(R.id.smileyImage);
        mRelativeLayout = findViewById(R.id.relativeLayout);
        mCommentButton = findViewById(R.id.comment_button);
        mHistoryButton = findViewById(R.id.history_button);
        mShareButton = findViewById(R.id.share_button);
    }

    /**
     * Creating every moods with their assets :
     * name : (String)
     * drawable : (int) smiley ID
     * color : (int) color ID
     * index : (int) index of the mood  (from 0 to 4)
     * comment : (String) user-added comment
     * <p>
     * set mMoods to normal value
     */
    private void initMoodList() {

        moods = new ArrayList<>();
        moods.add(new Moods("Triste",
                R.drawable.smileysad, R.color.color_sad, 0,
                ""));
        moods.add(new Moods("Déçu",
                R.drawable.smileydisappointed, R.color.color_disappointed, 1,
                ""));
        moods.add(new Moods("Bien",
                R.drawable.smileynormal, R.color.color_normal, 2,
                ""));
        moods.add(new Moods("Heureux",
                R.drawable.smileyhappy, R.color.color_happy, 3,
                ""));
        moods.add(new Moods("Très heureux",
                R.drawable.smileysuperhappy, R.color.color_super_happy, 4,
                ""));

        mMoods = new Moods("Heureux",
                R.drawable.smileyhappy, R.color.color_happy, 3,
                "");
    }

    /**
     * Method to add a comment on click. Set the text visible on
     * MainActivity if comment is not empty.
     * It will provide an AlertDialog to enter the comment.
     * Also, adding an empty comment in there will erase previous added comment.
     */
    private void commentListener() {

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


    /**
     * method to create intent to share mood and sending them with other apps.
     * messages are different if a comment has been set.
     */
    private void shareListener() {
        //method to add a comment when comment button is clicked. Set the text visible on
        //MainActivity if it's not empty.
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                if (mMoods.getComment().equals("") || mMoods.getComment() == null) {
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Salut ! Je te partage mon ressenti car je suis " +
                                    mMoods.getName() +
                                    "." +
                                    "\r\n -- Message envoyé depuis mon application MoodTracker");
                } else {
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Salut ! Je te partage mon ressenti car je suis " +
                                    mMoods.getName() +
                                    "; et j'ai d'ailleurs retenu ça de ma journée : " +
                                    mMoods.getComment() +
                                    "\r\n -- Message envoyé depuis mon application MoodTracker --");
                }
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    /**
     * Save data on clic.
     * Start History Activity.
     */
    private void historyButton() {

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSaveHelper.saveData(mMoods);
                Intent historyActivity = new Intent
                        (MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }

        });
    }

    /**
     * Set a repeating RTC alarm at 11:59'59 each day.
     * the OnReceive method is in AlarmReveiver.java
     */
    private void initAlarmManager() {
        //alarm manager for save
        Calendar midnightCalendar = Calendar.getInstance();
        midnightCalendar.set(Calendar.HOUR_OF_DAY, 23);
        midnightCalendar.set(Calendar.MINUTE, 59);
        midnightCalendar.set(Calendar.SECOND, 59);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent midnightPI = PendingIntent.getBroadcast(this,
                0,
                new Intent("com.happyapp.grrravity.moodtracker.controller.broadcaster.AlarmReceiver"),
                0);

        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC,
                midnightCalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                midnightPI);

    }

    /**
     * Method to watch if the user is swiping up and down.
     * Will use changeScreenDisplayed(); if true, sending (int) counter to change the screen
     * displayed.
     *
     * @param motionEvent1 : The first recorded event on screen (linked with its X and Y coordinate)
     *                     used to get Delta on movement.
     * @param motionEvent2 : The second recorded event on screen. Same as motionEvent 1.
     * @param X            : unused param as we're only looking for up & down event.
     * @param Y            : Coordinate from up and down position on the screen.
     * @return : true if Y Delta is higher than ONFLING_SENSIBILITY.
     */
    public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

        if (motionEvent1.getY() - motionEvent2.getY() > ONFLING_SENSIBILITY) {
            if (counter < 4) {
                counter++;
                changeScreenDisplayed(counter);
            }
            return true;
        }

        if (motionEvent2.getY() - motionEvent1.getY() > ONFLING_SENSIBILITY) {
            if (counter > 0) {
                counter--;
                changeScreenDisplayed(counter);
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

    /**
     * Method to get Drawable ID and Color ID of the current mood, and displaying them on the
     * screen.
     *
     * @param counter : index value of the new mood called from OnFling(); method.
     */
    public void changeScreenDisplayed(int counter) {
        //Display background and drawable when called from onFling method
        mMoods = moods.get(counter);
        mSmileyView.setImageResource(mMoods.getDrawableId());
        mRelativeLayout.setBackgroundColor(getResources().getColor(mMoods.getColorId()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSaveHelper.saveData(mMoods);
        Log.d(TAG, "onPause :");
    }

}