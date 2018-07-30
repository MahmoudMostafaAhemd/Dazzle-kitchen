package com.example.mahmouddiab.dazzlekitchen.orders;

import com.example.mahmouddiab.dazzlekitchen.api.ApiFactory;
import com.example.mahmouddiab.dazzlekitchen.model.Error;
import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersPresenterImpl implements OrderPresenter {
    private OrderView orderView;

    public OrdersPresenterImpl(OrderView orderView) {
        this.orderView = orderView;
    }

    public void getOrders(String authorization) {
        ApiFactory.createInstance().getOrders(authorization).enqueue(new Callback<OrdersModel>() {
            @Override
            public void onResponse(Call<OrdersModel> call, Response<OrdersModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((response.body()).getError() == null) {
                        orderView.onGetOrderSuccess(response.body());
                    } else {
                        orderView.onGetOrderFailed((response.body()).getError());
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

    public void onDoneClicked(String authorization, int id) {
        ApiFactory.createInstance().submitDoneOrder(authorization, id).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((response.body()).getError() == null) {
                        orderView.onDoneSuccess(response.body());
                    } else {
                        orderView.onGetOrderFailed((response.body()).getError());
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

    public void onInProgressClicked(String authorization, int id) {
        ApiFactory.createInstance().submitInProgressOrder(authorization, id).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((response.body()).getError() == null) {
                        orderView.onInProgressSuccess(response.body());
                    } else {
                        orderView.onGetOrderFailed((response.body()).getError());
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

    public void onCloseClicked(String authorization, int id) {
        ApiFactory.createInstance().submitCloseOrder(authorization, id).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((response.body()).getError() == null) {
                        orderView.onCloseSuccess(response.body());
                    } else {
                        orderView.onGetOrderFailed((response.body()).getError());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDoneModel> call, Throwable t) {
            }
        });
    }

    public void onRegisterToken(String authorization, String id, String token) {
        ApiFactory.createInstance().registerToken(authorization, id, token).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if ((response.body()).getError() == null) {
                        orderView.onTakenRigestered(response.body());
                    } else {
                        orderView.onGetOrderFailed((response.body()).getError());
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

    public void onSubmitWaitingTime(String authorization, int id, String minutes) {
        ApiFactory.createInstance().updateOrderWaitingTime(authorization, id, (int)Double.parseDouble(minutes)).enqueue(new Callback<OrderDoneModel>() {
            @Override
            public void onResponse(Call<OrderDoneModel> call, Response<OrderDoneModel> response) {
                if (response.isSuccessful() && response.body() != null && (response.body()).getError() != null) {
                    orderView.onGetOrderFailed((response.body()).getError());
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
}
