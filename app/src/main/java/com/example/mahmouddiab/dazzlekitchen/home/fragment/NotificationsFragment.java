package com.example.mahmouddiab.dazzlekitchen.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahmouddiab.dazzlekitchen.R;
import com.example.mahmouddiab.dazzlekitchen.adapter.NotificationAdapter;
import com.example.mahmouddiab.dazzlekitchen.home.notificationPresenter.NotificationPresenter;
import com.example.mahmouddiab.dazzlekitchen.home.notificationPresenter.NotificationPresnterImpl;
import com.example.mahmouddiab.dazzlekitchen.home.notificationPresenter.NotificationView;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.NotificationData;
import com.example.mahmouddiab.dazzlekitchen.model.NotificationModel;
import com.example.mahmouddiab.dazzlekitchen.userManegment.UserManager;
import com.example.mahmouddiab.dazzlekitchen.utils.Utils;
import com.example.mahmouddiab.dazzlekitchen.utils.WrapContentLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment implements NotificationView {
    @BindView(R.id.empty_text)
    TextView emptyText;
    private NotificationAdapter notificationAdapter;
    private List<NotificationData> notificationModel;
    private NotificationPresenter presenter;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    final Handler handler = new Handler();

    public static NotificationsFragment getInstance() {
        return new NotificationsFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.notificationModel = new ArrayList();
        this.presenter = new NotificationPresnterImpl(this);
        this.notificationAdapter = new NotificationAdapter(getContext(), this.notificationModel);
        this.recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(this.notificationAdapter);
        this.swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Utils.isNetworkAvailable(getContext()))
                    presenter.onGetNotifications(UserManager.getInstance().getUser().getData().getToken());
                else {
                    Utils.showLongToast(getContext(), getString(R.string.no_net));
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            if (Utils.isNetworkAvailable(getContext()))
                                presenter.onGetNotifications(UserManager.getInstance().getUser().getData().getToken());
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 10000);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onGetNotifications(UserManager.getInstance().getUser().getData().getToken());
    }

    @Override
    public void onGetNotificationSuccess(NotificationModel notificationModel) {
        this.notificationModel.clear();
        this.notificationModel.addAll(notificationModel.getData());
        this.swipeRefreshLayout.setRefreshing(false);
        if (this.notificationModel.isEmpty()) {
            this.emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_active_orders));
        } else {
            this.emptyText.setVisibility(View.GONE);
        }
        this.notificationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetNotificationError(Error error) {
        this.emptyText.setVisibility(View.VISIBLE);
        emptyText.setText(getString(R.string.no_net));
    }
}
