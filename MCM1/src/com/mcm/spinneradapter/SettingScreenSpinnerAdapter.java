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

public class SettingScreenSpinnerAdapter extends BaseAdapter {

	Context context;
	ArrayList<ArrayList<String>> values;
	int layoutResourceId;
	boolean iselected;
	private TextView text2;
	private LayoutInflater mInflator;
	String defaultSpinnerValue;
	String id;

	public SettingScreenSpinnerAdapter(Context context, ArrayList<ArrayList<String>> values, String defaultSpinnerValue, String id) {
		this.context = context;
		this.values = values;
		this.defaultSpinnerValue = defaultSpinnerValue;
		this.id  = id;
		mInflator = ((Activity) context).getLayoutInflater();
		Log.e("defaultSpinnerValueL", "  " + defaultSpinnerValue);
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
			convertView = mInflator.inflate(R.layout.custom_spinner_row, parent ,false);
		}
		text2 = (TextView) convertView.findViewById(R.id.spinnerTarget);

//		if (pos == 0) {
//			text2.setText(defaultSpinnerValue);
//		} else {
//			text2.setText(values.get(pos).get(1).toString().trim());
//		}
		
		if (id.trim()== "null") {
			text2.setText(defaultSpinnerValue);
		} else {
          int valueInt = Integer.parseInt(id);
          text2.setText(values.get(valueInt).get(1).toString().trim());
		}
		
		
		return convertView;
	}

	@Override
	public View getDropDownView(int pos, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = mInflator.inflate(
					android.R.layout.simple_spinner_dropdown_item, null);
		}
		text2 = (TextView) convertView.findViewById(android.R.id.text1);
		text2.setText(values.get(pos).get(1).toString().trim());
		return convertView;
	}

}
