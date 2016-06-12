package com.example.craftbeermob;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private GoogleApiClient mGoogleApiClient;
    ILocationAware callbackClient;
    LocationRequest mLocationRequest;


    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        buildGoogleApiClient();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocationService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocationService.this;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        startLocationUpdates();
        callbackClient.ClientConnected(mGoogleApiClient);
    }

    public void setCallBack(ILocationAware locationAware) {
        this.callbackClient = locationAware;
    }

    void startLocationUpdates() {
        if (mGoogleApiClient != null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1);
            mLocationRequest.setFastestInterval(1);
            mLocationRequest.setNumUpdates(1);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Googleconnectionfailed", Integer.toString(i));
    }

    PreferenceManager.OnActivityStopListener onActivityStopListener = new PreferenceManager.OnActivityStopListener() {
        @Override
        public void onActivityStop() {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, LocationService.this);
        }
    };

    @Override
    public void onLocationChanged(Location location) {
        callbackClient.CurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Googleconnectionfailed", connectionResult.toString());
    }
}
