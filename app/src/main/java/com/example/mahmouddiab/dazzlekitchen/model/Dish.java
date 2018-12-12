package com.example.mahmouddiab.dazzlekitchen.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Dish {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("additions")
    @Expose
    private List<AdditionsAndRemovals> additions = null;
    @SerializedName("removals")
    @Expose
    private List<AdditionsAndRemovals> removals = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getIsDone() {
        return status;
    }

    public void setIsDone(String isDone) {
        this.status = isDone;
    }
    public List<AdditionsAndRemovals> getAdditions() {
        return additions;
    }

    public void setAdditions(List<AdditionsAndRemovals> additions) {
        this.additions = additions;
    }

    public List<AdditionsAndRemovals> getRemovals() {
        return removals;
    }

    public void setRemovals(List<AdditionsAndRemovals> removals) {
        this.removals = removals;
    }
}
