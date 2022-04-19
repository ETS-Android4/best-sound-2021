package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextPaint;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.appnhac.R;

public class Activity_RunApp extends AppCompatActivity
{
    TextView textView;
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

        // xử lí text view
        textView = (TextView) findViewById(R.id.textview_nameapp);
        textView.setText("Best Sounds 2021");

        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Best Sounds 2021");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#F97C3C"),
                        Color.parseColor("#FDB54E"),
                        Color.parseColor("#64B678"),
                        Color.parseColor("#478AEA"),
                        Color.parseColor("#8446CC"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
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
