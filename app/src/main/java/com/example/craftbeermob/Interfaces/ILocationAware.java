package com.example.craftbeermob.Interfaces;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ret70 on 6/11/2016.
 */
public interface ILocationAware {

    void CurrentLocation(LatLng latLng);

    void ClientConnected(GoogleApiClient googleApiClient);
}
