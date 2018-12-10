package gr.teicm.cityguidetl.cityguidetl.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gr.teicm.cityguidetl.cityguidetl.Activities.PointsActivity;
import gr.teicm.cityguidetl.cityguidetl.Entities.Photos;
import gr.teicm.cityguidetl.cityguidetl.Entities.Point;
import gr.teicm.cityguidetl.cityguidetl.R;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointsViewAHolder> {

	public class PointsViewAHolder extends  RecyclerView.ViewHolder {

		ImageView image;

		public TextView text;
		public TextView cityNameTextView;

		public PointsViewAHolder(View view)  {
			super(view);
			text = view.findViewById(R.id.pointName);
			image = view.findViewById(R.id.pointImage);
			cityNameTextView = view.findViewById(R.id.cityNameTextView);

		}
	}

	List<Photos> points;
	String cityName;

	public PointsAdapter(List<Photos> points) {
		this.points = points;
	}

	@NonNull
	@Override
	public PointsViewAHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.point, viewGroup, false);

		return new PointsViewAHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull PointsViewAHolder viewHolder, int index) {

		Photos point = points.get(index);

		viewHolder.text.setText(point.getLatitude().toString() + " " +  point.getLongitude().toString());

	}

	@Override
	public int getItemCount() {
		return  points.size();
	}
}
