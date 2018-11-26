package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import gr.teicm.cityguidetl.cityguidetl.R;
import gr.teicm.cityguidetl.cityguidetl.Sights;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static String data;
    String topFivePhotosParsed = "";
    ArrayList<Sights> sightsArrayList=new ArrayList<>();
    TextView locTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locTextView= (TextView) findViewById(R.id.textView2);

        Button clickButton = (Button) findViewById(R.id.button_id);
        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
                for(Sights sight : sightsArrayList)
                {
                    addMarker(sight);
                }
            }
        });


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }

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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap=googleMap;
        googleMap.setMinZoomPreference(8);
        String lon="151";
        String lan="-34";

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

    public void addMarker(Sights sight)
    {
        LatLng latLng = new LatLng(Double.valueOf(sight.getLatitude()),Double.valueOf(sight.getLongitude()));
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        String Slatlag = String.valueOf(latLng.latitude) +","+ String.valueOf(latLng.longitude);
        locTextView.setText(Slatlag);
    }

    public void jsonParse() //TODO 1.make separate class for this 2.Take the town as parameter when list of supported cities is done
    {
        RequestQueue mQueue;
        mQueue=Volley.newRequestQueue(this);
        String URL="https://api.myjson.com/bins/960ti";
        //String URL="http://10.0.2.2:8080/flickr/get/1";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                topFivePhotosParsed ="";
                try {
                    JSONArray jsonTopFiveArray =response.getJSONArray("topFivePhotos");
                    for(int i =0;i<jsonTopFiveArray.length(); i++)
                    {
                        JSONObject topFivePhotos =jsonTopFiveArray.getJSONObject(i);
                        int totalPhotos = topFivePhotos.getInt("totalNearPhotos");
                        long id=topFivePhotos.getLong("id");
                        String longitude=topFivePhotos.getString("longitude");
                        String latitude=topFivePhotos.getString("latitude");
                        Sights sights = new Sights(totalPhotos,id,longitude,latitude);
                        sightsArrayList.add(sights);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getNetworkTimeMs());
            }
        });
        mQueue.add(request);

    }


}


