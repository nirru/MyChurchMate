package com.mcm;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.mcm.asynclass.ConnectionDetector;
import com.mcm.asynclass.SplashAsynTask;
import com.mcm.database.MCMDatabase;
import com.mcm.database.PzDatabaseHelper;
import com.mcm.home.HomeActivity;

public class SplashActivity extends Activity {

	public static PzDatabaseHelper databaseHelper;
	ProgressDialog mpProgressDialog;
	String url, reg_id;
	Boolean isInternetPresent = false;
	public static ArrayList<ArrayList<String>> churchMemeberList;
	public static ArrayList<ArrayList<String>> approvedMemeberList;
	public static String REG_ID;
	private Thread splashThreaad;
	public static Typeface mpBold, mpSemiBold, mpHardBold, mpKoll;
	boolean success = true;
	 String msgREGID = "";
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity_layout);
		init();
		createTable();
		startRegisterActivity();

		// startAsynTask();
	}
	

	private void init() {
		checkAppicationisRegisteredOrNot();
		mpBold = Typeface.createFromAsset(getAssets(),
				"fonts/MinionPro_Bold.ttf");
		mpSemiBold = Typeface.createFromAsset(getAssets(),
				"fonts/MinionPro_SemiBold.ttf");
		mpHardBold = Typeface.createFromAsset(getAssets(),
				"fonts/Gobold Uplow.ttf");
		mpKoll = Typeface.createFromAsset(getAssets(), "fonts/SLC_.ttf");
		approvedMemeberList = new ArrayList<ArrayList<String>>();
		churchMemeberList = new ArrayList<ArrayList<String>>();
		databaseHelper = new PzDatabaseHelper(getApplicationContext(),
				com.mcm.database.AppConstant.DB_NAME, null, 1);
		url = "http://mcmwebapi.victoriatechnologies.com/api/MembershipTypes";
		mpProgressDialog = new ProgressDialog(SplashActivity.this);

	}

	private void startAsynTask() {
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			Log.e("Internet ", "IS present");
			new SplashAsynTask(SplashActivity.this, url, mpProgressDialog)
					.execute("");
		} else {
			Toast.makeText(SplashActivity.this,
					"You don't have internet connection", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void createTable() {
		MCMDatabase databaseTable = new MCMDatabase(SplashActivity.this);
		databaseTable.createdatabase();
	}

	private void startRegisterActivity() {
		final SplashActivity splash = this;
		splashThreaad = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						// wait given period of time or exit on touch
						wait(3000);

					}
				}

				catch (InterruptedException ex) {

				}

				finish();

				// Run the next Activity
				Intent intent = new Intent();
				intent.setClass(splash, HomeActivity.class);
				startActivity(intent);
				// stop();
			}

		};
		splashThreaad.start();

	}

	

	private void checkAppicationisRegisteredOrNot() {
		sharedPreferences = this.getSharedPreferences("com.example.app",
				HomeActivity.MODE_PRIVATE);
		msgREGID = sharedPreferences.getString(
				com.mcm.appconstant.AppConstant.KEYS_REGISTRATION, "");
		if (msgREGID.equals("")) {
			Log.e("FIRST TIME REGISTRATION", "AAAAA");
			GCMRegistrar.register(SplashActivity.this,
					GCMIntentService.SENDER_ID);
		} else {
			Log.e("ALREADY GOT REGID", "" + msgREGID);
		}

	}

}