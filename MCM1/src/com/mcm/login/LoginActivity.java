package com.mcm.login;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcm.R;
import com.mcm.SplashActivity;
import com.mcm.asynclass.ConnectionDetector;
import com.mcm.asynclass.SplashAsynTask;
import com.mcm.database.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.forgetpassword.ForgetPassword;
import com.mcm.listener.LoginClickListner;
import com.mcm.registration.InterfaceSPinnerId;
import com.mcm.spinneradapter.SpinnerAdapter;

public class LoginActivity extends Activity implements InterfaceSPinnerId,
		PopulateChurchListOnValidating , AnimationListener{

	EditText login, passWord;
	Spinner spinnerChurchMember;
	Button logiButton;
	TextView tv_message, tv_forgotPassword, tv_sign_as_differ_user;
	TextView tv_header, tv_your_church;
	private ProgressDialog mProgressDialog;
	int clientid;
	ArrayList<ArrayList<String>> clientChurchList;
	ArrayList<ArrayList<String>> menuChurchList;
	GetDataFromDatabase getDataFromDatabase;
	boolean is_Table_Empty = false;
	boolean is_Find = false;
	SpinnerAdapter spinnerAdapter;
	SharedPreferences sharedPreferences;
	boolean isLoginAlready;
	String regID;
	String login_emailID, login_password;
	
	RelativeLayout relative;
	Animation animBounce;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		init();
		header();
		addItemIfDatabaseIsNotEmpty();
		addListenerOnSpinnerItemSelection();
	}

	@Override
	public void onPause() {
		super.onPause();
		// if (mProgressDialog != null)
		// mProgressDialog.dismiss();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	private void init() {

		Log.e("I amin login CTIVI", "yes");
		SharedPreferences sharedPreferences = this.getSharedPreferences(
				"com.example.app", LoginActivity.MODE_PRIVATE);
		isLoginAlready = sharedPreferences.getBoolean(
				com.mcm.appconstant.AppConstant.PREFS_KEYS, false);
		is_Table_Empty = getIntent().getBooleanExtra(AppConstant.CHECK_TABLE,
				false);
		regID = sharedPreferences.getString(
				com.mcm.appconstant.AppConstant.KEYS_REGISTRATION, "");
		Log.e("RegiSTRATION IDS", "" + regID);
		getDataFromDatabase = new GetDataFromDatabase();
		mProgressDialog = new ProgressDialog(LoginActivity.this);
		relative = (RelativeLayout)findViewById(R.id.root);
		clientChurchList = new ArrayList<ArrayList<String>>();
		menuChurchList = new ArrayList<ArrayList<String>>();
		tv_message = (TextView) findViewById(R.id.la_tvErrorMsg);
		tv_forgotPassword = (TextView) findViewById(R.id.la_forfotpaswword);
		login = (EditText) findViewById(R.id.la_editEmail);
		tv_sign_as_differ_user = (TextView) findViewById(R.id.textView4);
		passWord = (EditText) findViewById(R.id.la_editPass);
		logiButton = (Button) findViewById(R.id.la_btnLogin);
		spinnerChurchMember = (Spinner) findViewById(R.id.la_spChurchCentre);
		logiButton.setOnClickListener(loginClkListener);
		tv_sign_as_differ_user.setOnClickListener(loginClkListener);
		tv_forgotPassword.setOnClickListener(loginClkListener);
		spinnerChurchMember.setOnTouchListener(Spinner_OnTouch);
		tv_your_church = (TextView)findViewById(R.id.textView1);
		tv_message.setVisibility(View.INVISIBLE);
	}

	private void header() {
		tv_header = (TextView) findViewById(R.id.headerTextView);
		tv_header.setText("Login");
	}

	private void addItemIfDatabaseIsNotEmpty() {
		boolean isClientTableEmpty = getDataFromDatabase.checkForClientTables();
		if (!isClientTableEmpty) {

		} else {
			clientChurchList = getDataFromDatabase.getClientChurch();
			// addItemsOnChurchMemberShipTypeSpinner(clientChurchList);
			// com.mcm.spinneradapter.SpinnerAdapter spinnerAdapter = new
			// com.mcm.spinneradapter.SpinnerAdapter(LoginActivity.this,
			// clientChurchList, false);
			// spinnerChurchMember.setAdapter(spinnerAdapter);
			// showSpinnerData(true);

		}
	}

	public void addItemsOnChurchMemberShipTypeSpinner(
			ArrayList<ArrayList<String>> churchList) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < churchList.size(); i++) {
			list.add(churchList.get(i).get(4));
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				LoginActivity.this, android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerChurchMember.setAdapter(dataAdapter);
		spinnerChurchMember.setPrompt("Church Center");
		spinnerChurchMember.setEnabled(true);
	}

	public void addListenerOnSpinnerItemSelection() {
		// spinnerChurchMember
		// .setOnItemSelectedListener(new ActiveMemeberShipSpinner(
		// LoginActivity.this, LoginActivity.this,
		// spinnerChurchMember,clientChurchList,true));
		spinnerChurchMember.setPrompt("Church Center");
		showSpinnerData(true, 1);
	}

	@Override
	public void getSpinnerId(int pos, boolean isSpiinerItem) {
		// clientid =
		// Integer.parseInt(clientChurchList.get(pos).get(0).toString());
		// showSpinnerData(isSpiinerItem);
	}

	LoginClickListner loginClkListener = new LoginClickListner() {

		@Override
		public void onLogInBtnClk(View view) {
			// TODO Auto-generated method stub
			validationForLoginButton();
		}

		@Override
		public void onforgotPasswordClk(View view) {
			// TODO Auto-generated method stub
			ArrayList<ArrayList<String>> passList = new ArrayList<ArrayList<String>>();
			@SuppressWarnings("unchecked")
			ArrayList<ArrayList<String>> myList = (ArrayList<ArrayList<String>>) getIntent()
					.getSerializableExtra("LIST");
			if (myList == null) {
				return;
			}
			if (myList.size() == 0) {
				Log.e("SIZE IS ZERO", "" + myList.size());
				passList = clientChurchList;
			} else {
				Log.e("SIZE IS NOT ZERO", "" + myList.size());
				passList = myList;
			}

			Log.e("LIST EXTAR", "" + passList);
			Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
			intent.putExtra("LISTVALUE", passList);
			intent.putExtra("CLIENTID", "1");
			startActivity(intent);
		}

		@Override
		public void onSignAsDiffUserClk(View view) {
			loginAsDifferentUser();
		}
	};

	private void loginAsDifferentUser() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				LoginActivity.this);
		alertDialog.setTitle("Message");
		alertDialog
				.setMessage("All the existing details will be removed. Do you want to continue?");
		alertDialog.setPositiveButton("Yes", dialogClickListener);
		alertDialog.setNegativeButton("No", dialogClickListener);
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				// Yes button clicked
				yesButtonClick();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// No button clicked
				dialog.dismiss();
				break;
			}
		}
	};

	private void yesButtonClick() {
		SQLiteDatabase database = SplashActivity.databaseHelper
				.getWritableDatabase();
		InsertTable insertTable = new InsertTable(database);
		insertTable.deleteAllTable();
		clientChurchList = getDataFromDatabase.getClientChurch();
		showSpinnerData(true, 1);
		hideErrorMessage();
		
		animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.bounce);

		// set animation listener
		animBounce.setAnimationListener(this);
		relative.startAnimation(animBounce);
	}

	private void validationForLoginButton() {
		if (!checkEmail())
			return;
		if (!checkEmailPattern())
			return;
		if (!checkpassWord())
			return;
		else {
			authorizedUser();
		}
	}

	private void getEmailAndpasswordFromClientId() {

		SQLiteDatabase sqLiteDatabase = SplashActivity.databaseHelper
				.getReadableDatabase();

		String query = " SELECT * " + " FROM "
				+ com.mcm.database.AppConstant.MEMBER_TABLE_NAME + " WHERE "
				+ com.mcm.database.AppConstant.MCM_MEMBER_CLIENT_ID + " ='"
				+ clientid + "'";

		Cursor cursor = sqLiteDatabase.rawQuery(query, null);

		cursor.moveToFirst();
		if (!cursor.isAfterLast()) {
			do {
				login_emailID = cursor.getString(4);
				login_password = cursor.getString(5);

			} while (cursor.moveToNext());
			cursor.close();
		}
	}

	private boolean checkEmailAdressByDatabaase() {
		if (!login.getText().toString().trim().equals(login_emailID.trim())) {
			setErrMsg("Invalid EmailId or Password");
			return false;
		}
		return true;
	}

	private boolean checkEmail() {
		if (login.length() == 0) {
			setErrMsg("Please fill Email field.");
			return false;
		}
		return true;
	}

	public boolean checkEmailPattern() {
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(login.getText().toString().trim());
		if (!matcher.matches()) {
			setErrMsg("Please fill valid Email Address.");
			return false;
		}
		return true;
	}

	private boolean checkpassWord() {
		if (passWord.length() == 0) {
			setErrMsg("Please fill password field.");
			return false;
		}
		return true;
	}

	private boolean checkPasswordByDatabase() {
		if (!passWord.getText().toString().trim().equals(login_password.trim())) {
			setErrMsg("Invalid EmailId or Password");
			return false;
		}
		return true;
	}

	View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_UP) {
				// showToast();
				if (!churchdropDown()) {
					spinnerChurchMember.setEnabled(false);
				} else {
					Log.e("CLIENT CHURCH LIST", "" + clientChurchList);
					if (clientChurchList.size() > 0) {
						spinnerChurchMember.setEnabled(true);
						showSpinnerData(false, 2);
					} else {
						spinnerChurchMember.setEnabled(false);
					}
				}
			}
			return false;
		}
	};

	private boolean churchdropDown() {
		GetDataFromDatabase getDataFromDatabase = new GetDataFromDatabase();
		if (!getDataFromDatabase.checkForTables())
			return false;
		else
			return true;

	}

	private void showSpinnerData(boolean isSpinnerItem, int k) {

		// Log.e("ClientCHURCH LIST SIZE", " " + clientChurchList.size());
		if (clientChurchList.size() == 0) {
			Log.e("ClientCHURCH LIST SIZE", " " + clientChurchList.size());
			spinnerChurchMember.setEnabled(false);
			SpinnerAdapter typeSpinnerAdapter = new SpinnerAdapter(
					LoginActivity.this, clientChurchList, isSpinnerItem);
			spinnerChurchMember.setAdapter(typeSpinnerAdapter);
		    spinnerChurchMember.setVisibility(View.INVISIBLE);
		    tv_your_church.setVisibility(View.INVISIBLE);
		} else if (clientChurchList.size() == 1) {
			// spinnerChurchMember.setSelection(clientChurchList.size() - 1);
			spinnerChurchMember.setEnabled(false);
			SpinnerAdapter typeSpinnerAdapter = new SpinnerAdapter(
					LoginActivity.this, clientChurchList, isSpinnerItem);
			spinnerChurchMember.setAdapter(typeSpinnerAdapter);
			Log.e("I AM TOUCHED",
					"" + spinnerChurchMember.getSelectedItemPosition());
			clientid = Integer.parseInt(clientChurchList.get(
					(int) spinnerChurchMember.getSelectedItemId()).get(0));
			   spinnerChurchMember.setVisibility(View.VISIBLE);
			    tv_your_church.setVisibility(View.VISIBLE);
		} else if (clientChurchList.size() > 1) {
			SpinnerAdapter typeSpinnerAdapter = new SpinnerAdapter(
					LoginActivity.this, clientChurchList, isSpinnerItem);
			spinnerChurchMember.setAdapter(typeSpinnerAdapter);
			clientid = Integer.parseInt(clientChurchList.get(
					(int) spinnerChurchMember.getSelectedItemId()).get(0));
			spinnerChurchMember.setVisibility(View.VISIBLE);
		    tv_your_church.setVisibility(View.VISIBLE);
			Log.e("I AM ELSE TOUCHED",
					"" + spinnerChurchMember.getSelectedItemPosition());
		}
	}

	public void setErrMsg(String msg) {
		tv_message.setVisibility(View.VISIBLE);
		tv_message.setText(msg);
		tv_message.setTextColor(Color.RED);
	}

	private void hideErrorMessage() {
		login.setHint("Email address");
		passWord.setHint("Password");
		tv_message.setVisibility(View.INVISIBLE);
		Toast.makeText(LoginActivity.this, "please use the different user to login", Toast.LENGTH_LONG).show();
	}

	private void authorizedUser() {
		if (!checkForClientTables()) {
			Log.e("Calling False one", " " + checkForClientTables());
			validateFromApi(checkForClientTables());
		} else {
			Log.e("Calling True one", " " + checkForMemberTables());
			validatefromDatabase();
		}

	}

	public boolean checkForMemberTables() {
		return getDataFromDatabase.checkForTables();
	}

	public boolean checkForClientTables() {
		return getDataFromDatabase.checkForClientTables();
	}

	private void validateFromApi(boolean checkMemberTable) {

		firstTimeLogin(checkMemberTable);
	}

	private void firstTimeLogin(boolean checkMemberTable) {
		ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
		Boolean isInternetPresent = cd.isConnectingToInternet();
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getWritableDatabase();
			String url = "http://mcmwebapi.victoriatechnologies.com/api/Member/GetRegisteredMemberByEmailPwd?EmailId="
					+ login.getText().toString().trim()
					+ "&"
					+ "Password="
					+ passWord.getText().toString().trim();
			Log.e("Reg id", "" + regID);
			new LogInAsync(LoginActivity.this, mProgressDialog, url, login
					.getText().toString().trim(), passWord.getText().toString()
					.trim(), LoginActivity.this, database, spinnerChurchMember,
					checkMemberTable, regID,tv_your_church).execute("");
		} else {
			showOKAleart("Message", "You are not connected to internet");
		}
	}

	private void validatefromDatabase() {
		if (!checkForClientID()) {
			showOKAleart("Error", "Please select atleast one church");
			return;
		}
		getEmailAndpasswordFromClientId();

		if (!checkEmailAdressByDatabaase())
			return;

		if (!checkPasswordByDatabase())
			return;
		else {
			tv_message.setVisibility(View.INVISIBLE);
			clientid = Integer.parseInt(clientChurchList.get(
					(int) spinnerChurchMember.getSelectedItemId()).get(0));
			Log.e("CLIEN ID IN LOGIN ACTIVITU", "" + clientid);
			putValueInPrefs();
			Intent i = new Intent(LoginActivity.this,
					com.mcm.menuandnotification.MenuAndNotification.class);
			i.putExtra(com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID,
					clientid);
			i.putExtra(com.mcm.appconstant.AppConstant.EMAIL_ID, login
					.getText().toString().trim());
			i.putExtra(
					com.mcm.appconstant.AppConstant.NOTIFICATION_SURVEY_FLAG, 0);
			i.putExtra(
					com.mcm.appconstant.AppConstant.NOTIFICATION_PUSH_NOTIFICATION_ID,
					0);
			i.putExtra(com.mcm.appconstant.AppConstant.NOTIFICATION_DETAILS, "");
			startActivity(i);
			// startActivity(new Intent(LoginActivity.this,
			// com.mcm.menuandnotification.MenuAndNotification.class)
			// .putExtra(
			// com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID,
			// clientid));
			finish();
		}
	}

	private void putValueInPrefs() {
		SharedPreferences.Editor prefs = LoginActivity.this
				.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
				.edit();
		prefs.putBoolean(com.mcm.appconstant.AppConstant.PREFS_KEYS, true);
		prefs.putInt(com.mcm.appconstant.AppConstant.PREFS_KEYS_CLIENT_ID,
				clientid);
		prefs.putString(com.mcm.appconstant.AppConstant.EMAIL_ID, login
				.getText().toString().trim());
		prefs.commit();
	}

	private boolean checkForClientID() {

		for (int i = 0; i < clientChurchList.size(); i++) {
			Log.e("clientChurchLis client id",
					"" + Integer.parseInt(clientChurchList.get(i).get(0)));
			Log.e("CLIENT ID", "" + clientid);
			if (Integer.parseInt(clientChurchList.get(i).get(0)) == clientid) {
				is_Find = true;
				break;
			}
		}
		return is_Find;
	}

	public void showOKAleart(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				LoginActivity.this);
		builder.setTitle(title).setMessage(message)
				.setNegativeButton("OK", null).show();
	}

	@Override
	public void populateChurchList(ArrayList<ArrayList<String>> myChurchList,
			ArrayList<ArrayList<String>> menuChurchListArray) {
		// TODO Auto-generated method stub
		clientChurchList = myChurchList;
		this.menuChurchList = menuChurchListArray;
		spinnerChurchMember.setEnabled(true);
		is_Table_Empty = true;
	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		if (animation == animBounce) {
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
}
