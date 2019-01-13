package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.ui.IconGenerator;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import gr.teicm.cityguidetl.cityguidetl.Entities.BestRoute;
import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.Cost;
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

	private List<Marker> allMarkers = new LinkedList<Marker>();
	private Location finalLocation;
	private List<Point> justPoints;
	private List<Point> orderedPoints;

	private ListView listView;
	private List<DataSetObserver> observers = new LinkedList<DataSetObserver>();
	private List<Point> visitedPlaces = new LinkedList<Point>();
	private Polyline lastPolyLine;
	//require permissions for location of user in the points activity class and get position of the user
	//here if the permission were granted;

	public Point findVisited(Point p) {
		for(Point visitedPlace : visitedPlaces) {
			if(  p.distance(visitedPlace) < 10) {
				return visitedPlace;
			}
		}
		return null;
	}

	public Boolean isVisited(Point p) {
		return findVisited(p) != null;
	}

	public void deleteVisited(Point p) {
		final Point visited = findVisited(p);

		MainActivity.cityService.deleteVisitedPoint(visited.getId())
				.enqueue(new Callback<Void>() {
					@Override
					public void onResponse(Call<Void> call, final Response<Void> response) {

						visitedPlaces.remove(visited);
						updateList();
					}

					@Override
					public void onFailure(Call<Void> call, Throwable t) {

					}
				});
	}

	public void addVisited(Point p) {

		MainActivity.cityService.addVisitedPoint(p)
				.enqueue(new Callback<Point>() {
					@Override
					public void onResponse(Call<Point> call, final Response<Point> response) {
						visitedPlaces.add(response.body());
						updateList();
					}

					@Override
					public void onFailure(Call<Point> call, Throwable t) {

					}
				});


	}

	public void updateList() {
		update();
	}

	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_map);

		mapView = findViewById(R.id.mapView);
		listView = findViewById(R.id.listView);

		listView.setAdapter(new ListAdapter() {
			@Override
			public boolean areAllItemsEnabled() {
				return true;
			}

			@Override
			public boolean isEnabled(int i) {
				return true;
			}

			@Override
			public void registerDataSetObserver(DataSetObserver dataSetObserver) {
				observers.add(dataSetObserver);
			}

			@Override
			public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
				observers.remove(dataSetObserver);
			}

			@Override
			public int getCount() {
				return justPoints == null || orderedPoints == null ? 0 : justPoints.size();
			}

			@Override
			public Object getItem(int i) {

				if(i < orderedPoints.size()) {
					return orderedPoints.get(i);
				}

				i = i - orderedPoints.size();

				for(Point p : justPoints) {
					if(isVisited(p)) {
						if(i-- == 0) {
							return p;
						}
					}
				}

				return null;
			}

			@Override
			public long getItemId(int i) {
				return i;
			}

			@Override
			public boolean hasStableIds() {
				return true;
			}

			@Override
			public View getView(int i, View view, ViewGroup viewGroup) {

				if(view == null) {
					LayoutInflater inflater = LayoutInflater.from(getBaseContext());
					view = inflater.inflate(R.layout.item, viewGroup, false);

					view.findViewById(R.id.checkBox).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							int i = (int)view.getTag();
							Point point = (Point)getItem(i);

							if(isVisited(point)) {
								deleteVisited(point);
							} else {
								addVisited(point);
							}
						}
					});

					view.findViewById(R.id.buttonGo).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							int i = (int)view.getTag();
							Point point = (Point)getItem(i);

							Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
									Uri.parse("http://maps.google.com/maps?daddr="+point.getLatitude()+","+point.getLongitude()));
							startActivity(intent);
						}
					});
				}
				Point point = (Point)this.getItem(i);

				CheckBox visited = view.findViewById(R.id.checkBox);
				visited.setTag(i);

				Button goBtn = view.findViewById(R.id.buttonGo);
				goBtn.setTag(i);

				visited.setChecked(isVisited(point));

				Point myPoint = new Point();
				myPoint.setLongitude(BigDecimal.valueOf(finalLocation.getLongitude()));
				myPoint.setLatitude(BigDecimal.valueOf(finalLocation.getLatitude()));

				TextView box = view.findViewById(R.id.textView2);
				box.setText((i+1)+"");

				TextView disYou = view.findViewById(R.id.disYou);
				TextView disPrev = view.findViewById(R.id.disPrev);

				double distance = point.distance(myPoint);

				if(distance >= 1000) {
					disYou.setText( String.format("%.2f", (distance/1000.0)) + "km" );
				} else {
					disYou.setText( String.format("%d", (int)(distance)) + "m" );
				}

				if(i >= orderedPoints.size()) {
					box.setVisibility(View.INVISIBLE);
				} else {
					box.setVisibility(View.VISIBLE);
				}

				if(i == 0 || i >= orderedPoints.size()) {
					disPrev.setVisibility(View.INVISIBLE);
				} else {
					disPrev.setVisibility(View.VISIBLE);
					Point justPointPrev = (Point)this.getItem(i - 1);
					distance = point.distance(justPointPrev);

					if(distance >= 1000) {
						disPrev.setText( String.format("%.2f", (distance/1000.0)) + "km" );
					} else {
						disPrev.setText( String.format("%d", (int)(distance)) + "m" );
					}
				}

				return view;
			}

			@Override
			public int getItemViewType(int i) {
				return 1;
			}

			@Override
			public int getViewTypeCount() {
				return 1;
			}

			@Override
			public boolean isEmpty() {
				return justPoints == null || orderedPoints == null || justPoints.isEmpty();
			}
		});

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
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mapView.onStop();
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

		MainActivity.cityService.getVisitedPoints().enqueue(new Callback<List<Point>>() {
			@Override
			public void onResponse(Call<List<Point>> call, Response<List<Point>> response) {
				visitedPlaces = response.body();
				MainActivity.cityService.getCityWithInterestingPlaces(Long.valueOf(cityId))
						.enqueue(new Callback<City>() {
							@Override
							public void onResponse(Call<City> call, final Response<City> response) {
								Log.e("response got from the", cityId);
								MarkerOptions o;
								o = new MarkerOptions();
								if (response.body().getTopFivePhotos() != null) {
									justPoints =  new LinkedList<Point>();
									for (Photos photo : response.body().getTopFivePhotos()) {
										Point point = new Point();
										point.setLatitude(BigDecimal.valueOf(photo.getLatitude()));
										point.setLongitude(BigDecimal.valueOf(photo.getLongitude()));
										justPoints.add(point);
										o.position(new LatLng(photo.getLatitude(), photo.getLongitude()));

										o.icon(
												BitmapDescriptorFactory.fromBitmap(new IconGenerator(getBaseContext()).makeIcon("BLA BLA"))
										);
										allMarkers.add(googleMap.addMarker(o));
									}
									update();
								}
								else
									throw new NullPointerException("The are no top five photos");
								}

							@Override
							public void onFailure(Call<City> call, Throwable t) {

							}
						});
			}
			@Override
			public void onFailure(Call<List<Point>> call, Throwable t) {

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
				finalLocation = location;
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
				//TODO: run an update function to recalculate best route on available points list.....
				update();
			}
		}
	};

	public void update() {
		if(allMarkers.size() > 0 && finalLocation != null) {
			BestRoute body = new BestRoute();
			Point start = new Point();
			start.setLatitude(BigDecimal.valueOf(finalLocation.getLatitude()));
			start.setLongitude(BigDecimal.valueOf(finalLocation.getLongitude()));
			body.startingPoint = start;

			List<Point> notVisitedPoints = new LinkedList<>();

			for(Point point : justPoints) {
				if(!isVisited(point)) {
					notVisitedPoints.add(point);
				} else {
					for(Marker marker : allMarkers) {

						if (marker.getPosition().longitude == point.getLongitude().doubleValue()
								&& marker.getPosition().latitude == point.getLatitude().doubleValue()) {

							marker.setIcon(null);
						}
					}
				}
			}
			body.placesNeedsToBeVisited = notVisitedPoints;

			MainActivity.cityService.findBestRoute(body)
					.enqueue(new Callback<Cost>() {
						@Override
						public void onResponse(Call<Cost> call, Response<Cost> response) {
							orderedPoints = new LinkedList<>();
							Cost body = response.body();
							PolylineOptions polylineOptions = new PolylineOptions();
							polylineOptions.width(10);

							int i = 1;
							int b = 0;
							for(Point point : body.pointsTaken) {
								if(b != 0) {
								orderedPoints.add(point);}
								b = 1;
								for(Marker marker : allMarkers) {
									if(marker.getPosition().longitude == point.getLongitude().doubleValue()
											 && marker.getPosition().latitude == point.getLatitude().doubleValue()) {
									//	marker.(i+". Place");
										marker.setIcon(
										BitmapDescriptorFactory.fromBitmap(new IconGenerator(getBaseContext()).makeIcon(i+""))
										);
										++i;
									}
								}
								polylineOptions.add(new LatLng(point.getLatitude().doubleValue(), point.getLongitude().doubleValue()));
							}

							if(lastPolyLine != null) {
								lastPolyLine.remove();
							}

							lastPolyLine = googleMap.addPolyline(polylineOptions);

							for(DataSetObserver observer : observers) {
								observer.onChanged();
							}

						}

						@Override
						public void onFailure(Call<Cost> call, Throwable t) {

						}
					});



		}


	}

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
