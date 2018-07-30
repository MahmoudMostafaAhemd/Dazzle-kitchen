package com.example.mahmouddiab.dazzlekitchen.home;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.adapter.PagerAdapter;
import com.example.mahmouddiab.dazzlekitchen.authentication.AuthenticationActivity;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WaiterUserActivity extends AppCompatActivity {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter_user);
        ButterKnife.bind(this);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, WaiterUserActivity.class);
        context.startActivity(starter);
    }

    @OnClick(R.id.logout)
    void onLogoutClicked() {
        UserManager.getInstance().logout();
        AuthenticationActivity.start(this);
        finish();
    }
}
