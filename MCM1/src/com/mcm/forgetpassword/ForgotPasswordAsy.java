package com.mcm.forgetpassword;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.SplashActivity;
import com.mcm.asynclass.SplashAsynTask;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.library.GetDataFromApi;
import com.mcm.registration.AlertMessage;

public class ForgotPasswordAsy extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String url;
	AlertMessage alertMessage;

	public ForgotPasswordAsy(Context context, ProgressDialog mProgressDialog,
			String url, AlertMessage alertMessage) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.alertMessage = alertMessage;
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

	private void callLogin() {
		// TODO Auto-generated method stub
		GetDataFromApi getDataFromApi = new GetDataFromApi(url);
		String changePassword = getDataFromApi.postSignIn();
		Log.e("CHANGPASSWORD", "" + changePassword);
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		ArrayList<ArrayList<String>> clientArrayList = getDataFromDatabase
				.getAllRowsAsArrays();
		ArrayList<ArrayList<String>> clientChurchList = getDataFromDatabase
				.getClientChurch();
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getReadableDatabase();
		InsertTable insertTable = new InsertTable(database);
		insertTable.deleteTable();
		for (int i = 0; i < clientArrayList.size(); i++) {
			String memberID = clientArrayList.get(i).get(0).toString().trim();
			String clientID = clientArrayList.get(i).get(1).toString().trim();
			String firstName = clientArrayList.get(i).get(2).toString().trim();
			String surName = clientArrayList.get(i).get(3).toString().trim();
			String emailIDString = clientArrayList.get(i).get(4).toString()
					.trim();
			String passWordString = changePassword.trim();
//			Log.e("MEMBER Id", "" + memberID);
//			Log.e("CLIENT Id", "" + clientID);
			insertTable.addRowforMemberTable(memberID, clientID, firstName,
					surName, emailIDString, passWordString);
		}
		for (int i = 0; i < clientChurchList.size(); i++) 
		{
			int clientID = Integer.parseInt(clientChurchList.get(i).get(0));
			int memberID = Integer.parseInt(clientChurchList.get(i).get(1));
			String firstName = clientChurchList.get(i).get(2).toString().trim();
			String lastName = clientChurchList.get(i).get(3).toString().trim();
			String churhList = clientChurchList.get(i).get(4).toString().trim();
			String email = clientChurchList.get(i).get(5).toString().trim();
			String password = changePassword;
//			Log.e("CLIENT MEMBER Id", "" + memberID);
//			Log.e("CLIENT CLIENT Id", "" + clientID);
			insertTable.addRowforClientTable(clientID, memberID, firstName, lastName, churhList, email, password);
		}
		
		
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		alertMessage.showMessage("Check your email to get temporary password",  "");
	}

	private void showDialog() {
		mProgressDialog.setMessage("Please Wait patiently....");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

}
