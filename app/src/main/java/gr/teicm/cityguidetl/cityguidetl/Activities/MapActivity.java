package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.Photos;
import gr.teicm.cityguidetl.cityguidetl.Entities.Point;
import gr.teicm.cityguidetl.cityguidetl.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

	private MapView mapView;
	private GoogleMap googleMap;
	private FusedLocationProviderClient fusedLocationClient;
	private LocationRequest locationRequest;
	private Location lastKnownLocation;
	private Marker currentLocationMarker;
	private String cityId;
	private Context context;
	private int utilCounter = 0;
	//require permissions for location of user in the points activity class and get position of the user
	//here if the permission were granted;


	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_map);

		mapView = findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);

		super.onCreate(savedInstanceState);
		context = this;
		cityId = getIntent().getExtras().getString("city_id");
		fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
		mapView.getMapAsync(this);
		}

	@Override
	protected void onStart() {
		super.onStart();
		mapView.onStart();
	}

	@Override
	public void onMapReady(GoogleMap googleMapCallBack) {
		googleMap = googleMapCallBack;
		locationRequest = new LocationRequest();
		locationRequest.setInterval(5000); // two minute interval
		locationRequest.setFastestInterval(5000);
		locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (ContextCompat.checkSelfPermission(this,
					Manifest.permission.ACCESS_FINE_LOCATION)
					== PackageManager.PERMISSION_GRANTED) {
				//Location Permission already granted
				fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
				googleMap.setMyLocationEnabled(true);
			} else {
				//Request Location Permission
				checkLocationPermission();
			}
		} else {
			fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
			googleMap.setMyLocationEnabled(true);
		}
		MainActivity.cityService.getCityWithInterestingPlaces(Long.valueOf(cityId))
				.enqueue(new Callback<City>() {
					@Override
					public void onResponse(Call<City> call, final Response<City> response) {
						Log.e("response got from the", cityId);

						MarkerOptions o;
						o = new MarkerOptions();
						if (response.body().getTopFivePhotos() != null) {
							for (Photos photo : response.body().getTopFivePhotos()) {
								o.position(new LatLng(photo.getLatitude(), photo.getLongitude()));
								googleMap.addMarker(o);
							}


						}
						else
							throw new NullPointerException("The are no top five photos");

//					userPosition.setLatitude();
//					userPosition.setLongitude();
//					Location currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//					o.position(new LatLng())
						//googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(o.getPosition(), 12));
					}

					@Override
					public void onFailure(Call<City> call, Throwable t) {

					}
				});

	}

	LocationCallback locationCallback = new LocationCallback() {
		@Override
		public void onLocationResult(LocationResult locationResult) {
			List<Location> locationList = locationResult.getLocations();
			if (locationList.size() > 0) {
				//The last location in the list is the newest
				Location location = locationList.get(locationList.size() - 1);
				Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
				lastKnownLocation = location;
				if (currentLocationMarker != null) {
					currentLocationMarker.remove();
				}

				//Place current location marker
				LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(latLng);
				markerOptions.title("Current Position");
				markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
				currentLocationMarker = googleMap.addMarker(markerOptions);

				//
				if(utilCounter == 0) {
					googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
					utilCounter=+1;
				}

			}
		}
	};


		public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
		private void checkLocationPermission() {
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
					!= PackageManager.PERMISSION_GRANTED) {

				if (ActivityCompat.shouldShowRequestPermissionRationale(this,
						Manifest.permission.ACCESS_FINE_LOCATION)) {

					new AlertDialog.Builder(this)
							.setTitle("Location Permission Needed")
							.setMessage("This app needs the Location permission, please accept to use location functionality")
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {
									ActivityCompat.requestPermissions(MapActivity.this,
											new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
											MY_PERMISSIONS_REQUEST_LOCATION);
								}
							})
							.create()
							.show();


				} else {
					// No explanation needed, we can request the permission.
					ActivityCompat.requestPermissions(this,
							new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
							MY_PERMISSIONS_REQUEST_LOCATION);
				}
			}
		}

		@Override
		public void onRequestPermissionsResult(int requestCode,
											   String permissions[], int[] grantResults) {
			switch (requestCode) {
				case MY_PERMISSIONS_REQUEST_LOCATION: {
					if (grantResults.length > 0
							&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
						if (ContextCompat.checkSelfPermission(this,
								Manifest.permission.ACCESS_FINE_LOCATION)
								== PackageManager.PERMISSION_GRANTED) {

							fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
							googleMap.setMyLocationEnabled(true);
						}

					} else {

						Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
					}
					return;
				}

			}
		}
}
