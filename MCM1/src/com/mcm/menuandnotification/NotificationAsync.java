package com.mcm.menuandnotification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.appconstant.AppConstant;

public class NotificationAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String url;
	SQLiteDatabase database;
	int clientid;
	int notificationId;
	public NotificationAsync(Context context, ProgressDialog mProgressDialog,
			String url, int clientid,int notificationId) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.notificationId = notificationId;
		this.clientid = clientid;
		Log.e("MERA URL DEKH LO", "" + url);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
		Log.e("MAY AYA", "" + "do in background in sigin");
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

	private void callLogin() {
	}

	private void postmessageInLogin(String urlPost, String messagePost) {

		try {
			JSONArray jsonArray = new JSONArray(messagePost.toString().trim());
			
//			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = null;

				jsonObject = jsonArray.getJSONObject(0);
				 Log.e("LEngh is Not zero ", "" + jsonArray.length());
				String appPin = jsonObject
						.getString(AppConstant.LOGIN_CLIENT_ID);
//				Log.e("App pin", "" + appPin);
				String fName = jsonObject
						.getString(AppConstant.LOGIN_FIRST_NAME);
//				Log.e("First nAMe", "" + fName);
				String sName = jsonObject.getString(AppConstant.LOGIN_SURNAME);
//				Log.e("sName nAMe", "" + sName);
				String email = jsonObject.getString(AppConstant.LOGIN_EMAIL_ID);
//				Log.e("email", "" + email);
				String passWord = jsonObject
						.getString(AppConstant.LOGIN_PASSWORD);
//				Log.e("passWord", "" + passWord);
				String address_One = jsonObject
						.getString(AppConstant.LOGIN_ADDRESS_ONE);
//				Log.e("address_One", "" + address_One);
				String address_Two = jsonObject
						.getString(AppConstant.LOGIN_ADDRESS_TWO);
//				Log.e("address_Two", "" + address_Two);
				String street = jsonObject.getString(AppConstant.LOGIN_STREET);
//				Log.e("street", "" + street);
				String town = jsonObject.getString(AppConstant.LOGIN_TOWN);
//				Log.e("town", "" + town);
				String city = jsonObject.getString(AppConstant.LOGIN_CITY);
//				Log.e("city", "" + city);
				String state = jsonObject.getString(AppConstant.LOGIN_STATE);
//				Log.e("state", "" + state);
				String county = jsonObject.getString(AppConstant.LOGIN_COUNTY);
//				Log.e("state", "" + state);
				String country = jsonObject
						.getString(AppConstant.LOGIN_COUNTRY);
//				Log.e("country", "" + country);
				String postalCode = jsonObject
						.getString(AppConstant.LOGIN_POSTAL_CODE);
//				Log.e("postalCode", "" + postalCode);
				String mobileNumber = jsonObject
						.getString(AppConstant.LOGIN_MOBILE);
//				Log.e("mobileNumber", "" + mobileNumber);
				String sex = jsonObject.getString(AppConstant.LOGIN_SEX);
//				Log.e("sex", "" + sex);
				String homeTelephone = jsonObject
						.getString(AppConstant.LOGIN_HOMETELEPHONE);
//				Log.e("homeTelephone", "" + homeTelephone);
//				String physicalDeviceId = jsonObject
//						.getString(AppConstant.LOGIN_PHYSICAL_DEVICE_ID);
//				String deviceOS = jsonObject
//						.getString(AppConstant.LOGIN_PHYSICAL_DEVICE_OS);
//				String deviceIdFromGCMorAPNS = jsonObject
//						.getString(AppConstant.LOGIN_PHYSICAL_DEVICE_ID_FROM_GCM);
//				String deviceType = jsonObject
//						.getString(AppConstant.LOGIN_PHYSICAL_DEVICE_TYPE);
				String churMemeberShip_ID = jsonObject
						.getString(AppConstant.LOGIN_CHURCHMEMEBERSHIP_ID).toString();
//				Log.e("churMemeberShip_ID", "" + churMemeberShip_ID);
				
				
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	
}
