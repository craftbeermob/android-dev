package com.example.craftbeermob.Activities;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.craftbeermob.Models.Hideouts;
import com.example.craftbeermob.Models.Users;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/9/2016.
 */
public class App extends Application {
    private static ArrayList<Hideouts> hideoutList;
    static private Users users;
    final int GEOFENCE_RADIUS_IN_METERS = 100;
    //global class which holds objects through the lifecycle of the application
    private Bitmap beerPhoto;

    public static Users getUsers() {
        return users;
    }

    public static void setUsers(Users users) {
        users = users;
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
