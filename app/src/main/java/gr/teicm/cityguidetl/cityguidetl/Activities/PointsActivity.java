package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interesting_points_of_city);
		//final CardView cardView = (CardView) findViewById(R.id.cardviewPoints);


		final String city_id = String.valueOf(getIntent().getExtras().getInt("city_id"));
		final String city_name = String.valueOf(getIntent().getExtras().getString("city_name"));
		Log.e("demo", city_id);
		TextView cityNameTextView = findViewById(R.id.cityNameTextView);
		cityNameTextView.setText(city_name);
		findViewById(R.id.showMap)
				.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						Log.e("onclick", "demo");

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
