package com.example.mahmouddiab.dazzlekitchen.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDoneModel {

    @SerializedName("data")
    @Expose
    private OrderData data;
    @SerializedName("error")
    @Expose
    private Error error;

    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

}