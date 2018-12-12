package com.example.mahmouddiab.dazzlekitchen.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

public class Utils {

    private static final int DAY_MILLIS = 86400000;
    private static final int HOUR_MILLIS = 3600000;
    private static final int MINUTE_MILLIS = 60000;
    private static final int SECOND_MILLIS = 1000;

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            time *= 1000;
        }
        long now = System.currentTimeMillis();
        if (time <= now) {
            if (time > 0) {
                long diff = now - time;
                if (diff < 60000) {
                    return "just now";
                }
                if (diff < 120000) {
                    return "a minute ago";
                }
                StringBuilder stringBuilder;
                if (diff < 3000000) {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(diff / 60000);
                    stringBuilder.append(" minutes ago");
                    return stringBuilder.toString();
                } else if (diff < 5400000) {
                    return "an hour ago";
                } else {
                    if (diff < 86400000) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(diff / 3600000);
                        stringBuilder2.append(" hours ago");
                        return stringBuilder2.toString();
                    } else if (diff < 172800000) {
                        return "yesterday";
                    } else {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(diff / 86400000);
                        stringBuilder.append(" days ago");
                        return stringBuilder.toString();
                    }
                }
            }
        }
        return null;
    }
    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void showLongToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }


}
