package com.mcm.menuandnotification;

import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.mcm.R;
import com.mcm.SplashActivity;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.library.GetDataFromApi;

public class RefreshAsyn extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	int client_ID;
	EventListAdaptor eventListAdaptor, eventListAdaptor1;
	ListView listView, upcomListView;
	String emailFiled, message, folderName;;
	Date currentDate;
	ArrayList<ArrayList<String>> eventList;
	ArrayList<ArrayList<String>> upComing_event_List;

	public RefreshAsyn(Context context, ProgressDialog mProgressDialog,
			String emailFiled, EventListAdaptor eventListAdaptor,
			EventListAdaptor eventListAdaptor1, ListView listView,
			ListView upcomListView, int client_ID, String folderName) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.emailFiled = emailFiled;
		this.eventListAdaptor = eventListAdaptor;
		this.eventListAdaptor1 = eventListAdaptor1;
		this.listView = listView;
		this.upcomListView = upcomListView;
		this.client_ID = client_ID;
		this.folderName = folderName;
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
		onsuccesfull();
	}

	private void onsuccesfull() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		eventList = getDataFromDatabase.GetAllEvents(client_ID);
		upComing_event_List = getDataFromDatabase.GetEvents(client_ID, true);
		currentDate = new Date(System.currentTimeMillis());
		eventListAdaptor = new EventListAdaptor(context, eventList,
				R.layout.event_fragement_row, folderName,currentDate,emailFiled,client_ID);
		listView.setAdapter(eventListAdaptor);
		eventListAdaptor1 = new EventListAdaptor(context, upComing_event_List,
				R.layout.event_fragement_row, folderName, currentDate, emailFiled,client_ID);
		upcomListView.setAdapter(eventListAdaptor1);

	}

	void showDialog() {
		mProgressDialog
				.setMessage("Sync data from Server is in progress. Please wait patiently....");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	private void callLogin() {
		saveVlaueInInsertTable();
	}

	public void saveVlaueInInsertTable() {

		String churchListUrl = "http://mcmwebapi.victoriatechnologies.com/api/Client?EmailId="
				+ emailFiled;
		message = new GetDataFromApi(churchListUrl).postSignIn();
        Log.e("HI MY BABY DOLL", "" + message);
		try {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getReadableDatabase();
			InsertTable insertTable = new InsertTable(database);
			insertTable.deleteEventsTableByClientId();
			JSONArray jArray = new JSONArray(message.toString().trim());
			Log.e("JSON LENGHT ", "" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				int clien_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_ID);
				insertValueInEventTable(clien_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showOKAleart(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", null).show();
	}

	private void insertValueInEventTable(int clientID) {
		String eventUrl = "http://mcmwebapi.victoriatechnologies.com/api/Event/GetEvents?ClientId="
				+ clientID;
		message = new GetDataFromApi(eventUrl).postSignIn();

		try {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getReadableDatabase();
			InsertTable insertTable = new InsertTable(database);
			JSONArray jArray = new JSONArray(message.toString().trim());
			// Log.e("JSON LENGHT ", "" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				int eventID = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.EVENT_ID);
				int id_client = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.EVENT_CLIENT_ID);
				String event_date_time = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_DATE_TIME);
				String dispalyStartDate = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_DISPALY_START_DATE);
				String dispalyEndDate = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_DISPALY_END_DATE);
				String eventTitle = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_TITLE);
				String event_short_desc = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_SHORT_DESC);
				String event_long_desc = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_LONG_DESC);
				boolean event_upcoming = jsonObject
						.getBoolean(com.mcm.appconstant.AppConstant.EVENT_UPCOMING);
				String location = jsonObject
						.getString(com.mcm.appconstant.AppConstant.EVENT_LOCATION);
				boolean reminder_Flag = jsonObject
						.getBoolean(com.mcm.appconstant.AppConstant.EVENT_SET_REMINDER_FLAG);
				
				insertTable.addRowforEventTable(eventID, id_client,
						event_date_time, dispalyStartDate, dispalyEndDate,
						eventTitle, event_short_desc, event_long_desc,
						event_upcoming,location, reminder_Flag);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
