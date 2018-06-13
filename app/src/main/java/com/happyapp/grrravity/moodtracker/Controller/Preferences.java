package com.happyapp.grrravity.moodtracker.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



public class Preferences {
    private static Preferences instance;
    private static String IMAGE = "smileynormal", BACKGROUND = "cornflower_blue_65";
    private static int SCREENID = 2;
    private SharedPreferences imageMood, backgroundMood, screenID;

    private Preferences(Context context) {

        imageMood = context.getSharedPreferences(IMAGE, Activity.MODE_PRIVATE);
        backgroundMood = context.getSharedPreferences(BACKGROUND, Activity.MODE_PRIVATE);
        screenID = context.getSharedPreferences(String.valueOf(SCREENID), Activity.MODE_PRIVATE);

    }

    public static Preferences getInstance(Context context) {
        if (instance == null)
            instance = new Preferences(context);
        return instance;
    }

    public void setImageMood() {
        String[] screenList = new String[] {"smileysad","smileydisappointed",
                "smileynormal","smileyhappy","smileysuperhappy"};
        SharedPreferences.Editor editor = imageMood.edit();
        editor.putString(IMAGE, screenList[getSCREENID()]);
        editor.apply();
        }

    public void setBackgroundMood(){
        String[] backgroundList = new String[] {"faded_red","warm_grey",
                "cornflower_blue_65","light_sage","banana_yellow"};
        SharedPreferences.Editor editor = backgroundMood.edit();
        editor.putString(BACKGROUND, backgroundList[getSCREENID()]);
        editor.apply();
    }
    public void setScreenID(int screen){
        SharedPreferences.Editor editor = screenID.edit();
        editor.putInt(String.valueOf(SCREENID), screen);
        editor.apply();
    }

    public int getSCREENID(){
        return screenID.getInt(String.valueOf(SCREENID), 2);
    }

    public String getImage() {
        return imageMood.getString(IMAGE, "smileynormal");
    }

    public String getBackground() {
        return backgroundMood.getString(BACKGROUND, "cornflower_blue_65");
    }
}
