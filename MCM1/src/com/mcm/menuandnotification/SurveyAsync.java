package com.mcm.menuandnotification;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.library.SurveyNotificationPostJson;

public class SurveyAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String url;
	int clientID, memberId, notificationId;
    boolean isApprove;
	
	public SurveyAsync(Context context, ProgressDialog mProgressDialog,
			String url, int clientID, int memberId, int notificationId,  boolean isApprove) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.clientID = clientID;
		this.memberId = memberId;
		this.notificationId = notificationId;
		this.isApprove = isApprove;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
//		Log.e("MAY AYA", "" + "do in background in sigin");
		callLogin();
		return null;
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
	}

	void showDialog() {
		mProgressDialog
				.setMessage("Login Successful.  Sync data from Server is in progress. Please wait patiently....");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	private void callLogin(){
		String url = "http://mcmwebapi.victoriatechnologies.com/api/PushNotification/PostSurvey";
		SurveyNotificationPostJson surveyNotificationPostJson = new SurveyNotificationPostJson(
				url, clientID, memberId, notificationId, isApprove);
		String my = surveyNotificationPostJson.postDataToServer();
//		Log.e("TAGGGGGG", "" + my);
	}

	
}
