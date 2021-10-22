package com.integration.project.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Wongerfeng on 2020/8/10.
 */
public class SPUtil {

    private static final String TOKEN = "token";
    private static final String ID = "employeeid";
    private static final String NAME = "name";
    private static final String STATUS = "status";



    public static String shareToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        String token = preferences.getString(TOKEN, "");
        return token;
    }

    public static void saveToken(Context context, String token) {
        context.getSharedPreferences(TOKEN, Context.MODE_PRIVATE).edit().putString(TOKEN,token).apply();
    }


    public static void saveEmployeeId(Context context, Integer employeeId) {
        context.getSharedPreferences(ID, Context.MODE_PRIVATE).edit().putInt(ID, employeeId).apply();
    }


    public static void saveName(Context context, String name) {
        context.getSharedPreferences(NAME, Context.MODE_PRIVATE).edit().putString(NAME, name).apply();
    }

    public static String shareUserName(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE).getString(NAME, "员工");
    }

    public static void saveUserId(Context context, Integer id) {
        context.getSharedPreferences(ID, Context.MODE_PRIVATE).edit().putInt(ID, id).apply();
    }


    public static Integer shareUserId(Context context) {
        return context.getSharedPreferences(ID, Context.MODE_PRIVATE).getInt(ID, 0);
    }

    public static void saveUserStatus(Context context, Integer status) {
        context.getSharedPreferences(STATUS, Context.MODE_PRIVATE).edit().putInt(STATUS, status).apply();
    }

    public static Integer shareUserStatus(Context context) {
        return context.getSharedPreferences(STATUS, Context.MODE_PRIVATE).getInt(STATUS, 0);
    }
}
