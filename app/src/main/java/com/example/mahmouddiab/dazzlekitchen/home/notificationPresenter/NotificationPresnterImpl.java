package com.example.mahmouddiab.dazzlekitchen.home.notificationPresenter;

import com.example.mahmouddiab.dazzlekitchen.api.ApiFactory;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationPresnterImpl implements NotificationPresenter {
    private NotificationView view;

    public NotificationPresnterImpl(NotificationView notificationView) {
        this.view = notificationView;
    }

    public void onGetNotifications(String auth) {
        ApiFactory.createInstance().getWaiterCalls(auth).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getError() == null) {
                        NotificationPresnterImpl.this.view.onGetNotificationSuccess(response.body());
                    } else {
                        NotificationPresnterImpl.this.view.onGetNotificationError((response.body()).getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationModel> call, Throwable t) {
                Error error = new Error();
                error.setCode(0);
                error.setTitle(t.getLocalizedMessage());
                NotificationPresnterImpl.this.view.onGetNotificationError(error);
            }
        });
    }
}
