package com.aditya.restaurant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.aditya.restaurant.MainActivity;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.util.UUID;


public class SplashScreenActivity extends AppCompatActivity
{
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    static final String LOG_TAG = MainActivity.class.getCanonicalName();

    //--------- To Save Value In SharedPreference -----------
    public static final String PREF_NAME = "PEOPLE_PREFERENCES";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean loggedIn = shared.getBoolean("logged", false);

        System.out.println("loggedIn::::"+loggedIn);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (loggedIn != true)
                {

                    Intent mainIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else
                {

                    Intent mainIntent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }



}