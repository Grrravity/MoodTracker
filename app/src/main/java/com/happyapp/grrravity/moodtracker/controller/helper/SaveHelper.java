package com.happyapp.grrravity.moodtracker.controller.helper;

import android.content.Context;

import com.happyapp.grrravity.moodtracker.R;
import com.happyapp.grrravity.moodtracker.controller.MoodPreferences;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SaveHelper {

    private static MoodPreferences mPref;

    public SaveHelper(Context context) {
        mPref = MoodPreferences.getInstance(context);
    }

    /**
     * Comparing device date and the last date stored in MoodPreference ArrayList to store a new
     * mood if it's not the same day.
     */
    public void saveMissingDay() {

        Calendar mCalendar = Calendar.getInstance();

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
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add "normal" mood to the stored mood list. Then, it will save the current date for this mood
     * entry.
     * Then it will remove the first value stored in mood list if the list is longer than 7 element,
     * and finally it saves mood list in sharedPreference.
     *
     * @param storedMood : (ArrayList) mood list from MoodPreferences.java
     * @param storedDate : (Date) date stored in the last MoodPreferences
     */
    private void fillingMoodList(ArrayList<Moods> storedMood, Date storedDate) {

        storedMood.add(new Moods("Bien",
                R.drawable.smileynormal, R.color.color_normal, 2,
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
    }

    /**
     * Get device date, setting it as date for the last mood entry, then it will :
     * - compare current date with the last saved date to replace it if they are equals.
     * - remove the older mood if more than 7 element are stored.
     * Then it saves the mood list in SharedPreference.
     *
     * @param mMoods : Mood list that requires to b saved in sharedPreference.
     */
    public void saveData(Moods mMoods) {

        Calendar mCalendar = Calendar.getInstance();

        Date dateOnSave = mCalendar.getTime(); // Current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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

}
