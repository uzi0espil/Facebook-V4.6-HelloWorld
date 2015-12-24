package com.v4.facebook.tutorial.facebookv4helloworld;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Osama on 12/24/2015.
 */
public class PreferencesUtils  {

    // string
    public static void writePreferenceValue(Context context, String prefsKey, String prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putString(prefsKey, prefsValue);
        editor.commit();
    }

    // int
    public static void writePreferenceValue(Context context, String prefsKey, int prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putInt(prefsKey, prefsValue);
        editor.commit();
    }

    // boolean
    public static void writePreferenceValue(Context context, String prefsKey, boolean prefsValue) {
        SharedPreferences.Editor editor = getPrefsEditor(context);
        editor.putBoolean(prefsKey, prefsValue);
        editor.commit();
    }

    private static SharedPreferences.Editor getPrefsEditor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.edit();
    }

}
