package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import gr.teicm.cityguidetl.cityguidetl.R;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MapView mapView = new MapView(this);
        ((ConstraintLayout) findViewById(R.id.mapView)).addView(mapView);
        mapView.getMapAsync(this);




    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        if (googleMap != null)
        {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("You are here!"));
        }

    }
}
