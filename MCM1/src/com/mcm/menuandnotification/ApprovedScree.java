package com.mcm.menuandnotification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mcm.R;
import com.mcm.appconstant.AppConstant;
import com.mcm.database.GetDataFromDatabase;

public class ApprovedScree extends Fragment {
	TextView textView, event_fragement_header;
	ListView listView;
	ListView upcoming_listView;
	ArrayList<ArrayList<String>> approveList;
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
	ApprovedListAdaptor approvedListAdaptor;
	ProgressDialog mProgressDialog;

	public ApprovedScree(Context context, int clientID, String folderName,
			String email_String) {
		this.clientID = clientID;
		this.context = context;
		this.folderName = folderName;
		this.email_String = email_String;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_approved,
				container, false);
		Log.e("EXUCUTED FIRST", "YES");
		init(rootView);
		Log.e("EXUCUTED SCOND", "YES");
		startAsynTask();
		Log.e("EXUCUTED THIRD", "YES");
		setBackground("EventsBG.png", folderName, event_fragement_background);
		return rootView;
	}

	private void init(View rootView) {
		approveList = new ArrayList<ArrayList<String>>();
		mpProgressDialog = new ProgressDialog(context);
		event_fragement_header = (TextView) rootView
				.findViewById(R.id.ev_fr_event_header);
		event_fragement_background = (ImageView) rootView
				.findViewById(R.id.fg_detail_imageview);
		listView = (ListView) rootView.findViewById(R.id.listView1);
//		approvedListAdaptor = new ApprovedListAdaptor(context, approveList,
//				R.layout.approved_fragement_row, folderName,mpProgressDialog);
//		listView.setAdapter(approvedListAdaptor);
		Log.e("EXUCUTED init in the last", "YES");

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
		mpProgressDialog.setCanceledOnTouchOutside(false);
		mpProgressDialog
		.setMessage("Sync data from Server is in progress. Please wait patiently....");
		mpProgressDialog.show();
		String url = "http://mcmwebapi.victoriatechnologies.com/api/Member/ListForApproval?ClientId=" + clientID;
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		        Log.e("RESPONSE", "" + response);
		        try {
					JSONArray jArray = new JSONArray(response.trim());

					Log.e("JSON LENGHT ", "" + jArray.length());
					for (int i = 0; i < jArray.length(); i++) {
						ArrayList<String> memeberFeild = new ArrayList<String>();
						JSONObject jsonObject = jArray.getJSONObject(i);
						
						String firstName = jsonObject.getString(AppConstant.LOGIN_FIRST_NAME);
						String surName = jsonObject.getString(AppConstant.LOGIN_SURNAME);
						String emailString = jsonObject.getString(AppConstant.LOGIN_EMAIL_ID);
						String clientId =  jsonObject.getString(AppConstant.LOGIN_CLIENT_ID);
						
						memeberFeild.add("" + firstName);
						memeberFeild.add("" + surName);
						memeberFeild.add("" + emailString);
						memeberFeild.add("" + clientId);
						
						approveList.add(memeberFeild);
						
						
						
					} // update
				
					approvedListAdaptor = new ApprovedListAdaptor(context, approveList,listView,
							R.layout.approved_fragement_row, folderName,mpProgressDialog);
					listView.setAdapter(approvedListAdaptor);
					approvedListAdaptor.notifyDataSetChanged();
					listView.invalidateViews();
					mpProgressDialog.dismiss();
//					Log.e("Approve List", "" + approveList);
				} catch (Exception e) {
					e.printStackTrace();

				}
		    }
		});
	}
	

}
