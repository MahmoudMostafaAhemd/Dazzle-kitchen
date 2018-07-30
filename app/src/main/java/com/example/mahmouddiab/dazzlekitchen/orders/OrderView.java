package com.example.mahmouddiab.dazzlekitchen.orders;

import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;

public interface OrderView {
    void onCloseSuccess(OrderDoneModel orderDoneModel);

    void onDoneSuccess(OrderDoneModel orderDoneModel);

    void onGetOrderFailed(Error error);

    void onGetOrderSuccess(OrdersModel ordersModel);

    void onInProgressSuccess(OrderDoneModel orderDoneModel);

    void onTakenRigestered(OrderDoneModel orderDoneModel);
}
