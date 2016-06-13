package com.example.craftbeermob.Models;

import com.example.craftbeermob.Interfaces.IObject;

/**
 * Created by ret70 on 6/11/2016.
 */
public class Hideouts implements IObject {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("Latitude")
    private String Latitude;

    @com.google.gson.annotations.SerializedName("Longitude")
    private String Longitude;

    @com.google.gson.annotations.SerializedName("RequestId")
    private String RequestId;

    public Double getLatitude() {
        return Double.parseDouble(Latitude);
    }

    public void setLatitude(Double latitude) {
        Latitude = Double.toString(latitude);
    }

    public Double getLongitude() {
        return Double.parseDouble(Longitude);
    }

    public void setLongitude(Double longitude) {
        Longitude =Double.toString(longitude);
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    @Override
    public Object returnObj() {
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
