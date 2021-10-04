package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.example.appnhac.R;

public class Activity_RunApp extends AppCompatActivity
{

    private static String TAG = Activity_RunApp.class.getName();
    private static long SLEEP_TIME = 2;    // Time in seconds to show the picture

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

        setContentView(R.layout.activity_run_app);  //your layout with the picture

        // Start timer and launch main activity
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
    }

    private class IntentLauncher extends Thread {
        @Override
        /**
         * Sleep for some time and than start new activity.
         */
        public void run() {
            try {
                // Sleeping
                Thread.sleep(SLEEP_TIME*1000);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            // Start main activity
            Intent intent = new Intent(Activity_RunApp.this, MainActivity.class);
            Activity_RunApp.this.startActivity(intent);
            Activity_RunApp.this.finish();
        }
    }
}
