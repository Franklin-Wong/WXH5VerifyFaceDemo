package com.example.network.utils;

import android.util.Log;

import rx.android.BuildConfig;


/**
 * Created by Wongerfeng on 2020/8/11.
 */
public class LogUtils {

    private static boolean debug = BuildConfig.DEBUG;
    public static void i(String tag, String message) {
        if (debug) {
            Log.i(tag , "i: "+message);
        }
    }
    public static void w(String tag, String message) {
        if (debug) {
            Log.w(tag, "i: "+message);
        }
    }
    public static void e(String tag, String message,Throwable throwable) {
        if (debug) {
            Log.e(tag, "i: "+message, throwable);
        }
    }
}
