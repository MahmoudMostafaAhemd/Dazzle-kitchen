package com.example.mahmouddiab.dazzlekitchen.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.home.fragment.NotificationsFragment;
import com.example.mahmouddiab.dazzlekitchen.home.fragment.OrdersFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OrdersFragment.getInstance();
            default:
                return NotificationsFragment.getInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.orders);
            default:
                return context.getString(R.string.Notification);
        }
    }
}
