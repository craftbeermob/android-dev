package com.example.craftbeermob;

/**
 * Created by ret70 on 6/10/2016.
 */
public class GeofenceCoords {

    private double hideoutLat;
    private double hideoutLon;
    private String hideoutID;

    protected double getHideoutLat() {
        return hideoutLat;
    }

    protected void setHideoutLat(double hideoutLat) {
        this.hideoutLat = hideoutLat;
    }

    protected double getHideoutLon() {
        return hideoutLon;
    }

    protected void setHideoutLon(double hideoutLon) {
        this.hideoutLon = hideoutLon;
    }

    protected String getHideoutID() {
        return hideoutID;
    }

    protected void setHideoutID(String hideoutID) {
        this.hideoutID = hideoutID;
    }
}
