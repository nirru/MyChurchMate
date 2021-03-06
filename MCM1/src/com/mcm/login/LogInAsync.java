package com.mcm.login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
//import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mcm.SplashActivity;
import com.mcm.appconstant.AppConstant;
import com.mcm.appconstant.RegistrationAppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.library.GetDataFromApi;
import com.mcm.library.PostJson;
import com.mcm.library.PostJsonLogin;
import com.mcm.spinneradapter.SpinnerAdapter;

public class LogInAsync extends AsyncTask<String, String, String> {

	ProgressDialog mProgressDialog;
	Context context;
	String url;
	PopulateChurchListOnValidating populateChurchListOnValidating;
	int loginValue = 5;
	boolean isTableFalse;
	SQLiteDatabase database;
	String email;
	String password;
	Spinner spinnerChurchMember;
	int clientid;
	String emailFiled, passwordField, firstname, lastname, message, reg_ID;
	String appLoginStatus;
	int status = 0;
	TextView tv_your_church;
	ArrayList<ArrayList<String>> menu_Church_Array = new ArrayList<ArrayList<String>>();

	public LogInAsync(Context context, ProgressDialog mProgressDialog,
			String url, String email, String password,
			PopulateChurchListOnValidating populateChurchListOnValidating,
			SQLiteDatabase database, Spinner spinnerChurchMember,
			boolean isTableFalse, String reg_ID, TextView tv_your_church) {
		this.context = context;
		this.mProgressDialog = mProgressDialog;
		this.url = url;
		this.email = email;
		this.password = password;
		this.database = database;
		this.spinnerChurchMember = spinnerChurchMember;
		this.isTableFalse = isTableFalse;
		this.populateChurchListOnValidating = populateChurchListOnValidating;
		this.reg_ID = reg_ID;
		this.tv_your_church = tv_your_church;
		// Log.e("MERA URL DEKH LO", "" + reg_ID);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {
		// Log.e("MAY AYA", "" + "do in background in sigin");
		callLogin();
		return null;
	}

	@Override
	protected void onPostExecute(String unused) {
		closeDialog();
		onSuccesFull();
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
		GetDataFromApi getDataFromApi = new GetDataFromApi(url);
		message = getDataFromApi.postSignIn().toString().trim();
		// Log.e("<<<<Messagw>>>>", "" + message);
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(message.toString().trim());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				appLoginStatus = jsonObject
						.getString(AppConstant.LOGIN_APP_REGISTRATION_SATUS);
			}
			if (appLoginStatus.trim().equals("Login Successful.")) {
				// Log.e("I AM IN", "" + "MY DONE");
				status = 2;
				String urlhbtfgh = "http://mcmwebapi.victoriatechnologies.com/api/Member";
				postmessageInLogin(urlhbtfgh, message);
				syncroniseDatabase(message);
				GetMemberMenuChurchThemeImage(email);
				createDirectory();
				saveVlaueInInsertTable();
				saveValueInMemberMenuTable();
			} else {
				// Log.e("I AM IN ELSE", "" + "MY DONE 222");
				status = 1;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void postmessageInLogin(String urlPost, String messagePost) {

		try {
			JSONArray jsonArray = new JSONArray(messagePost.toString().trim());

			JSONObject jsonObject = null;

			jsonObject = jsonArray.getJSONObject(0);
			String appPin = jsonObject.getString(AppConstant.LOGIN_CLIENT_ID);
			String fName = jsonObject.getString(AppConstant.LOGIN_FIRST_NAME);
			String sName = jsonObject.getString(AppConstant.LOGIN_SURNAME);
			String email = jsonObject.getString(AppConstant.LOGIN_EMAIL_ID);
			String passWord = jsonObject.getString(AppConstant.LOGIN_PASSWORD);
			String address_One = jsonObject
					.getString(AppConstant.LOGIN_ADDRESS_ONE);
			String address_Two = jsonObject
					.getString(AppConstant.LOGIN_ADDRESS_TWO);
			String street = jsonObject.getString(AppConstant.LOGIN_STREET);
			String town = jsonObject.getString(AppConstant.LOGIN_TOWN);
			String city = jsonObject.getString(AppConstant.LOGIN_CITY);
			String state = jsonObject.getString(AppConstant.LOGIN_STATE);
			String county = jsonObject.getString(AppConstant.LOGIN_COUNTY);
			String country = jsonObject.getString(AppConstant.LOGIN_COUNTRY);
			String postalCode = jsonObject
					.getString(AppConstant.LOGIN_POSTAL_CODE);
			String mobileNumber = jsonObject
					.getString(AppConstant.LOGIN_MOBILE);
			String sex = jsonObject.getString(AppConstant.LOGIN_SEX);
			String homeTelephone = jsonObject
					.getString(AppConstant.LOGIN_HOMETELEPHONE);
			String churMemeberShip_ID = jsonObject.getString(
					AppConstant.LOGIN_CHURCHMEMEBERSHIP_ID).toString();

			PostJson postJson = new PostJson(urlPost, appPin, fName, sName,
					email, passWord, address_One, address_Two, street, town,
					city, county, state, country, postalCode, mobileNumber,
					sex, 1, homeTelephone, "xyz", "Android", reg_ID, "Mobile");
			postJson.postDataToServer();

			// Log.e("LEngh is Not zero ", "" + postJson.postDataToServer());
			String urlpost1 = "http://mcmwebapi.victoriatechnologies.com/api/Member/PostMemberDeviceId?gcmDevice=Yes";
			PostJson postJson1 = new PostJson(urlpost1, appPin, fName, sName,
					email, passWord, address_One, address_Two, street, town,
					city, county, state, country, postalCode, mobileNumber,
					sex, 1, homeTelephone, "xyz", "Android", reg_ID, "Mobile");
			postJson1.postDataToServer();
			// Log.e("LEngh is Not zero ", "" + postJson1.postDataToServer());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void GetMemberMenuChurchThemeImage(String email2) {
		String apiTogetMemberChurchThemeImages = "http://mcmwebapi.victoriatechnologies.com/api/Member/GetMemberChurchThemeImages?EmailId="
				+ email2;
		message = new GetDataFromApi(apiTogetMemberChurchThemeImages)
				.postSignIn();

		// Log.e("<<<DVADYAD>>>", "" + message);
		JSONArray jArray;
		try {
			jArray = new JSONArray(message.toString().trim());
			for (int i = 0; i < jArray.length(); i++) {
				ArrayList<String> data = new ArrayList<String>();
				JSONObject jsonObject = jArray.getJSONObject(i);
				int clien_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID);
				String client_Name = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_NAME);
				String church_Theme_Image_Url = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_THEME_URL);
				data.add("" + clien_id);
				data.add("" + client_Name);
				data.add("" + church_Theme_Image_Url);

				menu_Church_Array.add(data);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void saveVlaueInInsertTable() {

		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		ArrayList<ArrayList<String>> memberList = getDataFromDatabase
				.getAllRowsAsArrays();
		int member_id = Integer.parseInt(memberList.get(memberList.size() - 1)
				.get(0).toString());
		firstname = memberList.get(memberList.size() - 1).get(2).toString()
				.trim();
		lastname = memberList.get(memberList.size() - 1).get(3).toString()
				.trim();
		emailFiled = memberList.get(memberList.size() - 1).get(4).toString()
				.trim();
		passwordField = memberList.get(memberList.size() - 1).get(5).toString()
				.trim();
		String churchListUrl = "http://mcmwebapi.victoriatechnologies.com/api/Client?EmailId="
				+ emailFiled;
		message = new GetDataFromApi(churchListUrl).postSignIn();

		try {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getReadableDatabase();
			InsertTable insertTable = new InsertTable(database);
			JSONArray jArray = new JSONArray(message.toString().trim());
			// Log.e("JSON LENGHT ", "" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				int clien_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_ID);
				String church = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MEMEBER_TAG_NAME);
				insertTable.addRowforClientTable(clien_id, member_id,
						firstname, lastname, church, emailFiled, passwordField);
				// Log.e("<<<<<<<<<CLIENT ID >>>>>>", "" + clien_id);
				insertValueInEventTable(clien_id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveValueInMemberMenuTable() {
		String churchListUrl = "http://mcmwebapi.victoriatechnologies.com/api/MemberAppMenu/MemberAppMenu?EmailId="
				+ email;
		message = new GetDataFromApi(churchListUrl).postSignIn();
		try {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getReadableDatabase();
			InsertTable insertTable = new InsertTable(database);
			JSONArray jArray = new JSONArray(message.toString().trim());
			// Log.e("JSON LENGHT ", "" + jArray.length());
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject jsonObject = jArray.getJSONObject(i);
				int clien_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID);
				int member_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MENU_MEMBER_ID);
				int menu_App_id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MENU_APP_MENU_ID);
				String church_Menu_Client_Name = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_NAME);
				String appMenuName = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MENU_APP_MENU_NAME);
				int display_Order_Id = jsonObject
						.getInt(com.mcm.appconstant.AppConstant.CHURCH_MENU_APP_DISPLAY_ORDER_ID);
				String theme_Image_Url_Path = jsonObject
						.getString(com.mcm.appconstant.AppConstant.CHURCH_MENU_APP_THEME_IMAGE_URL_PATH);
				insertTable.addRowforChurchMenuTable(clien_id, member_id,
						menu_App_id, church_Menu_Client_Name, appMenuName,
						display_Order_Id, theme_Image_Url_Path);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void syncroniseDatabase(String mesString) {
		InsertTable insertTable = new InsertTable(database);
		try {
			JSONArray jsonArray = new JSONArray(mesString);
			// Log.e("LEngh is Not zero ", "" + jsonArray.length());
			JSONObject jsonObject = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject = jsonArray.getJSONObject(i);
				insertTable
						.addRowforMemberTable(
								jsonObject
										.getString(RegistrationAppConstant.MEMBER_ID),
								jsonObject
										.getString(RegistrationAppConstant.CLIENT_ID),
								jsonObject
										.getString(RegistrationAppConstant.FIRSTNAME),
								jsonObject
										.getString(RegistrationAppConstant.SURNAME),
								jsonObject
										.getString(RegistrationAppConstant.EMAIL_ID),
								jsonObject
										.getString(RegistrationAppConstant.PASSWORD));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public void addItemsOnChurchMemberShipTypeSpinner(
			ArrayList<ArrayList<String>> churchList) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < churchList.size(); i++) {
			list.add(churchList.get(i).get(4));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinnerChurchMember.setAdapter(dataAdapter);
		spinnerChurchMember.setPrompt("Church Center");
		spinnerChurchMember.setEnabled(true);
	}

	private void onSuccesFull() {
		if (status == 1) {
			showOKAleart("Message", appLoginStatus);

		} else {
			ArrayList<ArrayList<String>> clientChurchList = new GetDataFromDatabase()
					.getClientChurch();
			if (clientChurchList.size() == 1) {
				populateChurchListOnValidating.populateChurchList(
						clientChurchList, menu_Church_Array);
				addItemsOnChurchMemberShipTypeSpinner(clientChurchList);
				clientid = Integer.parseInt(clientChurchList.get(
						clientChurchList.size() - 1).get(0));
				// Log.e("CLIENT ID IN LOGIN ASY WAY", "" + clientid);
				putValueInPrefs();
				Intent i = new Intent(context,
						com.mcm.menuandnotification.MenuAndNotification.class);
				i.putExtra(
						com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID,
						clientid);
				i.putExtra(com.mcm.appconstant.AppConstant.EMAIL_ID, email);
				i.putExtra(
						com.mcm.appconstant.AppConstant.NOTIFICATION_SURVEY_FLAG,
						0);
				i.putExtra(
						com.mcm.appconstant.AppConstant.NOTIFICATION_PUSH_NOTIFICATION_ID,
						0);
				i.putExtra(
						com.mcm.appconstant.AppConstant.NOTIFICATION_DETAILS,
						"");
				context.startActivity(i);
				((Activity) context).finish();

			} else {
				spinnerChurchMember.setVisibility(View.VISIBLE);
				tv_your_church.setVisibility(View.VISIBLE);
				populateChurchListOnValidating.populateChurchList(
						clientChurchList, menu_Church_Array);
				SpinnerAdapter spinnerAdapter = new SpinnerAdapter(context,
						clientChurchList, true);
				spinnerChurchMember.setAdapter(spinnerAdapter);
				showOKAleart("Message",
						"Please select the Church from the Chruch dropdown  and click on login button.");
			}

		}
	}

	public void showOKAleart(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", null).show();
	}

	private void createDirectory() {

		ArrayList<String> data_church = new ArrayList<String>();
		for (int i = 0; i < menu_Church_Array.size(); i++) {
			data_church.add(menu_Church_Array.get(i).get(1).toString().trim());
		}
		HashSet<String> hs = new HashSet<String>();
		hs.addAll(data_church);
		data_church.clear();
		data_church.addAll(hs);

		for (int i = 0; i < data_church.size(); i++) {
			String folder = data_church.get(i).toString().trim();
			File myFile = new File(context.getFilesDir(), "/Images/" + folder
					+ "/" + "ThemeImages/");
			myFile.mkdirs();

			for (int j = 0; j < menu_Church_Array.size(); j++) {
				String urlT = menu_Church_Array.get(j).get(2).toString().trim();
				String substr = urlT.substring(urlT.indexOf("UploadedImages/"));
				//
				String churchName = substr.replace("UploadedImages/", "")
						.trim();
				// Log.e("Church name", "" + churchName);

				createfileInDirectory(churchName, urlT, folder);
			}
		}
	}

	public boolean createfileInDirectory(String fileName, String muUrl,
			String folder) {

		try {
			OutputStream stream = new FileOutputStream(new File(
					context.getFilesDir(), "/Images/" + folder + "/"
							+ "ThemeImages/" + fileName));

			// if (fileName.equals("Settings.png") ||
			// fileName.equals("Chat.png") ||
			// fileName.equals("NotificationsSelected .png")) {
			// // do nothing
			// } else {
			// Bitmap bitmap = BitmapFactory
			// .decodeStream((InputStream) new URL(muUrl).getContent());
			// writeToFile(bitmap, fileName, folder);
			// }

			Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
					muUrl).getContent());

			// Log.e("BITMAP not null", "" + bitmap);
			writeToFile(bitmap, fileName, folder);

			// stream.write(content);
			stream.flush();
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException("Failed to create", e);
		}
		return true;
	}

	private void writeToFile(Bitmap bitmapTo, String filename, String folder) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(context.getFilesDir(),
					"/Images/" + folder + "/" + "ThemeImages/" + filename));
			bitmapTo.compress(Bitmap.CompressFormat.PNG, 90, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Throwable ignore) {
			}
		}
	}

	private void insertValueInEventTable(int clientID) {
		String eventUrl = "http://mcmwebapi.victoriatechnologies.com/api/Event/GetEvents?ClientId="
				+ clientID;
		message = new GetDataFromApi(eventUrl).postSignIn();
		Log.e("EVENT CLIENT TABLE ENTYRY", "" + message);

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
						event_upcoming, location, reminder_Flag);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void putValueInPrefs() {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				"com.example.app", Context.MODE_PRIVATE).edit();
		prefs.putBoolean(AppConstant.PREFS_KEYS, true);
		prefs.putInt(AppConstant.PREFS_KEYS_CLIENT_ID, clientid);
		prefs.putString(com.mcm.appconstant.AppConstant.EMAIL_ID, email
				.toString().trim());
		prefs.commit();
	}
}
