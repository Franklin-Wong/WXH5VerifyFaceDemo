package com.integration.project;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Wongerfeng on 2020/8/7.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
