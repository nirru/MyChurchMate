package com.mcm.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gcm.GCMRegistrar;
import com.mcm.R;
import com.mcm.database.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.listener.HomeClickListner;
import com.mcm.login.LoginActivity;
import com.mcm.registration.RegisterActivity;

public class HomeActivity extends FragmentActivity {
	Button register, signIn;
	ProgressDialog mProgressDialog;
	boolean isLoginAlready;
	int clientid;
	String regid;
	String clientEmail;
	static String msgREGID = "";
	SharedPreferences sharedPreferences;
	String url = "http://mcmwebapi.victoriatechnologies.com/api/Client?EmailId=";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_or_sign_in_activity_layout);
		init();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}

	private void init() {
		sharedPreferences = this.getSharedPreferences("com.example.app",
				HomeActivity.MODE_PRIVATE);
		isLoginAlready = sharedPreferences.getBoolean(
				com.mcm.appconstant.AppConstant.PREFS_KEYS, false);
		clientid = sharedPreferences.getInt(
				com.mcm.appconstant.AppConstant.PREFS_KEYS_CLIENT_ID, 0);
		clientEmail = sharedPreferences.getString(
				com.mcm.appconstant.AppConstant.EMAIL_ID, null);
		msgREGID = sharedPreferences.getString(
				com.mcm.appconstant.AppConstant.KEYS_REGISTRATION, "");
		Log.e("CLIENT EMAIL in home Activity", "" + clientEmail);
		mProgressDialog = new ProgressDialog(HomeActivity.this);
		register = (Button) findViewById(R.id.reg_btn);
		signIn = (Button) findViewById(R.id.signin_btn);
		register.setOnClickListener(homeClkListener);
		signIn.setOnClickListener(homeClkListener);
		
		getRegIdFromPrefences();
	}

	HomeClickListner homeClkListener = new HomeClickListner() {

		@Override
		public void onSignInBtnClk(View view) {
			checkLogin();
		}

		@Override
		public void onRegisterBtnClk(View view) {
			Intent intent = new Intent(HomeActivity.this,
					RegisterActivity.class);
			
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
		}
	};

	public boolean checkForMEmberTables() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		return getDataFromDatabase.checkForTables();
	}

	private String getRegistrationIdFromServer() {
		Log.e("getRegistrationIdFromServer", "getRegistrationIdFromServer");
		msgREGID = GCMRegistrar.getRegistrationId(HomeActivity.this);
		putRegIdinPrefences();
		Log.e("I HAVE GET REGISTRATION ID", "" + msgREGID);
		return msgREGID;
	}

	private void checkLogin() {
		Log.e("IS ALREAD LOGIN", "" + isLoginAlready);
		if (isLoginAlready) {
			Log.e("CLIEN ID IN LOGIN ACTIVITU", "" + clientid);
			Intent i = new Intent(HomeActivity.this,
					com.mcm.menuandnotification.MenuAndNotification.class);
			i.putExtra(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID,
					clientid);
			i.putExtra(com.mcm.appconstant.AppConstant.EMAIL_ID, clientEmail);
			startActivity(i);
			finish();
		} else {
			Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(AppConstant.CHECK_TABLE, checkForMEmberTables());
			startActivity(intent);
			finish();
		}
	}
	
	private void putRegIdinPrefences()
	{
		SharedPreferences.Editor prefs = HomeActivity.this
				.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
				.edit();
		prefs.putString(com.mcm.appconstant.AppConstant.KEYS_REGISTRATION, msgREGID);
		prefs.commit();
		}
	
	private void getRegIdFromPrefences()
	{
		if (msgREGID.equals("")) {
			Log.e("HIT BLANK", "YES");
			regid = getRegistrationIdFromServer();
		} else {
			Log.e("HIT FILLED", "YES");
          regid = msgREGID;
		}
	}

}
