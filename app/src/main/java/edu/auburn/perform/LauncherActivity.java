package edu.auburn.perform;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by fzhou on 12/19/14.
 */

public class LauncherActivity extends Activity {
    final private int SPLASH_TIME = 2000;
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                launchMainActivity();
            }
        }, SPLASH_TIME);

        setContentView(R.layout.launcher);
    }

    private void launchMainActivity() {

        prefs = getSharedPreferences("phone", MODE_PRIVATE);

        if (prefs.getBoolean("firststart", true)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firststart", false);
            editor.commit();
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}