package com.example.craftbeermob.LocationClasses;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.example.craftbeermob.Activities.BeerPicker;
import com.example.craftbeermob.Classes.BaseQuery;
import com.example.craftbeermob.Classes.Constants;
import com.example.craftbeermob.Classes.GeofenceErrorMessages;
import com.example.craftbeermob.Classes.StoreBlob;
import com.example.craftbeermob.Interfaces.IList;
import com.example.craftbeermob.Interfaces.ILocationAware;
import com.example.craftbeermob.Models.Hideouts;
import com.example.craftbeermob.Models.RecentActivity;
import com.example.craftbeermob.R;
import com.example.craftbeermob.Services.GeofenceTransitionsIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ret70 on 6/10/2016.
 */
public class GeoMain extends FragmentActivity implements ResultCallback<Status>, ILocationAware,
        IList, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    protected static final String TAG = "GeoMain";
    public static int GeoMain_RequestCode = 102;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * The list of geofences used in this sample.
     */
    protected ArrayList<Geofence> mGeofenceList;
    Bitmap mUserPhoto;

    LocationService mService;
    boolean isWithinHideout;
    BroadcastReceiver Receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Constants.TransitionEntered) {
                isWithinHideout = true;

                //init barcode

//                IntentIntegrator intentIntegrator = new IntentIntegrator(GeoMain.this);
//                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
//                intentIntegrator.setPrompt("Ask a bartender for the barcode!");
//                intentIntegrator.setCameraId(-1);
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setBarcodeImageEnabled(false);
//                intentIntegrator.initiateScan();


            }
        }
    };
    private ArrayList<Object> HideoutObjects;
    /**
     * Used to keep track of whether geofences were added.
     */
    private boolean mGeofencesAdded;
    /**
     * Used when requesting to add or remove geofences.
     */
    private PendingIntent mGeofencePendingIntent;
    private ServiceConnection mConnection = new ServiceConnection() {

        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            // Because we have bound to an explicit
            // service that is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
            LocationService.LocalBinder binder = (LocationService.LocalBinder) service;
            mService = binder.getService();
            mService.setCallBack(GeoMain.this);

        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e("Ex_Binding", "onServiceDisconnected");
        }
    };

    public GeoMain() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geomain);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS disabled please check settings", Toast.LENGTH_LONG);
            setResult(RESULT_CANCELED);
            finishActivity(GeoMain_RequestCode);
        }

        //get user photo
        Intent photoIntent = getIntent();
        mUserPhoto = photoIntent.getParcelableExtra("userphoto");


        // Empty list for storing geofences.
        mGeofenceList = new ArrayList<Geofence>();

        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;

        //bind to service
        Intent intent = new Intent(this, LocationService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        //load hideouts
        new BaseQuery<>(this, Hideouts.class).getAll(this);

        //register receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(Receiver, new IntentFilter(Constants.TransitionEntered));
        Intent beerPickerIntent = new Intent(this, BeerPicker.class);
        beerPickerIntent.putExtra("beerphoto", mUserPhoto);

        startActivityForResult(beerPickerIntent, BeerPicker.BEERPICKER_REQUEST_CODE);

    }

    @Override
    public void CurrentLocation(LatLng latLng) {
//        Hideouts hide = new Hideouts();
//        hide.setLatitude(latLng.latitude);
//        hide.setLongitude(latLng.longitude);
//        hide.setRequestId("Boobies");
//        new BaseQuery<>(this,Hideouts.class).addItem(hide);
    }

    @Override
    public void ClientConnected(GoogleApiClient googleApiClient) {

        mGoogleApiClient = googleApiClient;

        Log.i(TAG, "Connected to GoogleApiClient");

        checkListset_ClientConnected();
    }

    private void checkListset_ClientConnected() {
        if (mGoogleApiClient != null && HideoutObjects != null) {
            if (HideoutObjects.size() > 0 && mGoogleApiClient.isConnected()) {
                populateGeofenceList(HideoutObjects);
                addGeofences();
            }
        }
    }

    //set hideout objects
    @Override
    public void setList(ArrayList<Object> objects) {

        HideoutObjects = new ArrayList<>();
        HideoutObjects.addAll(objects);
        if (HideoutObjects != null) {
            Log.d("test", HideoutObjects.toString());
        }

        checkListset_ClientConnected();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason.
        Log.i(TAG, "Connection suspended");

        // onConnected() will be called again automatically when the service reconnects
    }

    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    /**
     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
     * specified geofences. Handles the success or failure results returned by addGeofences().
     */
    public void addGeofences() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    /**
     * Removes geofences, which stops further notifications when the device enters or exits
     * previously registered geofences.
     */
    public void removeGeofences() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Remove geofences.
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    // This is the same pending intent that was used in addGeofences().
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    private void logSecurityException(SecurityException securityException) {
        Log.e(TAG, "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

    /**
     * Runs when the result of calling addGeofences() and removeGeofences() becomes available.
     * Either method can complete successfully or with an error.
     * <p/>
     * Since this activity implements the {@link ResultCallback} interface, we are required to
     * define this method.
     *
     * @param status The Status returned through a PendingIntent when addGeofences() or
     *               removeGeofences() get called.
     */
    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Update state and save in shared preferences.
            mGeofencesAdded = !mGeofencesAdded;

            // Update the UI. Adding geofences enables the Remove Geofences button, and removing
            // geofences enables the Add Geofences button.
        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }

    /**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * This sample hard codes geofence data. A real app might dynamically create geofences based on
     * the user's location.
     */
    public void populateGeofenceList(List<Object> objects) {
        if (objects.size() > 0 && mGoogleApiClient.isConnected()) {

            for (Object hideout : objects) {

                mGeofenceList.add(new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this
                        // geofence.
                        .setRequestId(((Hideouts) hideout).getRequestId())

                        // Set the circular region of this geofence.
                        .setCircularRegion(
                                ((Hideouts) hideout).getLatitude(),
                                ((Hideouts) hideout).getLongitude(),
                                Constants.GEOFENCE_RADIUS_IN_METERS
                        )

                        // Set the expiration duration of the geofence. This geofence gets automatically
                        // removed after this period of time.
                        .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)

                        // Set the transition types of interest. Alerts are only generated for these
                        // transition. We track entry and exit transitions in this sample.
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT)

                        // Create the geofence.
                        .build());
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_CANCELED);
        this.finishActivity(GeoMain_RequestCode);
    }


    // Get the barcode results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (result != null) {
                    if (result.getContents() == null) {
                        this.finish();
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                    } else {
                        this.finish();
                        //save image to blob
//                        StoreBlob storeBlob = new StoreBlob(this, new RecentActivity(UUID.randomUUID().toString()), mUserPhoto);
//                        storeBlob.execute();
                        Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;

            case BeerPicker.BEERPICKER_REQUEST_CODE:
                RecentActivity recentActivity = data.getParcelableExtra("recentactivity");
                StoreBlob storeBlob = new StoreBlob(GeoMain.this, recentActivity, mUserPhoto);
                storeBlob.execute();
                finish();
                break;

        }

    }


}






