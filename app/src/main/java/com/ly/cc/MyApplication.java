package com.ly.cc;

import android.app.Application;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;

/**
 * Created by liuzhif on 15/7/31.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CustomActivityOnCrash.install(this);
    }
}
