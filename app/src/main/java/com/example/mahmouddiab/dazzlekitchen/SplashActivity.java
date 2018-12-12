package com.example.mahmouddiab.dazzlekitchen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.mahmouddiab.dazzlekitchen.authentication.AuthenticationActivity;
import com.example.mahmouddiab.dazzlekitchen.home.WaiterUserActivity;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.utils.PrefsManager;

public class SplashActivity extends AppCompatActivity {

    private String notificationType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        App.changeLang(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra("type"))) {
            notificationType = getIntent().getStringExtra("type");
        } else {
            notificationType = "2";
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (UserManager.getInstance().getUser() == null && !PrefsManager.getInstance().isUserSkip()) {
                    Intent intent = new Intent(SplashActivity.this, AuthenticationActivity.class);
                    startActivity(intent);
                } else {
                    if (UserManager.getInstance().getUser().getData().getRole().equalsIgnoreCase("ROLE_WAITER")) {

                        WaiterUserActivity.start(SplashActivity.this, notificationType);
                    } else {
                        MainActivity.start(SplashActivity.this);
                    }
                }
                finish();
            }
        }, 2000);
    }
}
