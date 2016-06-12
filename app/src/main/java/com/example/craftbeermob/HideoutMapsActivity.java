package com.example.craftbeermob;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class HideoutMapsActivity extends AppCompatActivity implements OnMapReadyCallback, ILocationAware, IList {

    private GoogleMap mMap;
    boolean mBound;
    LocationService mService;
    LatLng currentLocation;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mapstoolbar);
        toolbar.setTitle("Choose a hideout");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_reply_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, this.BIND_AUTO_CREATE);
    }


    private ServiceConnection mConnection = new ServiceConnection() {

        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Because we have bound to an explicit
            // service that is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            mService = binder.getService();
            mService.setCallBack(HideoutMapsActivity.this);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e("Ex_Binding", "onServiceDisconnected");
            mBound = false;
        }
    };


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Hideouts hideout : App.getHideouts()) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(hideout.getLatitude(), hideout.getLongitude())).title(hideout.getRequestId()));
        }

       mMap.moveCamera(CameraUpdateFactory.zoomTo(14));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        mService.unbindService(mConnection);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();

        }
        super.onStop();
    }

    @Override
    public void CurrentLocation(LatLng latLng) {
        currentLocation = latLng;
        new BaseQuery<>(this, Hideouts.class).getAll(this);
    }

    @Override
    public void ClientConnected(GoogleApiClient googleApiClient) {
        mGoogleApiClient = googleApiClient;
    }

    @Override
    public void setList(List<Object> objects) {
        ArrayList<Hideouts> temp = new ArrayList<>();
        for (Object obj : objects) {
            temp.add((Hideouts) obj);
        }
        App.setHideoutList(temp);
        setupMap();
    }

    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public List<Object> getList() {
        return null;
    }
}
