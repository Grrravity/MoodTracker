package com.happyapp.grrravity.moodtracker.controller.broadcaster;

        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;

        import com.happyapp.grrravity.moodtracker.controller.helper.SaveHelper;


public class AlarmReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {
        new SaveHelper(context).saveMissingDay();
    }
}
