package com.mcm.menuandnotification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.SplashActivity;
import com.mcm.database.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.sun.mail.util.QEncoderStream;

public class Notifications extends Fragment {
	TextView textView, event_fragement_header;
	ListView listView;
	ListView upcoming_listView;
	ArrayList<ArrayList<String>> notificationList;
	ArrayList<ArrayList<String>> upcomingeventsList;
	ArrayList<ArrayList<String>> surveyNotificationList;
	GetDataFromDatabase getDataFromDatabase;
	Context context;
	int clientID;
	String folderName;
	String message;
	RelativeLayout upComingEvents;
	ImageView event_fragement_background, refresh_img;
	ProgressDialog mpProgressDialog;
	String email_String;
	NotificationListAdaptor notificationListAdapter;
	int surveyFlag, memberId , notificationId;
	String notificationMessage;

	public Notifications(Context context, int clientID, String folderName,
			String email_String, int surveyFlag, String notificationMessage, int notification_id) {
		this.clientID = clientID;
		this.context = context;
		this.folderName = folderName;
		this.email_String = email_String;
		this.surveyFlag = surveyFlag;
		this.notificationMessage = notificationMessage;
		this.notificationId = notification_id;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_notificatiion,
				container, false);
		init(rootView);
		setBackground("EventsBG.png", folderName, event_fragement_background);
		return rootView;
	}

	private void init(View rootView) {

//		Log.e("survey FLAG", "" + surveyFlag);
		mpProgressDialog = new ProgressDialog(context);
		getDataFromDatabase = new GetDataFromDatabase();
		// Log.e("CLIENT ID IN EVENTS FRAGEMENTS", "" + clientID);
		notificationList = getDataFromDatabase.getAllNotification(clientID);
		surveyNotificationList = new ArrayList<ArrayList<String>>();
		event_fragement_header = (TextView) rootView
				.findViewById(R.id.ev_fr_event_header);

		upComingEvents = (RelativeLayout) rootView
				.findViewById(R.id.upcoming_events);
		event_fragement_background = (ImageView) rootView
				.findViewById(R.id.fg_detail_imageview);

		textView = (TextView) rootView.findViewById(R.id.ev_fr_event_subheader);
		listView = (ListView) rootView.findViewById(R.id.listView1);

		
		showList();

	}

	public Bitmap setBackground(String filename, String folder,
			ImageView imageView) {
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(
					context.getFilesDir(), "/Images/" + folder + "/"
							+ "ThemeImages/" + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			imageView.setImageBitmap(thumbnail);
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
		return thumbnail;
	}

	private void surveyNotification() {
		
		getMemeberIdFromClientId();
		for (int i = 0; i < 1; i++) {
			ArrayList<String> data = new ArrayList<String>();
			data.add(notificationMessage);
			surveyNotificationList.add(data);
		}

//		Log.e("SURVEY NOTIFICATION DATA", "" + surveyNotificationList);
		SurveyNotificationListAdaptor surveyNotificationListAdaptor = new SurveyNotificationListAdaptor(context,
				surveyNotificationList, R.layout.survey_custom_row, folderName, clientID , memberId , notificationId);
		listView.setAdapter(surveyNotificationListAdaptor);
	}

	private void getMemeberIdFromClientId() {

		SQLiteDatabase sqLiteDatabase = SplashActivity.databaseHelper
				.getReadableDatabase();
		String query = "SELECT " + AppConstant.MCM_MEMBER_MEMEBER_ID + " FROM "
				+ AppConstant.MEMBER_TABLE_NAME + " WHERE "
				+ AppConstant.MCM_MEMBER_CLIENT_ID + " ='" + clientID + "'";

		Cursor cursor = sqLiteDatabase.rawQuery(query, null);

		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			do {
				memberId = cursor.getInt(0);

			} while (cursor.moveToNext());
			cursor.close();
		}
		
//		Log.e("MEMBER ID", "" + memberId);

	}
	
	private void showList()
	{
		if (surveyFlag == 0) {
			Log.e("I AM IN IF", "" + "YES");
			notificationListAdapter = new NotificationListAdaptor(context,
					notificationList, R.layout.notification_fragement_row,
					folderName);
			listView.setAdapter(notificationListAdapter);
		} else {
			Log.e("I AM IN ELSE", "" + "YES");
			surveyNotification();
		}
	}

}
