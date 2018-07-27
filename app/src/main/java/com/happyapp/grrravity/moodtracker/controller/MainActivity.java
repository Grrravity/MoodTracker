package com.happyapp.grrravity.moodtracker.controller;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

    //Layout vars
    private ImageView mSmileyView;
    private ImageButton mCommentButton;
    private ImageButton mHistoryButton;
    private ImageButton mShareButton;
    private RelativeLayout mRelativeLayout;

    //Mood object vars
    private ArrayList<Moods> moods;
    private Moods mMoods;

    //SharedPrefs vars
    private MoodPreferences mPref;

    //Gesture detector (change OnFling_Sensibility to change in app gesture sensibility)
    GestureDetector gestureDetector;
    final int ONFLING_SENSIBILITY = 50;

    //Calendar
    Calendar mCalendar = Calendar.getInstance();


    //other vars
    String mComment;
    private int counter = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mPref = MoodPreferences.getInstance(this);
        gestureDetector = new GestureDetector(this, this);
        Log.d(TAG, "onCreate: ");

        initVars();
        initMoodList();
        commentListener();
        shareListener();
        historyButton();
        initAlarmManager();

    }

    /**
     * Connecting layout element by id
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
     * name (String)
     * drawable : (int) smiley ID
     * color : (int) color ID
     * index : (int) index of the mood  (from 0 to 4)
     * comment : (String) user-added comment
     */
    private void initMoodList() {

        // TODO renome mMoods or qqch
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

        mMoods = new Moods("Bien",
                R.drawable.smileynormal, R.color.color_normal, 2,
                "");
    }

    /**
     * Method to add a comment when comment button is clicked. Set the text visible on
     * MainActivity if comment is not empty.
     * <p>
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
                            "Salut ! Je te partage mon ressentis car je suis " +
                                    mMoods.getName() +
                                    "." +
                                    "\r\n -- Message envoyé depuis mon application MoodTracker");
                } else {
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Salut ! Je te partage mon ressentis car je suis " +
                                    mMoods.getName() +
                                    "; et j'ai d'ailleurs pensé ça de ma journée : " +
                                    mMoods.getComment() +
                                    "\r\n -- Message envoyé depuis mon application MoodTracker --");
                }
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void historyButton() {

        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent historyActivity = new Intent
                        (MainActivity.this, HistoryActivity.class);
                startActivity(historyActivity);
            }

        });
    }

    private void initAlarmManager() {
        //alarm manager for save
        Calendar midnightCalendar = Calendar.getInstance();
        midnightCalendar.set(Calendar.HOUR_OF_DAY, 0);
        midnightCalendar.set(Calendar.MINUTE, 0);
        midnightCalendar.set(Calendar.SECOND, 0);

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                saveMissingDay();
            }
        };
        registerReceiver(br, new IntentFilter(
                "com.happyapp.grrravity.moodtracker.controller.MainActivity"));
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent midnightPI = PendingIntent.getBroadcast(this,
                0,
                new Intent("com.happyapp.grrravity.moodtracker.controller.MainActivity"),
                0);

        assert alarmManager != null;
        alarmManager.setRepeating(AlarmManager.RTC,
                midnightCalendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                midnightPI);

    }

    /**
     * Use mood saved in SharedPreference to compare current date with the last saved. If there is
     * any missing day, it will save them by copying the last mood saved.
     */
    private void saveMissingDay() {

        Long currentDate = mCalendar.getTimeInMillis();
        ArrayList<Moods> storedMood = mPref.getMoods();
        String dateFromMood = storedMood.get(storedMood.size() - 1).getDate();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date storedDate = sdf.parse(dateFromMood);
            long storedTime = storedDate.getTime();
            long timeGap = currentDate - storedTime;
            long daysGap = TimeUnit.MILLISECONDS.toDays(timeGap);

            if (daysGap > 0) {
                fillingMoodList(storedMood, storedDate);
            } else {
                Toast.makeText(this, "Humeurs à jour", Toast.LENGTH_SHORT).show();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param storedMood : (ArrayList) mood list from MoodPreferences.java
     * @param storedDate : (Date) date stored in the last MoodPreferences
     *                   <p>
     *                   First it fill storedMood list for every missing days and giving each of them a date.
     *                   Then, if there's more than 7 element in storedMood, it will remove the older elements until
     *                   the list is filled with 7 elements.
     *                   Once done, it's being saved.
     */
    private void fillingMoodList(ArrayList<Moods> storedMood, Date storedDate) {

        storedMood.add(new Moods(
                (storedMood.get(storedMood.size() - 1).getName()),
                (storedMood.get(storedMood.size() - 1).getDrawableId()),
                (storedMood.get(storedMood.size() - 1).getColorId()),
                (storedMood.get(storedMood.size() - 1).getIndex()),
                ""));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar fillingCalendar = Calendar.getInstance();
        fillingCalendar.setTime(storedDate);
        fillingCalendar.add(Calendar.DATE, 1);
        storedMood.get(storedMood.size() - 1).setDate(sdf.format(fillingCalendar.getTime()));

        if (storedMood.size() > 7) {
            storedMood.remove(0);
        }
        mPref.storeMoods(storedMood);

        Toast.makeText(this, "Humeurs mises à jour", Toast.LENGTH_SHORT).show();
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

        Date dateOnSave = mCalendar.getTime(); // Current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()); // Set your date format String
        String currentDate = sdf.format(dateOnSave);

        //save current date in the running mMoods list.
        mMoods.setDate(currentDate);

        ArrayList<Moods> storedMood = mPref.getMoods();


        if (storedMood != null && storedMood.size() > 0) {
            if (storedMood.size() < 8) {
                String storedDate = String.valueOf(storedMood.get(storedMood.size() - 1).getDate());
                if (storedDate.equals(currentDate)) {

                    storedMood.remove(storedMood.size() - 1);
                }
            } else {
                String storedDate = String.valueOf(storedMood.get(storedMood.size() - 1).getDate());
                if (storedDate.equals(currentDate)) {

                    storedMood.remove(storedMood.size() - 1);
                } else {
                    storedMood.remove(0);
                }
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