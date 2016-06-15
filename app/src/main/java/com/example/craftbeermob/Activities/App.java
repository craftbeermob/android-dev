package com.example.craftbeermob.Activities;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.craftbeermob.Models.Hideouts;
import com.example.craftbeermob.Models.User;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/9/2016.
 */
public class App extends Application {
    private static ArrayList<Hideouts> hideoutList;
    static private User user;
    final int GEOFENCE_RADIUS_IN_METERS = 100;
    //global class which holds objects through the lifecycle of the application
    private Bitmap beerPhoto;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        user = user;
    }

    public Bitmap getBeerPhoto() {
        return beerPhoto;
    }

    public void setBeerPhoto(Bitmap beerPhoto) {
        if (beerPhoto != null) {
            //release bitmap resources
            beerPhoto.recycle();
            this.beerPhoto = beerPhoto;
        }

    }
}
