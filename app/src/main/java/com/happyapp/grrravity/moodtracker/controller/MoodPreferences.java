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
    private static MoodPreferences instance;
    private static String MOODPREFS = "MOODPEFS";

    private SharedPreferences moodPrefs;

    private MoodPreferences(Context context) {

        moodPrefs = context.getSharedPreferences(MOODPREFS, Activity.MODE_PRIVATE);
    }

    public static MoodPreferences getInstance(Context context) {
        if (instance == null)
            instance = new MoodPreferences(context);
        return instance;
    }

    public void storeMoods(ArrayList<Moods> moods) {
        //start writing (open the file)
        SharedPreferences.Editor editor = moodPrefs.edit();
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(moods);
        editor.putString(MOODPREFS, json);
        //close the file
        editor.apply();
    }

    public void addMoods(ArrayList<Moods> moods){
        SharedPreferences.Editor editor = moodPrefs.edit();
        String storedMoods = moodPrefs.getString(MOODPREFS, "");
        //put the data
        Gson gson = new Gson();
        String json = gson.toJson(storedMoods + ", " + moods);
        editor.putString(MOODPREFS, json);
        //close the file
        editor.apply();
    }

    public ArrayList<Moods> getMoods() {
        Gson gson = new Gson();
        String json = moodPrefs.getString(MOODPREFS, "");

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
