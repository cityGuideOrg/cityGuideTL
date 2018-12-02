package gr.teicm.cityguidetl.cityguidetl.Activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import gr.teicm.cityguidetl.cityguidetl.R;


//TODO:pass the city object here, handle it, based on that object get points from db
//TODO:pass the points to the adapter and then handle showing the in the grid view design


public class PointsActivity extends AppCompatActivity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interesting_points_of_city);
		final CardView cardView = (CardView) findViewById(R.id.cardviewPoints);
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
