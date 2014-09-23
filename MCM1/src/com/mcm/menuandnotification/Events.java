package com.mcm.menuandnotification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.database.GetDataFromDatabase;

public class Events extends Fragment implements OnScrollListener {
	TextView textView, event_fragement_header;
	ListView listView;
	ListView upcoming_listView;
	ArrayList<ArrayList<String>> eventsList;
	ArrayList<ArrayList<String>> upcomingeventsList;
	GetDataFromDatabase getDataFromDatabase;
	Context context;
	int clientID;
	String folderName;
	String message;
	RelativeLayout upComingEvents;
	ImageView event_fragement_background, refresh_img;
	ProgressDialog mpProgressDialog;
	String email_String;
	EventListAdaptor eventListAdaptor, eventListAdaptor1;
	Date currentDate;
	int currentFirstVisibleItem, currentVisibleItemCount, currentScrollState;
	boolean isLoading = false;
	int offset;

	public Events(Context context, int clientID, String folderName,
			String email_String) {

		this.clientID = clientID;
		this.context = context;
		this.folderName = folderName;
		this.email_String = email_String;
		// Log.e("FolderName", "" + folderName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event, container,
				false);
		init(rootView);
		setBackground("EventsBG.png", folderName, event_fragement_background);
		// getThumbnail("upcoming-events-bg.png", folderName, upComingEvents);
		return rootView;
	}

	private void init(View rootView) {

		currentDate = new Date(System.currentTimeMillis());
		// Log.e("CURRENT TIMME for calneder", "" + currentDate);
		mpProgressDialog = new ProgressDialog(context);
		getDataFromDatabase = new GetDataFromDatabase();
		// Log.e("CLIENT ID IN EVENTS FRAGEMENTS", "" + clientID);
		eventsList = getDataFromDatabase.GetAllEvents(clientID);

		// Log.e("EVENT LIST", "" + eventsList);
		upcomingeventsList = getDataFromDatabase.GetEvents(clientID, true);

		event_fragement_header = (TextView) rootView
				.findViewById(R.id.ev_fr_event_header);
		upComingEvents = (RelativeLayout) rootView
				.findViewById(R.id.upcoming_events);
		event_fragement_background = (ImageView) rootView
				.findViewById(R.id.fg_bg_image);
		textView = (TextView) rootView.findViewById(R.id.ev_fr_event_subheader);
		listView = (ListView) rootView.findViewById(R.id.listView1);
		upcoming_listView = (ListView) rootView.findViewById(R.id.listView2);

		refresh_img = (ImageView) rootView.findViewById(R.id.imageView1);
		refresh_img.setOnClickListener(l);

		eventListAdaptor = new EventListAdaptor(context, eventsList,
				R.layout.event_fragement_row, folderName, currentDate,
				email_String, clientID);
		listView.setAdapter(eventListAdaptor);
		eventListAdaptor1 = new EventListAdaptor(context, upcomingeventsList,
				R.layout.event_fragement_row, folderName, currentDate,
				email_String, clientID);
		upcoming_listView.setAdapter(eventListAdaptor1);

//		listView.setOnScrollListener(this);
	}

	View.OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			startAsynTask();
		}
	};

	public Bitmap getThumbnail(String filename, String folder,
			RelativeLayout layout) {
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(
					context.getFilesDir(), "/Images/" + folder + "/"
							+ "ThemeImages/" + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			layout.setBackgroundDrawable(d);
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
		return thumbnail;
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

	private void startAsynTask() {
		new RefreshAsyn(context, mpProgressDialog, email_String,
				eventListAdaptor, eventListAdaptor1, listView,
				upcoming_listView, clientID, folderName).execute("");
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.currentScrollState = scrollState;
		this.isScrollCompleted();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.currentFirstVisibleItem = firstVisibleItem;
		this.currentVisibleItemCount = visibleItemCount;
	}

	private void isScrollCompleted() {
		if (this.currentVisibleItemCount > 0
				&& this.currentScrollState == SCROLL_STATE_IDLE) {
			/***
			 * In this way I detect if there's been a scroll which has completed
			 ***/
			/*** do the work for load more date! ***/
//			if (!isLoading) {
				isLoading = true;
				offset = offset + 5;
				Log.e("MY SCROLING IS DONE", "  " + offset);
				eventsList = getDataFromDatabase.GetAllEvents(clientID);
				eventListAdaptor = new EventListAdaptor(context, eventsList,
						R.layout.event_fragement_row, folderName, currentDate,
						email_String, clientID);
				listView.setAdapter(eventListAdaptor);
//			}

		}
	}
}
