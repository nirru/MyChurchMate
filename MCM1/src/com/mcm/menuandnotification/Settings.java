package com.mcm.menuandnotification;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mcm.R;
import com.mcm.R.color;
import com.mcm.SplashActivity;
import com.mcm.appconstant.AppConstant;
import com.mcm.datepicker.InterfaceDateToPass;
import com.mcm.datepicker.UiDatePicker;
import com.mcm.listener.RegisterClickListner;
import com.mcm.spinneradapter.SettingScreenSpinnerAdapter;

public class Settings extends Fragment implements InterfaceDateToPass {

	Context context;
	UiDatePicker uiDatePicker;
	int clientID;
	String folderName;
	TextView errorText;
	RelativeLayout notifi_fragement_background;
	TextView tv_text_one, tv_text_two, tv_text_three, tv_title;
	EditText editFName, editSName, editchildren, editDOB, editMobile,
			editHomeTelephone;
	LinearLayout layout_one, layout_two, layout_three;

	Button btnNext, btnPrevious, btnSubmit, btnPrevious_StepThree,
			btnSubmit_StepThree;

	RelativeLayout nextRelative, bottomRelative, bottomRelative_Step_Three;
	LinearLayout linear_qqw;
	ScrollView scrollView1, scrollView2;
	ListView listView;
	TextView step_One, step_Two, step_Three;

	String monthOFCalender;

	Spinner la_profession, la_marital_status, la_nationality,
			spSelectChurchMembership, spMaleFemale;
	ArrayList<ArrayList<String>> proffesionList, maritalStatusList,
			CountryList, churchMemberList, interestList, interestCheckList;

	int login_member_member_id, login_member_client_id,
			login_member_church_member_ship_id;
	String login_member_first_name, login_member_sur_name,
			login_member_date_of_birth, login_member_sex,
			login_member_mobile_number, login_member_profeesional_id,
			login_member_marital_status_id, login_member_nationality_id,
			login_member_number_of_children;

	ProgressDialog mpProgressDialog;

	SettingAsync settingAsync;
	SettingScreenSpinnerAdapter typeSpinnerAdapter;
	SettingsListAdaptor settingsListAdaptor;

	int memberID = 265;
	String email, password;

	public Settings(Context context, int clientID, String folderName) {

		this.clientID = clientID;
		this.folderName = folderName;
		this.context = context;
		uiDatePicker = new UiDatePicker(context, false, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragement_custom_setting,
				container, false);

		init(rootView);

		setBackground("EventsBG.png", folderName, notifi_fragement_background);

		getMemeberIdFromClientId();

		PreviousHide();

		fetchingDataFromServer();

		return rootView;
	}

	private void init(View rootView) {

		mpProgressDialog = new ProgressDialog(context);
		proffesionList = new ArrayList<ArrayList<String>>();
		maritalStatusList = new ArrayList<ArrayList<String>>();
		CountryList = new ArrayList<ArrayList<String>>();
		churchMemberList = new ArrayList<ArrayList<String>>();
		interestList = new ArrayList<ArrayList<String>>();
		interestCheckList = new ArrayList<ArrayList<String>>();

		notifi_fragement_background = (RelativeLayout) rootView
				.findViewById(R.id.fragement_setting_imageview);

		btnNext = (Button) rootView.findViewById(R.id.ra_btnNextStep2);
		btnSubmit = (Button) rootView.findViewById(R.id.ra_btnSubmit);
		btnPrevious = (Button) rootView.findViewById(R.id.ra_btnprevious);
		btnPrevious_StepThree = (Button) rootView
				.findViewById(R.id.ra_btnprevious_stepthree);
		btnSubmit_StepThree = (Button) rootView
				.findViewById(R.id.ra_btnSubmit_stepthree);

		nextRelative = (RelativeLayout) rootView.findViewById(R.id.next_ID);
		bottomRelative = (RelativeLayout) rootView.findViewById(R.id.bootom_ID);
		bottomRelative_Step_Three = (RelativeLayout) rootView
				.findViewById(R.id.bootom_submit_id);
		linear_qqw = (LinearLayout) rootView.findViewById(R.id.linear_qqw);

		scrollView1 = (ScrollView) rootView.findViewById(R.id.scrollView1);
		scrollView2 = (ScrollView) rootView.findViewById(R.id.scrollView2);
		listView = (ListView) rootView.findViewById(R.id.listView1);
		errorText = (TextView) rootView.findViewById(R.id.ra_tvFormMsg);

		editDOB = (EditText) rootView.findViewById(R.id.ra_editEmail);
		editFName = (EditText) rootView.findViewById(R.id.ra_editFName);
		editSName = (EditText) rootView.findViewById(R.id.ra_editSName);
		editMobile = (EditText) rootView.findViewById(R.id.ra_editMobile);
		editHomeTelephone = (EditText) rootView
				.findViewById(R.id.ra_editHomeTelephone);
		editchildren = (EditText) rootView.findViewById(R.id.ra_editchildren);

		step_One = (TextView) rootView.findViewById(R.id.ra_Step1);
		step_Two = (TextView) rootView.findViewById(R.id.ra_Step2);
		step_Three = (TextView) rootView.findViewById(R.id.ra_Step3);

		spSelectChurchMembership = (Spinner) rootView
				.findViewById(R.id.ra_spSelectChurchMembership);
		spMaleFemale = (Spinner) rootView.findViewById(R.id.ra_spMaleFemale);
		la_profession = (Spinner) rootView.findViewById(R.id.la_profeesion);
		la_marital_status = (Spinner) rootView
				.findViewById(R.id.la_maritalstatus);
		la_nationality = (Spinner) rootView.findViewById(R.id.la_nationality);

		btnNext.setOnClickListener(registerClkListener);
		btnSubmit.setOnClickListener(registerClkListener);
		btnPrevious.setOnClickListener(registerClkListener);
		btnPrevious_StepThree.setOnClickListener(registerClkListener);
		btnSubmit_StepThree.setOnClickListener(registerClkListener);
		editDOB.setOnClickListener(registerClkListener);
		editDOB.setKeyListener(null);
	}
	
	public Bitmap setBackground(String filename, String folder,
			RelativeLayout imageView) {
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(
					context.getFilesDir(), "/Images/" + folder + "/"
							+ "ThemeImages/" + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			imageView.setBackgroundDrawable(d);
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
		return thumbnail;
	}

	private void PreviousHide() {
		nextRelative.setVisibility(View.VISIBLE);
		bottomRelative.setVisibility(View.INVISIBLE);
		bottomRelative_Step_Three.setVisibility(View.INVISIBLE);
		scrollView1.setVisibility(View.VISIBLE);
		scrollView2.setVisibility(View.INVISIBLE);
		linear_qqw.setVisibility(View.INVISIBLE);
		errorText.setVisibility(View.INVISIBLE);
		step_One.setBackgroundColor(getResources().getColor(color.bluedark));
		step_Two.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Three.setBackgroundColor(getResources().getColor(color.bluelight));
	}

	private void nextHide() {
		nextRelative.setVisibility(View.INVISIBLE);
		bottomRelative_Step_Three.setVisibility(View.INVISIBLE);
		bottomRelative.setVisibility(View.VISIBLE);
		scrollView1.setVisibility(View.INVISIBLE);
		linear_qqw.setVisibility(View.INVISIBLE);
		scrollView2.setVisibility(View.VISIBLE);
		errorText.setVisibility(View.INVISIBLE);
		step_Two.setBackgroundColor(getResources().getColor(color.bluedark));
		step_One.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Three.setBackgroundColor(getResources().getColor(color.bluelight));
	}

	private void nextHideStepThree() {
		nextRelative.setVisibility(View.INVISIBLE);
		bottomRelative.setVisibility(View.VISIBLE);
		bottomRelative_Step_Three.setVisibility(View.VISIBLE);
		scrollView1.setVisibility(View.INVISIBLE);
		scrollView2.setVisibility(View.INVISIBLE);
		linear_qqw.setVisibility(View.VISIBLE);
		errorText.setVisibility(View.INVISIBLE);
		step_One.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Two.setBackgroundColor(getResources().getColor(color.bluelight));
		step_Three.setBackgroundColor(getResources().getColor(color.bluedark));
	}

	RegisterClickListner registerClkListener = new RegisterClickListner() {

		@Override
		public void onstepTwoNextBtnClk(View view) {
			onStepTwoNextValidation();
		}

		@Override
		public void onNextBtnClk(View view) {
			ValidationForNextButton();
		}

		@Override
		public void onPreviousBtnClk(View view) {
			PreviousHide();
		}

		@Override
		public void onStepThreePreviousBtnClk(View view) {
			// TODO Auto-generated method stub
			nextHide();
		}

		@Override
		public void onStepThreeSubmitBtnClk(View view) {
			savedToServerDatabase();
		}

		@Override
		public void onCountryBtnClk(View view) {
		}

		@Override
		public void onDateOfBirthClick(View view) {
			// TODO Auto-generated method stub
			uiDatePicker.showDialog(view, "-");

		}
	};

	private void onStepTwoNextValidation() {
		if (!checkChurchMemberShip())
			return;
		if (!checkMobileNumber())
			return;
		else {
			nextHideStepThree();
		}
	}

	private void ValidationForNextButton() {
		if (!checkFirstName())
			return;
		if (!checkSurName())
			return;
		if (!checkDob())
			return;
		if (!checkProfession())
			return;
		if (!checkMaritalStatus())
			return;
		if (!checkChildren())
			return;
		if (!checknatioanlity())
			return;
		else {
			nextHide();
		}
	}

	private void showSpinnerData(String message,
			ArrayList<ArrayList<String>> value, Spinner spinner, String id) {

		typeSpinnerAdapter = new SettingScreenSpinnerAdapter(context, value,
				message, id);
		spinner.setAdapter(typeSpinnerAdapter);

	}

	private void fetchingDataFromServer() {

		String url4 = "http://mcmwebapi.victoriatechnologies.com/api/MemberInterest/GetAllInterests";
		getIntrestList(url4, interestList);

		String url5 = "http://mcmwebapi.victoriatechnologies.com/api/Member?clientid="
				+ clientID + "&memberId=" + memberID;
		getDetailByClientIdAndMemberId(url5);

		// String url =
		// "http://mcmwebapi.victoriatechnologies.com/api/Nationality/GetNationality";
		// getProfessionList(url, "Nationality", CountryList, la_nationality,
		// login_member_nationality_id);
		//
		// String url1 =
		// "http://mcmwebapi.victoriatechnologies.com/api/MaritalStatus/GetMaritalStatus";
		// getProfessionList(url1, "Marital Status", maritalStatusList,
		// la_marital_status, login_member_marital_status_id);
		//
		// String url2 =
		// "http://mcmwebapi.victoriatechnologies.com/api/Profession/GetProfession";
		// getProfessionList(url2, "Prosfession", proffesionList, la_profession,
		// login_member_profeesional_id);
		//
		// String url3 =
		// "http://mcmwebapi.victoriatechnologies.com/api/MembershipTypes";
		// getProfessionList(url3, "Select Church Membership", churchMemberList,
		// spSelectChurchMembership,String.valueOf(login_member_church_member_ship_id));

	}

	private void getProfessionList(String url, final String defaultmessage,
			final ArrayList<ArrayList<String>> value, final Spinner spinner1,
			final String id) {

		showDialog();

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				closeDialog();
				prepareArrayListOfProfeesion(response, value);
				showSpinnerData(defaultmessage, value, spinner1, id);
			}
		});
	}

	private void prepareArrayListOfProfeesion(String responseMessage,
			ArrayList<ArrayList<String>> mtList) {
		JSONObject jsonObject = null;
		try {
			JSONArray jsonArray = new JSONArray(responseMessage);
			for (int i = 0; i < jsonArray.length(); i++) {
				ArrayList<String> data = new ArrayList<String>();
				jsonObject = jsonArray.getJSONObject(i);
				data.add(jsonObject.getString(AppConstant.SETTING_ID));
				data.add(jsonObject.getString(AppConstant.SETTING_NAME));
				mtList.add(data);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void prepareArrayListOfIntrest(String responseMessage,
			ArrayList<ArrayList<String>> mtList) {
		JSONObject jsonObject = null;
		try {
			JSONArray jsonArray = new JSONArray(responseMessage);
			for (int i = 0; i < jsonArray.length(); i++) {
				ArrayList<String> data = new ArrayList<String>();
				jsonObject = jsonArray.getJSONObject(i);
				data.add(jsonObject
						.getString(AppConstant.SETTING_MEMBER_INTREST_ID));
				data.add(jsonObject
						.getString(AppConstant.SETTING_MEMBER_INTREST_NAME));
				mtList.add(data);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void getIntrestList(String url,
			final ArrayList<ArrayList<String>> value) {

		showDialog();

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				closeDialog();
				// Log.e("INTREST LIST ", "" + response);
				prepareArrayListOfIntrest(response, value);

				String url5 = "http://mcmwebapi.victoriatechnologies.com/api/MemberInterest/GetInterestsAssignedToMember?MemberId="
						+ memberID;
				getIntrestListForCheckedItem(url5, interestCheckList);
			}
		});
	}

	private void getIntrestListForCheckedItem(String url,
			final ArrayList<ArrayList<String>> value) {

		showDialog();

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				closeDialog();
				// Log.e("INTREST CHCKED LIST", "" + response);
				prepareArrayListOfIntrest(response, value);
				// Log.e("INTREST LIST SIZE", "" + interestList.size());
				settingsListAdaptor = new SettingsListAdaptor(context,
						interestList, interestCheckList, memberID,
						R.layout.setting_custom_row);
				listView.setAdapter(settingsListAdaptor);
			}
		});
	}

	private void getDetailByClientIdAndMemberId(String url) {

		showDialog();

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
				// closeDialog();
				// Log.e("CLIENT ID AND MEMBER ID LIT", "" + response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					login_member_member_id = jsonObject
							.getInt(AppConstant.SETTING_MEMBER_ID);
					login_member_client_id = jsonObject
							.getInt(AppConstant.SETTING_CLIENT_ID);
					login_member_first_name = jsonObject
							.getString(AppConstant.SETTING_FIRST_NAME);
					login_member_sur_name = jsonObject
							.getString(AppConstant.SETTING_SURNAME);
					login_member_date_of_birth = jsonObject
							.getString(AppConstant.SETTING_DATE_OF_BIRTH);
					login_member_profeesional_id = jsonObject
							.getString(AppConstant.SETTING_PROFESSIONAL_ID);
					login_member_marital_status_id = jsonObject
							.getString(AppConstant.SETTING_MARITAL_ID);
					login_member_nationality_id = jsonObject
							.getString(AppConstant.SETTING_NATIONALITY_ID);
					login_member_church_member_ship_id = jsonObject
							.getInt(AppConstant.SETTING_CHURCH_MEMBERSHIP_ID);
					login_member_sex = jsonObject
							.getString(AppConstant.SETTING_SEX);
					login_member_mobile_number = jsonObject
							.getString(AppConstant.SETTING_MOBILE_NUMBER);

					// Log.e("  TAG  ", "  " + login_member_member_id + " , "
					// + login_member_client_id + " , "
					// + login_member_first_name + ","
					// + login_member_sur_name + ", "
					// + login_member_profeesional_id + " , "
					// + login_member_marital_status_id + " , "
					// + login_member_nationality_id + ", "
					// + login_member_mobile_number);
					// Log.e("COUNT LOGIN MEBERID", "" +
					// login_member_nationality_id.length());
					// Log.e("null.lenght()", "" + "null".length());
					// if (login_member_nationality_id == "null" ) {
					// Log.e("HA MAI HOO", "YES");
					// } else {
					// Log.e("HA MAI NAHI HOO", "YES");
					// }
					String url = "http://mcmwebapi.victoriatechnologies.com/api/Nationality/GetNationality";
					getProfessionList(url, "Nationality", CountryList,
							la_nationality, login_member_nationality_id);

					String url1 = "http://mcmwebapi.victoriatechnologies.com/api/MaritalStatus/GetMaritalStatus";
					getProfessionList(url1, "Marital Status",
							maritalStatusList, la_marital_status,
							login_member_marital_status_id);

					String url2 = "http://mcmwebapi.victoriatechnologies.com/api/Profession/GetProfession";
					getProfessionList(url2, "Prosfession", proffesionList,
							la_profession, login_member_profeesional_id);

					String url3 = "http://mcmwebapi.victoriatechnologies.com/api/MembershipTypes";
					getProfessionList(
							url3,
							"Select Church Membership",
							churchMemberList,
							spSelectChurchMembership,
							Integer.toString(login_member_church_member_ship_id));
					settextOnGettingResponse();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void settextOnGettingResponse() {
		editFName.setText(login_member_first_name);
		editSName.setText(login_member_sur_name);
		if (login_member_date_of_birth.trim().equals("null")) {
			editDOB.setHint("Date of birth");
		} else {
			editDOB.setText(convertstringTodate(login_member_date_of_birth));
		}

	}

	private String convertstringTodate(String date_String) {

		String dtStart = date_String;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(dtStart);
			dtStart = getMonthYear(date, dtStart);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("DATE HOUR", "" + date);

		return dtStart;
	}

	private String getMonthYear(Date date, String dateTimeString) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String month1 = (String) android.text.format.DateFormat.format("MMM",
				date);
		String day1 = (String) android.text.format.DateFormat
				.format("dd", date); // 20
		String year1 = (String) android.text.format.DateFormat.format("yyyy",
				date); // 20
		String textdate = day1 + "-" + month1 + "-" + year1;

		return textdate;
	}

	void showDialog() {
		mpProgressDialog
				.setMessage("Login Successful.  Sync data from Server is in progress. Please wait patiently....");
		mpProgressDialog.setCancelable(false);
		mpProgressDialog.show();
	}

	void closeDialog() {
		if (mpProgressDialog.isShowing()) {
			mpProgressDialog.dismiss();
		}
	}

	private void getMemeberIdFromClientId() {

		SQLiteDatabase sqLiteDatabase = SplashActivity.databaseHelper
				.getReadableDatabase();
		// String query = "SELECT "
		// + com.mcm.database.AppConstant.MCM_MEMBER_MEMEBER_ID + " FROM "
		// + com.mcm.database.AppConstant.MEMBER_TABLE_NAME + " WHERE "
		// + com.mcm.database.AppConstant.MCM_MEMBER_CLIENT_ID + " ='"
		// + clientID + "'";

		String query = " SELECT * " + " FROM "
				+ com.mcm.database.AppConstant.MEMBER_TABLE_NAME + " WHERE "
				+ com.mcm.database.AppConstant.MCM_MEMBER_CLIENT_ID + " ='"
				+ clientID + "'";

		Cursor cursor = sqLiteDatabase.rawQuery(query, null);

		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			do {
				memberID = cursor.getInt(0);
				email = cursor.getString(4);
				password = cursor.getString(5);
				// Log.e("MEMEBER IDDD", " -- " + memberID + "  EMAIL  --" +
				// email
				// + "  password  --" + password);

			} while (cursor.moveToNext());
			cursor.close();
		}
	}

	private void savedToServerDatabase() {
		login_member_profeesional_id = proffesionList
				.get((int) la_profession.getSelectedItemId()).get(0).toString();
		login_member_marital_status_id = maritalStatusList
				.get((int) la_marital_status.getSelectedItemId()).get(0)
				.toString();
		login_member_nationality_id = CountryList
				.get((int) la_nationality.getSelectedItemId()).get(0)
				.toString();
		login_member_church_member_ship_id = Integer
				.parseInt(churchMemberList.get(
						(int) spSelectChurchMembership.getSelectedItemId())
						.get(0));
		login_member_sex = spMaleFemale.getSelectedItem().toString();
		// Log.e("login_member_profeesional_id", "" +
		// login_member_profeesional_id);
		// Log.e("login_member_marital_status_id", ""
		// + login_member_marital_status_id);
		// Log.e("login_member_nationality_id", "" +
		// login_member_nationality_id);
		// Log.e("login_member_church_member_ship_id", ""
		// + login_member_church_member_ship_id);
		// Log.e("login_member_sex", "" + login_member_sex);
		// Log.e("EMAIL INNN", "" + email);
		login_member_mobile_number = editMobile.getText().toString().trim();
		String url = "http://mcmwebapi.victoriatechnologies.com/api/MemberInterest/SaveMemberInterest";
		settingsListAdaptor.getListSaved();
		new SettingAsync(context, url, mpProgressDialog,
				settingsListAdaptor.getListSaved(), memberID, clientID,
				login_member_first_name, login_member_sur_name,
				login_member_date_of_birth, login_member_profeesional_id,
				login_member_marital_status_id,
				login_member_number_of_children, login_member_nationality_id,
				login_member_church_member_ship_id, login_member_sex,
				login_member_mobile_number, email, password, monthOFCalender)
				.execute("");
		// Log.e("JKUOOIH", "" + settingsListAdaptor.getListSaved());

	}

	private boolean checkFirstName() {

		if (editFName.getText().toString().equals("")) {
			setErrMsg("First Name field is Blank");
			return false;
		}
		return true;
	}

	private boolean checkSurName() {

		if (editSName.getText().toString().equals("")) {
			setErrMsg("Surname Name field is Blank");
			return false;
		}
		return true;
	}

	private boolean checkDob() {
		if (editDOB.getText().toString().trim().equals("")) {
			setErrMsg("Please fill Date Of Birth");
			return false;
		}
		return true;
	}

	private boolean checkChildren() {
		if (editchildren.getText().toString().trim().equals("")) {
			setErrMsg("Please fill children");
			return false;
		}
		return true;
	}

	private boolean checkMobileNumber() {
		if (editMobile.getText().toString().trim().equals("")) {
			setErrMsg("Please fill mobile number");
			return false;
		}
		return true;
	}

	private boolean checkProfession() {
		if (la_profession.getSelectedItemPosition() == 0) {
			setErrMsg("Please select profession");
			return false;
		}

		return true;
	}

	private boolean checkMaritalStatus() {
		if (la_marital_status.getSelectedItemPosition() == 0) {
			setErrMsg("Please select Marital status");
			return false;
		}

		return true;
	}

	private boolean checknatioanlity() {
		if (la_nationality.getSelectedItemPosition() == 0) {
			setErrMsg("Please select nationality");
			return false;
		}

		return true;
	}

	private boolean checkChurchMemberShip() {
		if (spSelectChurchMembership.getSelectedItemPosition() == 0) {
			setErrMsg("Please select church membership");
			return false;
		}

		return true;
	}

	public void setErrMsg(String msg) {

		errorText.setVisibility(View.VISIBLE);
		errorText.setText(msg);
		errorText.setTextColor(Color.RED);
	}

	@Override
	public void onDateSet(int year, int monthOfYear, int dayOfMonth) {
		// TODO Auto-generated method stub
		monthOFCalender = uiDatePicker.getMonthInString() + " " + dayOfMonth
				+ ", " + year;
	}

}
