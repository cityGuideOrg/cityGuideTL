package gr.teicm.cityguidetl.cityguidetl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.Entities.Point;
import gr.teicm.cityguidetl.cityguidetl.R;
//TODO:in adapters you just give the data to-be shown, the handling and picking of data should be
//TODO:conducted elsewhere
public class PointsGridAdapter extends ArrayAdapter<Point> {

	private Context context;
	private List<Point> points;

	public PointsGridAdapter(Context context, List<Point> points) {
		super(context, R.layout.activity_interesting_points_of_city, points);

		this.context = context;
		this.points = points;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row==null) {
			LayoutInflater inflater =
					(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.activity_interesting_points_of_city, parent, false);
		}
		return row;
		}
	}

