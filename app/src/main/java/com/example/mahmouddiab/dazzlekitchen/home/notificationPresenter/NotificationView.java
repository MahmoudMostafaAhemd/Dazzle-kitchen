package com.example.mahmouddiab.dazzlekitchen.home.notificationPresenter;

import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.NotificationModel;

public interface NotificationView {
    void onGetNotificationError(Error error);

    void onGetNotificationSuccess(NotificationModel notificationModel);
}
