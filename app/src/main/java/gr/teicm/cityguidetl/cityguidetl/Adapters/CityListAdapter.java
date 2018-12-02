package gr.teicm.cityguidetl.cityguidetl.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import gr.teicm.cityguidetl.cityguidetl.Entities.City;
import gr.teicm.cityguidetl.cityguidetl.R;

public class CityListAdapter extends ArrayAdapter<City> {
	private Context context;
	private List<City> values;

	public CityListAdapter(Context context, List<City> values) {
		super(context, R.layout.list_item_pagination, values);

		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			LayoutInflater inflater =
					(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.list_item_pagination, parent, false);
		}
		TextView textView = (TextView) row.findViewById(R.id.list_item_pagination_text);

		City item = values.get(position);
		String message = item.getWoe_name();
		textView.setText(message);
		return row;
	}
}
