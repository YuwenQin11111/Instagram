package com.example.android.threadsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class Pref {

    //////////////////// PUBLIC PREF KEYS ////////////////////
	public static final String NEXTURL = "com.example.android.threadsample.nextpaginationurl";

    /** Get an instance of the SharedPreferences for RadioApp */
    private static SharedPreferences getPref(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** Get an instance of the preference editor for RadioApp */
    private static Editor getEdit(Context context) {
        return getPref(context).edit();
    }

    //////////////////// PUBLIC ////////////////////

    /** Returns true if the key (arg1) is contained in SharedPreferences for this app */
    public static boolean contains(Context context, String key) {
        return getPref(context).contains(key);
    }

    /** Clear ALL key/values from the the RadioApp SharedPreferences, returns true if successful */
    public static boolean clear(Context context) {
        return getEdit(context).clear().commit();
    }

    /**
     * Preference value accessors
     *
     * @param1 Preference key to retrieve
     * @param2 Default value to return (if the key does not exist)
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getPref(context).getBoolean(key, defaultValue);
    }
    public static int getInt(Context context, String key, int defaultValue) {
        return getPref(context).getInt(key, defaultValue);
    }
    public static long getLong(Context context, String key, long defaultValue) {
        return getPref(context).getLong(key, defaultValue);
    }
    public static float getFloat(Context context, String key, float defaultValue) {
        return getPref(context).getFloat(key, defaultValue);
    }
    public static String getString(Context context, String key, String defaultValue) {
        return getPref(context).getString(key, defaultValue);
    }

    /**
     * Save a key (arg1) with a value (arg2) to SharedPreferences and immediately commit to disk
     *
     * @param1 Preference key string to add/modify
     * @param2 Value to insert/replace
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        return getEdit(context).putBoolean(key, value).commit();
    }

    public static boolean putInt(Context context, String key, int value) {
        return getEdit(context).putInt(key, value).commit();
    }

    public static boolean putLong(Context context, String key, long value) {
        return getEdit(context).putLong(key, value).commit();
    }

    public static boolean putFloat(Context context, String key, float value) {
        return getEdit(context).putFloat(key, value).commit();
    }

    public static boolean putString(Context context, String key, String value) {
        return getEdit(context).putString(key, value).commit();
    }
}
