package com.happyapp.grrravity.moodtracker.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.happyapp.grrravity.moodtracker.model.Moods;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class MoodPreferences {
    private static String MOODPREFS = "MOODPEFS";
    private static MoodPreferences mInstance;

    private SharedPreferences mMoodPrefs;

    private MoodPreferences(Context context) {

        mMoodPrefs = context.getSharedPreferences(MOODPREFS, Activity.MODE_PRIVATE);
    }

    public static MoodPreferences getInstance(Context context) {
        if (mInstance == null)
            mInstance = new MoodPreferences(context);
        return mInstance;
    }

    /**
     * uses gson to store moods as an Arraylist of strings.
     *
     * @param moods : lists of Moods objects.
     */
    public void storeMoods(ArrayList<Moods> moods) {
        //start writing (open the file)
        SharedPreferences.Editor editor = mMoodPrefs.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(moods);
        editor.putString(MOODPREFS, json);
        //close the file
        editor.apply();
    }

    /**
     * use gson to send back an ArrayList of Moods from String.
     *
     * @return : ArrayList of moods stored.
     */
    public ArrayList<Moods> getMoods() {
        Gson gson = new Gson();
        String json = mMoodPrefs.getString(MOODPREFS, "");

        ArrayList<Moods> moods;

        if (json.length() < 1) {
            moods = new ArrayList<>();
        } else {
            Type type = new TypeToken<ArrayList<Moods>>() {
            }.getType();
            moods = gson.fromJson(json, type);
        }
        return moods;
    }

}
