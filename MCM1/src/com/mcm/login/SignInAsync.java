package com.mcm.login;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.SplashActivity;
import com.mcm.database.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.library.GetDataFromApi;

public class SignInAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	ArrayList<String> myStrings;
	String url;
	boolean isTable;
	String firstname, lastname, email, password, message;

	public SignInAsync(Context context, ProgressDialog mProgressDialog,
			String url, boolean isTable) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.isTable = isTable;
		Log.e("MAY AYA", "" + "do in context");
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
		Log.e("MAY AYA", "" + "do in background in sigin");
		backgroundFunction();
		return null;
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		signInCondition();
	}

	void showDialog() {
		mProgressDialog.setMessage("Please Wait...updating form.");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	private void backgroundFunction()
	{
		if (!checkForMEmberTables()) {
			Log.e("IF CONDITION", "YES");
		} else {
			if (!checkForClientTable()) {
				Log.e("ELSE IF CONDITION", "YES");
				saveVlaueInInserTable();
			} else {
				Log.e("ELSE ELSE CONDITION", "YES");
			}
		}
	}

	public void saveVlaueInInserTable() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		ArrayList<ArrayList<String>> memberList = getDataFromDatabase
				.getAllRowsAsArrays();
		int member_id = Integer.parseInt(memberList.get(memberList.size() - 1)
				.get(0).toString());
		firstname = memberList.get(memberList.size() - 1).get(2).toString().trim();
		lastname = memberList.get(memberList.size() - 1).get(3).toString().trim();
		email = memberList.get(memberList.size() - 1).get(4).toString().trim();
		password = memberList.get(memberList.size() - 1).get(5).toString().trim();
		String churchListUrl = url + email;
		message = new GetDataFromApi(churchListUrl).postSignIn();
		try {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getReadableDatabase();
			InsertTable insertTable = new InsertTable(database);
			JSONArray jArray = new JSONArray(message.toString().trim());
			Log.e("JSON LENGHT ", "" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				int clien_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_ID);
				String church = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_NAME);
				insertTable.addRowforClientTable(clien_id, member_id,
						firstname, lastname, church, email, password);
			}
			Log.e("memberList", "" + SplashActivity.churchMemeberList); // update

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkForMEmberTables() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		return getDataFromDatabase.checkForTables();
	}

	private boolean checkForClientTable() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		return getDataFromDatabase.checkForClientTables();
	}

	private void signInCondition() {
		Log.e("BOOLEAN CONDITION", "" + checkForMEmberTables());
		Intent i = new Intent(context, LoginActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.putExtra(AppConstant.CHECK_TABLE, checkForMEmberTables());
		context.startActivity(new Intent(context,
				com.mcm.login.LoginActivity.class));
	}

}
