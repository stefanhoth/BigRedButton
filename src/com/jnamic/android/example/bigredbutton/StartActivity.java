package com.jnamic.android.example.bigredbutton;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class StartActivity extends Activity implements View.OnClickListener {

    private static final String KEY_PRESSED = StartActivity.class.getPackage()+".KEY_PRESSED";

    private int buttonPressed;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            buttonPressed = savedInstanceState.getInt(KEY_PRESSED,0);
        }else{
            buttonPressed = 0;
        }

        setContentView(R.layout.main);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.button) {
            buttonPressed++;
            showNotification();
        } else{
            Toast.makeText(getBaseContext(), R.string.wrong_button, Toast.LENGTH_SHORT).show();
        }

    }

    private void showNotification() {

        //code taken from http://developer.android.com/guide/topics/ui/notifiers/notifications.html
        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setLights(android.R.color.holo_red_dark, 1000, 100)
                        .setTicker(getString(R.string.button_pressed_text,buttonPressed))
                        .setSmallIcon(R.drawable.ic_stat_boom)
                        .setContentTitle(getString(R.string.button_pressed_title))
                        .setContentText(getString(R.string.button_pressed_text,buttonPressed));
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, StartActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(StartActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(R.id.notification_booms, mBuilder.build());

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_PRESSED, buttonPressed);
    }
}
