package com.example.mahmouddiab.dazzlekitchen.orders;

public interface OrderPresenter {

    void getOrders(String authorization);

    void onDoneClicked(String authorization, int id);

    void onInProgressClicked(String authorization, int id);

    void onCloseClicked(String authorization, int id);

}
