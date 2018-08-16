package id.net.gmedia.perkasaapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivitySplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DELAY = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!AppSharedPreferences.isLoggedIn(ActivitySplashScreen.this)){
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityLogin.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(ActivitySplashScreen.this, ActivityHome.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_SCREEN_DELAY);
    }
}