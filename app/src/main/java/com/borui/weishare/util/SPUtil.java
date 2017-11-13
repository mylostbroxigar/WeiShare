package com.borui.weishare.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by borui on 2017/9/5.
 */

public class SPUtil {

    private static final String SP_NAME="weshare";

    public static final String KEY_USERNAME="key_username";
    public static final String KEY_PASSWORD="key_password";
    public static final String KEY_LOGINED="key_logined";
    public static final String KEY_LATITUDE="key_latitude";
    public static final String KEY_LONGITUDE="key_longitude";
    public static final String KEY_CITY="key_city";

    public static void insertInt(Context context, String key, int value){
        SharedPreferences sp=context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        int value=sp.getInt(key,0);
        return value;
    }

    public static void insertString(Context context, String key, String value){
        SharedPreferences sp=context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        String value=sp.getString(key,"");
        return value;
    }

    public static void insertBoolean(Context context, String key, boolean value){
        SharedPreferences sp=context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String key){
        SharedPreferences sp=context.getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        boolean value=sp.getBoolean(key,false);
        return value;
    }
}
