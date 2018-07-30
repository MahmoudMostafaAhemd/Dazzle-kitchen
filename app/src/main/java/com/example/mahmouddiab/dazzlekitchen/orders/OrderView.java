package com.example.mahmouddiab.dazzlekitchen.orders;

import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;

public interface OrderView {

    void onGetOrderSuccess(OrdersModel ordersModel);

    void onDoneSuccess(OrderDoneModel orderDoneModel);

    void onInProgressSuccess(OrderDoneModel orderDoneModel);

    void onCloseSuccess(OrderDoneModel orderDoneModel);

    void onGetOrderFailed(Error error);
}
