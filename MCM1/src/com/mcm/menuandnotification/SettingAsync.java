package com.mcm.menuandnotification;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Spinner;

import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.internal.co;
import com.mcm.GCMIntentService;
import com.mcm.SplashActivity;
import com.mcm.appconstant.AppConstant;
import com.mcm.home.HomeActivity;
import com.mcm.library.GetDataFromApi;
import com.mcm.library.SettingSavedMemberDetail;
import com.mcm.library.SettingSavedPostJson;
import com.mcm.spinneradapter.SettingScreenSpinnerAdapter;

public class SettingAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String message, url, login_member_first_name, login_member_sur_name,
			login_member_date_of_birth, login_member_profeesional_id,
			login_member_marital_status_id, login_member_number_of_children,
			login_member_nationality_id, login_member_sex,
			login_member_mobile_number, login_member_email, login_member_password;
	int memberID, clientID, login_member_church_member_ship_id;
	ArrayList<String> proffesionList;

	String msgREGID = "";
	String monthOFCalender;
	SharedPreferences sharedPreferences;

	public SettingAsync(Context context, String url,
			ProgressDialog mProgressDialog, ArrayList<String> proffesionList,
			int memberID, int clientID, String login_member_first_name,
			String login_member_sur_name, String login_member_date_of_birth,
			String login_member_profeesional_id,
			String login_member_marital_status_id,
			String login_member_number_of_children,
			String login_member_nationality_id,
			int login_member_church_member_ship_id, String login_member_sex,
			String login_member_mobile_number, String login_member_email, String login_member_password, String monthOFCalender) {
		this.context = context;
		this.url = url;
		this.mProgressDialog = mProgressDialog;
		this.proffesionList = proffesionList;
		this.memberID = memberID;
		this.clientID = clientID;
		this.login_member_first_name = login_member_first_name;
		this.login_member_sur_name = login_member_sur_name;
		this.login_member_date_of_birth = login_member_date_of_birth;
		this.login_member_profeesional_id = login_member_profeesional_id;
		this.login_member_marital_status_id = login_member_marital_status_id;
		this.login_member_number_of_children = login_member_number_of_children;
		this.login_member_nationality_id = login_member_nationality_id;
		this.login_member_church_member_ship_id = login_member_church_member_ship_id;
		this.login_member_sex = login_member_sex;
		this.login_member_mobile_number = login_member_mobile_number;
		this.login_member_email = login_member_email;
		this.login_member_password = login_member_password;
		this.monthOFCalender = monthOFCalender;
		Log.e("MONTH OF CALENDER", "" + monthOFCalender);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
		callLogin();
		return null;
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		onSuccesFull();
	}

	private void onSuccesFull() {
		message = message.replace("\"", "");
		showYesNoDialog(message);
	}

	private void showYesNoDialog(String showMessage) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle("Message");
		alertDialog.setMessage("Setting Saved succesfully");
		alertDialog.setPositiveButton("Ok", dialogClickListener);
		// alertDialog.setNegativeButton("No", dialogClickListenerForCodeMAtch);
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialogInterface, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				dialogInterface.dismiss();
				context.startActivity(new Intent(context, HomeActivity.class));
				((Activity) context).finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				dialogInterface.dismiss();
				break;
			}
		}
	};

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
//		message = new SettingSavedPostJson(url, memberID, proffesionList)
//				.postDataToServer();
		if (login_member_sex.trim().equals("Female")) {
			login_member_sex = "F";
		} else {
			login_member_sex = "M";
		}
		String url1 = "http://mcmwebapi.victoriatechnologies.com/api/Member/UpdateMember?memberId=" + memberID;
		message = new SettingSavedMemberDetail(url1, memberID, clientID, login_member_first_name, login_member_sur_name, login_member_date_of_birth, login_member_profeesional_id, login_member_marital_status_id, login_member_number_of_children, login_member_nationality_id, login_member_church_member_ship_id, login_member_sex, login_member_mobile_number, login_member_email, login_member_password, monthOFCalender).postDataToServer();

	}
}
