package com.example.mahmouddiab.dazzlekitchen.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("errorData")
    @Expose
    private Object errorData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }

}