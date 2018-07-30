package com.example.mahmouddiab.dazzlekitchen.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("table")
    @Expose
    private String table;
    @SerializedName("isDone")
    @Expose
    private Boolean isDone;
    @SerializedName("isArrived")
    @Expose
    private Boolean isArrived;
    @SerializedName("canSetArrived")
    @Expose
    private Boolean canSetArrived;
    @SerializedName("canCloseOrder")
    @Expose
    private Boolean canCloseOrder;
    @SerializedName("canSetWaitingTime")
    @Expose
    private Boolean canSetWaitingTime;
    @SerializedName("dishes")
    @Expose
    private List<Dish> dishes = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("expectedArrivalMinutes")
    @Expose
    private Integer expectedArrivalMinutes;
    @SerializedName("status")
    @Expose
    private String status;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Boolean getIsArrived() {
        return isArrived;
    }

    public void setIsArrived(Boolean isArrived) {
        this.isArrived = isArrived;
    }

    public Boolean getCanSetArrived() {
        return canSetArrived;
    }

    public void setCanSetArrived(Boolean canSetArrived) {
        this.canSetArrived = canSetArrived;
    }

    public Boolean getCanCloseOrder() {
        return canCloseOrder;
    }

    public void setCanCloseOrder(Boolean canCloseOrder) {
        this.canCloseOrder = canCloseOrder;
    }

    public Boolean getCanSetWaitingTime() {
        return canSetWaitingTime;
    }

    public void setCanSetWaitingTime(Boolean canSetWaitingTime) {
        this.canSetWaitingTime = canSetWaitingTime;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getExpectedArrivalMinutes() {
        return expectedArrivalMinutes;
    }

    public void setExpectedArrivalMinutes(Integer expectedArrivalMinutes) {
        this.expectedArrivalMinutes = expectedArrivalMinutes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
