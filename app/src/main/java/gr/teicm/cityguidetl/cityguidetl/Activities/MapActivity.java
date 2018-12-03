package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.Photos;
import gr.teicm.cityguidetl.cityguidetl.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends Activity {

	MapView mapView;

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_map);

		mapView = findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);

		super.onCreate(savedInstanceState);

		final String city_id = getIntent().getExtras().getString("city_id");

		Log.e("demo 1234", city_id);
		mapView.getMapAsync(new OnMapReadyCallback() {
			@Override
			public void onMapReady(final GoogleMap googleMap) {
				Log.e("map ready", "asdasd");
				MainActivity.cityService.getCityWithInterestingPlaces(Long.valueOf(city_id))
				.enqueue(new Callback<City>() {
					@Override
					public void onResponse(Call<City> call, final Response<City> response) {
						Log.e("response got from the", city_id);
								MarkerOptions o;
								for(Photos photo : response.body().getTopFivePhotos()) {
									o = new MarkerOptions();

									o.position(new LatLng(photo.getLatitude(), photo.getLongitude()));

									googleMap.addMarker(o);
								}
							}
					@Override
					public void onFailure(Call<City> call, Throwable t) {
					}
				});
				}
		});
	}
	@Override
	protected void onStart() {
		super.onStart();
		mapView.onStart();
	}



}
