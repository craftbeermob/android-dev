package com.example.craftbeermob.Activities;

import android.app.Application;
import android.content.Context;

import com.example.craftbeermob.Models.Users;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

import java.net.MalformedURLException;

/**
 * Created by ret70 on 6/9/2016.
 */
public class App extends Application {

    //global class which holds objects through the lifecycle of the application
    private static String profileUri;

    private static MobileServiceClient mobileServiceClient;

    private static Users user;

    public static MobileServiceClient getMobileClientSingleton(Context con) {
        if (mobileServiceClient == null) {
            try {
                return mobileServiceClient = new MobileServiceClient("https://craftbeermob.azurewebsites.net", con);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            mobileServiceClient.setContext(con);
            return mobileServiceClient;
        }

        return null;
    }

    public static Users GetUserSingleton() {
        if (user == null) {
            return user = new Users();
        } else {
            return user;
        }
    }

    public static String getProfileUri() {
        return profileUri;
    }

    public static void setProfileUri(String setProfileUri) {
        profileUri = setProfileUri;
    }
}
