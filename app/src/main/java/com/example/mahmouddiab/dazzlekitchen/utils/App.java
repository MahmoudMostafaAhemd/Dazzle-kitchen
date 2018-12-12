package com.example.mahmouddiab.dazzlekitchen.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.Locale;

import io.paperdb.Paper;

/**
 * Created by mahmoud.diab on 4/18/2018.
 */

public class App extends Application {

    private static String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Paper.init(this);
        PrefsManager.init(this);
        userId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getClientId() {
        return userId;
    }

    public static void changeLang(Context context) {
        Locale myLocale;

        if (PrefsManager.getInstance().getLang() != null) {
            myLocale = new Locale(PrefsManager.getInstance().getLang());
        } else {
            myLocale = new Locale(Locale.getDefault().getLanguage());
        }

        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeLang(this);
    }
}
