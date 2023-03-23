package com.example.myapplication;

import android.app.Application;
import android.util.Log;

public class AppController extends Application {

    private static AppController instance;

    public static AppController getInstance() {
        return instance;
    }

    //Called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application-Class", "onCreate: Called");
        instance = this;
    }
}
