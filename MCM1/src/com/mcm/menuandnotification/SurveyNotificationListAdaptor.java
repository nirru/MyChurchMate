package com.mcm.menuandnotification;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.library.SurveyNotificationPostJson;

public class SurveyNotificationListAdaptor extends BaseAdapter {

	Context context;
	int layoutResourceId;
	String folderName;
	ArrayList<ArrayList<String>> values;
	String dateToPass, dayOfTheWeek, month, day, year;
	int hour, min;
	String amPm;
	int clientID, notificationId, memberId;

	public SurveyNotificationListAdaptor(Context context,
			ArrayList<ArrayList<String>> values, int layoutResourceId,
			String folderName, int clientID, int memberId, int notificationId) {
		this.context = context;
		this.values = values;
		this.layoutResourceId = layoutResourceId;
		this.folderName = folderName;
		this.clientID = clientID;
		this.memberId = memberId;
		this.notificationId = notificationId;
		
		Log.e("VALUE IN THE CAR", "" + values.size());
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

		View row = convertView;
		ItemHolder holder = null;
		final int position = pos;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ItemHolder();
			row.setTag(holder);

		} else {
			holder = (ItemHolder) row.getTag();
		}

		holder.messaage = (TextView) row.findViewById(R.id.textView1);
		holder.approvedButton = (ImageView) row.findViewById(R.id.approve_image);
		holder.rejectButton = (ImageView) row.findViewById(R.id.reject_image);

		holder.messaage.setText(values.get(position).get(0).toString().trim());
        Log.e("I AM VAKUE", "" + values.get(position).get(0).toString().trim());
		holder.rejectButton.setTag(position);;
		holder.approvedButton.setTag(position);
		holder.approvedButton.setOnClickListener(al);
		holder.rejectButton.setOnClickListener(rl);
		return row;
	}

	View.OnClickListener al = new OnClickListener() {

		@Override
		public void onClick(View v) {

			int id = (Integer) v.getTag();
			Log.e("ID IN RECEIVED", "" + id);
			displayView(id, true);
		}
	};

	View.OnClickListener rl = new OnClickListener() {

		@Override
		public void onClick(View v) {

			int id = (Integer) v.getTag();
			Log.e("ID IN RECEIVED", "" + id);
			displayView(id, false);
		}
	};

	static class ItemHolder {
		TextView messaage;
		ImageView approvedButton;
		ImageView rejectButton;
	}

	private void displayView(int id, boolean isTrue) {
		String url = "http://mcmwebapi.victoriatechnologies.com/api/PushNotification/PostSurvey";
		ProgressDialog mProgressDialog = new ProgressDialog(context);
	       new SurveyAsync(context, mProgressDialog, url, clientID, memberId, notificationId, isTrue).execute("");

	}

}