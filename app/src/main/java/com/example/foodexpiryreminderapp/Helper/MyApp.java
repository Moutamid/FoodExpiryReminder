package com.example.foodexpiryreminderapp.Helper;

// MainActivity.java or Application class
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.fxn.stash.Stash;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);
    }

  }
