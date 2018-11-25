package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.teicm.cityguidetl.cityguidetl.R;
import gr.teicm.cityguidetl.cityguidetl.Services.LocationService;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private Button mLocationBtn;
    private MapView mapView;
    private int MY_PERMISSION_LOCATION;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mLocationBtn = findViewById(R.id.getLocationButton);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        marshmallowGPSPremissionCheck();

        mLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = locationService.getLatLng();

                Log.d("LATLNG", "onClick: " + locationService.getLatLng().latitude +
                        ", " + locationService.getLatLng().longitude);

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
//    public void myClickHandler(View target) {
//        Button button = findViewById(R.id.getLocationButton);
//        if(target.equals(button)) {
//
//        }
//    }

    private void marshmallowGPSPremissionCheck() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && this.checkSelfPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && this.checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_LOCATION);
        } else {
            locationService = new LocationService(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSION_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            locationService = new LocationService(this);
        }
    }

    @Override
    public void onMapReady (GoogleMap googleMap)
    {
        googleMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ny);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
        this.googleMap = googleMap;

    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }



}


