package com.mcm.spinneradapter;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mcm.R;

public class SpinnerAdapter extends BaseAdapter {

	Context context;
	ArrayList<ArrayList<String>> values;
	int layoutResourceId;
	boolean iselected;
	private TextView text1;
	private LayoutInflater mInflator;

	public SpinnerAdapter(Context context, ArrayList<ArrayList<String>> values,
			boolean iselected) {
		this.context = context;
		this.values = values;
		this.iselected = iselected;
		mInflator = ((Activity) context).getLayoutInflater();

//		Log.e("Client CHURCH LIST", "" + values.size());
	}

	@Override
	public int getCount() {
		if (values != null)
			return values.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return values.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflator.inflate(R.layout.row_spinner, null);
		}
		text1 = (TextView) convertView.findViewById(R.id.spinnerTarget);

		if (values.size() == 0) {
			Log.e("mai 0 mai aa gaya", "" + values.size());
			text1.setText("Please select the Church");
		} else if (values.size() == 1) {
			Log.e("mai 1 mai aa gaya", "" + values.size());
			text1.setText(values.get(pos).get(4).toString().trim());
		} else if (values.size() > 1) {
			if (iselected) {
				Log.e("mai true mai aa gaya", "" + values.size());
				text1.setText("Please select the Church");
			} else {
				Log.e("mai false mai aa gaya", "" + values.size());
				text1.setText(values.get(pos).get(4).toString().trim());
			}
		}
		
	

		return convertView;
	}

	@Override
	public View getDropDownView(int pos, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflator.inflate(
					android.R.layout.simple_spinner_dropdown_item, null);
		}
		text1 = (TextView) convertView.findViewById(android.R.id.text1);
		text1.setText(values.get(pos).get(4).toString().trim());
		return convertView;
	}

}
