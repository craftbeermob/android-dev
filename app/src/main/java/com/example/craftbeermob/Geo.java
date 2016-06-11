package com.example.craftbeermob;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/**
 * Created by ret70 on 6/10/2016.
 */
public  class Geo extends Activity implements ResultCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    PendingIntent mGeofencePendingIntent;
    public static ArrayList<Geofence> mGeofenceList;
    final int GEOFENCE_RADIUS_IN_METERS=100;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    public Geo()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,GeofenceTransitionsIntentService.class));
        mGeofenceList = new ArrayList<>();
        buildGoogleApiClient();

        //TODO:Gget list of geofences

        GeofenceCoords coords = new GeofenceCoords();
        coords.setHideoutLat(40.6718323);
        coords.setHideoutLon(-111.8654755);
        coords.setHideoutID("test");
        setmGeofenceList(coords);


    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(getmGeofenceList());
        return builder.build();
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


    public static ArrayList<Geofence> getmGeofenceList() {
        return mGeofenceList;
    }
    public  void setmGeofenceList(GeofenceCoords geoObj)
    {
        mGeofenceList.add(new Geofence.Builder()
                // Set the request ID of the geofence. This is a string to identify this
                // geofence.
                .setRequestId("test")

                .setCircularRegion(
                        geoObj.getHideoutLat(),
                        geoObj.getHideoutLon(),
                        GEOFENCE_RADIUS_IN_METERS
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    public void addGeoToLocationService()
    {

        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent()
        ).setResultCallback(this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d("test",Double.toString(location.getLatitude()));



    }

    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onResult(@NonNull Result result) {
        Log.d("test",result.getStatus().toString());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        addGeoToLocationService();
//        mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(1);
//        mLocationRequest.setFastestInterval(1);
//        mLocationRequest.setNumUpdates(1);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
