package gr.teicm.cityguidetl.cityguidetl.Services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationService implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final int REQUEST_LOCATION = 0;
    private final Context context;
    private FusedLocationProviderClient locationProvider;
    private LatLng latLng;
    private GoogleApiClient mGoogleApiClient;


    public LocationService(Context context) {
        this.context = context;
        buildGoogleApiClient(context);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else {
            Toast.makeText(context, "Google API client not connected...", Toast.LENGTH_SHORT).show();
        }

    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng location) {
        this.latLng = location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationProvider = LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationProvider.getLastLocation()
                .addOnSuccessListener((AppCompatActivity) context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Toast.makeText(context, "Got Location", Toast.LENGTH_SHORT).show();
                        // Got last known location. In some rare situations this can be null.

                        if (location != null) {
                            setLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
                            Toast.makeText(context, "Location: " + location.getLatitude() + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Toast.makeText(context, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    protected synchronized void buildGoogleApiClient(Context context)
    {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
