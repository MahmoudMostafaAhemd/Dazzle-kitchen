package com.example.mahmouddiab.dazzlekitchen.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.text.TextUtils.isEmpty;

/**
 * Created by mahmoud.diab on 5/27/2018.
 */

public class PrefsManager {

    private static final String PREFS_NAME = "training_app_prefs";

    private static PrefsManager instance;
    private SharedPreferences preferences;

    private PrefsManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }


    public static void init(Context context) {
        if (instance == null) {
            synchronized (PrefsManager.class) {
                instance = new PrefsManager(context.getApplicationContext());
            }
        }
    }

    public static PrefsManager getInstance() {
        return instance;
    }


    public void setLang(String lang) {
        addString("APP_LANG", lang);
    }


    public String getLang() {
        return getString("APP_LANG", "en");
    }

    private void addString(String key, String value) {
        if (preferences == null) return;
        if (isEmpty(key)) return;
        preferences.edit().putString(key, value).apply();
    }

    private String getString(String key, String defaultVal) {
        if (preferences == null) return defaultVal;
        if (isEmpty(key)) return defaultVal;
        return preferences.getString(key, defaultVal);
    }

    private void addBoolean(String key, boolean val) {
        if (preferences == null) return;
        if (isEmpty(key)) return;
        preferences.edit().putBoolean(key, val).apply();
    }

    private boolean getBoolean(String key, boolean defaultVal) {
        if (preferences == null) return defaultVal;
        if (isEmpty(key)) return defaultVal;
        return preferences.getBoolean(key, defaultVal);
    }

    public boolean isUserSkip() {
        return getBoolean("isSkip", false);
    }

    public void setIsUserSkip(boolean b) {
        addBoolean("isSkip", b);
    }

    public String getRunningOrderId() {
        return getString("isRunning", null);
    }

    public void setIsRunningOrder(String id) {
        addString("isRunning", id);
    }
}
