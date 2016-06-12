package com.example.craftbeermob;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/10/2016.
 */
public class Geo extends Activity implements ResultCallback, ILocationAware {

    PendingIntent mGeofencePendingIntent;
    public static ArrayList<Geofence> mGeofenceList;
    final int GEOFENCE_RADIUS_IN_METERS = 100;
    GoogleApiClient mGoogleApiClient;
    boolean mBound;
    LocationService mService;

    public Geo() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, GeofenceTransitionsIntentService.class));
        mGeofenceList = new ArrayList<>();


        //TODO:Gget list of geofences


    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);
        return builder.build();
    }

    public void setGeofenceList(ArrayList<Hideouts> hideoutArrayList) {
        for (Hideouts hideout : hideoutArrayList) {

            mGeofenceList.add((Geofence) new Geofence.Builder()
                    // Set the request ID of the geofence. This is a string to identify this
                    // geofence.
                    .setRequestId("test")

                    .setCircularRegion(
                           hideout.getLatitude(),
                           hideout.getLatitude(),
                            GEOFENCE_RADIUS_IN_METERS
                    )
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                            Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build());
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }


    public void addGeoToLocationService() {

        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);

    }
    private ServiceConnection mConnection = new ServiceConnection() {

        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Because we have bound to an explicit
            // service that is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e("Ex_Binding", "onServiceDisconnected");
            mBound = false;
        }
    };


    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        mService.unbindService(mConnection);
        if(mGoogleApiClient!=null) {
            mGoogleApiClient.disconnect();

        }
        super.onStop();
    }


    @Override
    public void onResult(@NonNull Result result) {
        Log.d("test", result.getStatus().toString());
    }


    @Override
    public void CurrentLocation(LatLng latLng) {

    }

    @Override
    public void ClientConnected(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
        setGeofenceList(App.getHideouts());
        addGeoToLocationService();
    }
}
