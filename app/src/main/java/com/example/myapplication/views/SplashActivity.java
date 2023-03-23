package com.example.myapplication.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.RandomString;
import com.example.myapplication.prefs.AppSharedPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (loggedIn()){
            controlSplashActivity();

        }else {
            controlSplashActivity2();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    private void controlSplashActivity() {
        //3000ms - 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

    private void controlSplashActivity2() {
        //3000ms - 3s
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }



    private boolean loggedIn(){
      return AppSharedPreferences.getInstance().isLoggedIn();
    }
}