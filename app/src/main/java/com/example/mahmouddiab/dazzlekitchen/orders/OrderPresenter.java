package com.example.mahmouddiab.dazzlekitchen.orders;

public interface OrderPresenter {
    void getOrders(String str);

    void onCloseClicked(String str, int i);

    void onDoneClicked(String str, int i);

    void onInProgressClicked(String str, int i);

    void onRegisterToken(String str, String str2, String str3);

    void onSubmitWaitingTime(String str, int i, String str2);
}
