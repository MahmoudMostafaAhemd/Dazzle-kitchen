package com.example.mahmouddiab.dazzlekitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class NotificationModel {
    @SerializedName("data")
    @Expose
    private List<NotificationData> data = null;
    @SerializedName("error")
    @Expose
    private Error error;

    public List<NotificationData> getData() {
        return this.data;
    }

    public void setData(List<NotificationData> data) {
        this.data = data;
    }

    public Error getError() {
        return this.error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
