package com.example.mahmouddiab.dazzlekitchen.orders;

import com.example.mahmouddiab.dazzlekitchen.api.ApiFactory;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPresenterImpl implements OrderPresenter{

    private OrderView orderView;

    public OrdersPresenterImpl(OrderView orderView){
        this.orderView = orderView;
    }



    @Override
    public void getOrders(String authorization) {
        ApiFactory.createInstance().getOrders(authorization).enqueue(new Callback<OrdersModel>() {
            @Override
            public void onResponse(Call<OrdersModel> call, Response<OrdersModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().getError() == null){
                        orderView.onGetOrderSuccess(response.body());
                    }else {
                        orderView.onGetOrderFailed(response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrdersModel> call, Throwable t) {
                Error error = new Error();
                error.setCode(0);
                error.setTitle(t.getLocalizedMessage());
                orderView.onGetOrderFailed(error);
            }
        });
    }

    @Override
    public void onDoneClicked(String authorization, int id) {
        ApiFactory.createInstance().submitDoneOrder(authorization,id).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().getError() == null){
                        orderView.onDoneSuccess(response.body());
                    }else {
                        orderView.onGetOrderFailed(response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDoneModel> call, Throwable t) {
                Error error = new Error();
                error.setCode(0);
                error.setTitle(t.getLocalizedMessage());
                orderView.onGetOrderFailed(error);
            }
        });
    }

    @Override
    public void onInProgressClicked(String authorization, int id) {
        ApiFactory.createInstance().submitInProgressOrder(authorization,id).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().getError() == null){
                        orderView.onInProgressSuccess(response.body());
                    }else {
                        orderView.onGetOrderFailed(response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDoneModel> call, Throwable t) {
                Error error = new Error();
                error.setCode(0);
                error.setTitle(t.getLocalizedMessage());
                orderView.onGetOrderFailed(error);
            }
        });
    }

    @Override
    public void onCloseClicked(String authorization, int id) {
        ApiFactory.createInstance().submitCloseOrder(authorization,id).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().getError() == null){
                        orderView.onCloseSuccess(response.body());
                    }else {
                        orderView.onGetOrderFailed(response.body().getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDoneModel> call, Throwable t) {

            }
        });
    }
}
