package com.example.mahmouddiab.dazzlekitchen.api;

import com.example.mahmouddiab.dazzlekitchen.model.OrderDoneModel;
import com.example.mahmouddiab.dazzlekitchen.model.OrdersModel;
import com.example.mahmouddiab.dazzlekitchen.model.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiClient {
    String baseUrl = "http://35.171.211.169";

    @FormUrlEncoded
    @POST("/api/login")
    Call<UserModel> login(@Field("username") String username,
                          @Field("password") String password);

    @GET("/api/kitchen/orders")
    Call<OrdersModel> getOrders(@Header("Authorization") String authorization);


    @POST("/api/kitchen/order-done/{id}")
    Call<OrderDoneModel> submitDoneOrder(@Header("Authorization") String authorization
            , @Path("id") int id);

    @POST("/api/kitchen/close-order/{id}")
    Call<OrderDoneModel> submitCloseOrder(@Header("Authorization") String authorization
            , @Path("id") int id);

    @POST("/api/kitchen/order-in-progress/{id}")
    Call<OrderDoneModel> submitInProgressOrder(@Header("Authorization") String authorization
            , @Path("id") int id);

//    @GET("/api/kitchen/waiter-calls")
//    Call<> waiterCalls(@Header("Authorization") String authorization);
}
