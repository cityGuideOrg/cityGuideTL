package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.LinkedList;

import gr.teicm.cityguidetl.cityguidetl.Adapters.CityListAdapter;
import gr.teicm.cityguidetl.cityguidetl.Adapters.PointsAdapter;
import gr.teicm.cityguidetl.cityguidetl.Adapters.PointsGridAdapter;
import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.Point;
import gr.teicm.cityguidetl.cityguidetl.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//TODO:pass the city object here, handle it, based on that object get points from db
//TODO:pass the points to the adapter and then handle showing the in the grid view design


public class PointsActivity extends AppCompatActivity {

	RecyclerView recyclerView;
	Location location;



	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interesting_points_of_city);
		//final CardView cardView = (CardView) findViewById(R.id.cardviewPoints);
		LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
		LocationListener mLocationListener = new LocationListener() {
			@Override
			public void onLocationChanged(final Location location) {
				//your code hereas
				PointsActivity.this.location = location;
			}

			@Override
			public void onStatusChanged(String s, int i, Bundle bundle) {

			}

			@Override
			public void onProviderEnabled(String s) {

			}

			@Override
			public void onProviderDisabled(String s) {

			}
		};
		if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

		}
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,mLocationListener);



		final String city_id = String.valueOf(getIntent().getExtras().getInt("city_id"));

		Log.e("demo", city_id);

		findViewById(R.id.showMap)
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						Log.e("onclick", location.toString());

						Intent intent = new Intent(PointsActivity.this, MapActivity.class);
							intent.putExtra("city_id", city_id);
						PointsActivity.this.startActivity(intent);
					}
				});
		recyclerView = findViewById(R.id.cardviewPoints);
		recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));

		MainActivity.cityService.getCityWithInterestingPlaces(Long.valueOf(city_id))
		.enqueue(new Callback<City>() {
			@Override
			public void onResponse(Call<City> call, Response<City> response) {
				recyclerView.setAdapter(new PointsAdapter(response.body().getTopFivePhotos()));
			}

			@Override
			public void onFailure(Call<City> call, Throwable t) {

			}
		});








//		cardView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//
//				ColorStateList color = cardView.getCardBackgroundColor();
//				if(color.getDefaultColor()!= Color.parseColor("#00AF67"))
//					cardView.setCardBackgroundColor(Color.parseColor("#00AF67"));
//				else
//					cardView.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
//			}
//		});
	}
}
