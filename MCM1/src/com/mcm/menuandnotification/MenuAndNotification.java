package com.mcm.menuandnotification;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.SplashActivity;
import com.mcm.appconstant.AppConstant;
import com.mcm.database.GetDataFromDatabase;
import com.mcm.database.InsertTable;
import com.mcm.datepicker.InterfaceDateToPass;
import com.mcm.listener.MenuTabClickListener;
import com.mcm.login.LoginActivity;

public class MenuAndNotification extends FragmentActivity {

	/** Called when the activity is first created. */
	Button signOut;
	int clientId;
	TabHost.TabSpec spec;
	Intent intent;
	GetDataFromDatabase getDataFromDatabase;
	LinearLayout linearLayout;
	ArrayList<String> imageBackgroundChange;
	TextView text_first, text_second, text_three, text_fourth, text_fifth,
			text_sixth;
	ImageView first_defaultView, secondView, thirdView, fourthView, fifthView,
			sixthView;
	TextView selected_church_name_tv;
	ArrayList<ArrayList<String>> menuListArray;
	ArrayList<ImageView> listImageView;
	ArrayList<TextView> listOfTextView;
	String myClientChurhName;
	String email_string;
	int surveyFlag, notificationID;
	String notificationMessage;
	int x;
	ImageView rightArrow, leftArrow;
	ArrayList<ArrayList<String>> churchList;
	HorizontalScrollView scroll;

	RelativeLayout relative_one, relative_two, relative_three, relative_four,
			relative_five, relative_six;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_notification);

		selectedChurchHeader();
		init();
		// getThumbnail("TabBar.png", menuListArray.get(menuListArray.size() -
		// 1)
		// .get(3).toString().trim());

		disableAutoScrollin();
		checkForFirstDefaultImage();
	}

	private void selectedChurchHeader() {

		selected_church_name_tv = (TextView) findViewById(R.id.selected_church_tv);
		signOut = (Button) findViewById(R.id.btn_sign_out);
	}

	private void init() {
		notificationID = getIntent()
				.getIntExtra(
						com.mcm.appconstant.AppConstant.NOTIFICATION_PUSH_NOTIFICATION_ID,
						0);
		notificationMessage = getIntent().getStringExtra(
				com.mcm.appconstant.AppConstant.NOTIFICATION_DETAILS);
		surveyFlag = getIntent().getIntExtra(
				com.mcm.appconstant.AppConstant.NOTIFICATION_SURVEY_FLAG, 0);
		// Log.e("SURVEY FLAG", "" + surveyFlag + "AND NOTIFICATION ID" + " "
		// + notificationID);
		clientId = getIntent().getIntExtra(
				com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID, 0);
		email_string = getIntent().getStringExtra(
				com.mcm.appconstant.AppConstant.EMAIL_ID);

		// Log.e("CLIENT ID IN NOTIF in menu Notification", "" + clientId);
		getDataFromDatabase = new GetDataFromDatabase();
		menuListArray = getDataFromDatabase.GetMemberMenus(clientId);
		// Log.e("MENU LIST ARRAY CONTENT", "" + menuListArray);
		myClientChurhName = menuListArray.get(0).get(3).toString().trim();
		listImageView = new ArrayList<ImageView>();
		listOfTextView = new ArrayList<TextView>();
		imageBackgroundChange = new ArrayList<String>();
		linearLayout = (LinearLayout) findViewById(R.id.header);
		first_defaultView = (ImageView) findViewById(R.id.img_events);
		secondView = (ImageView) findViewById(R.id.img_notif);
		thirdView = (ImageView) findViewById(R.id.img_chat);
		fourthView = (ImageView) findViewById(R.id.img_rotas);
		fifthView = (ImageView) findViewById(R.id.img_approved);
		sixthView = (ImageView) findViewById(R.id.img_settings);

		text_first = (TextView) findViewById(R.id.tv_events);
		text_second = (TextView) findViewById(R.id.tv_notif);
		text_three = (TextView) findViewById(R.id.tv_chat);
		text_fourth = (TextView) findViewById(R.id.tv_rotas);
		text_fifth = (TextView) findViewById(R.id.tv_settings);
		text_sixth = (TextView) findViewById(R.id.tv_approved);

		relative_one = (RelativeLayout) findViewById(R.id.relative_one);
		relative_two = (RelativeLayout) findViewById(R.id.relative_two);
		relative_three = (RelativeLayout) findViewById(R.id.relative_three);
		relative_four = (RelativeLayout) findViewById(R.id.relative_four);
		relative_five = (RelativeLayout) findViewById(R.id.relative_fiive);
		relative_six = (RelativeLayout) findViewById(R.id.relative_six);

		signOut.setOnClickListener(menuTableClkListener);
		listImageView.add(first_defaultView);
		listImageView.add(secondView);
		listImageView.add(thirdView);
		listImageView.add(fourthView);
		listImageView.add(fifthView);
		listImageView.add(sixthView);

		listOfTextView.add(text_first);
		listOfTextView.add(text_second);
		listOfTextView.add(text_three);
		listOfTextView.add(text_fourth);
		listOfTextView.add(text_fifth);
		listOfTextView.add(text_sixth);

		// selected_church_name_tv.setTypeface(SplashActivity.mpBold,
		// Typeface.BOLD);
		selected_church_name_tv.setText(myClientChurhName);

		scroll = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);

		rightArrow = (ImageView) findViewById(R.id.right_arrow);
		leftArrow = (ImageView) findViewById(R.id.left_arrow);// text_first.setTypeface(SplashActivity.mpBold);

		leftArrow.setOnClickListener(menuTableClkListener);
		rightArrow.setOnClickListener(menuTableClkListener);
		// text_second.setTypeface(SplashActivity.mpBold);
		// text_three.setTypeface(SplashActivity.mpBold);
		// text_fourth.setTypeface(SplashActivity.mpBold);
		// text_fifth.setTypeface(SplashActivity.mpBold);

		// Log.e("MENU LIST ARRAY SIZe AND SIZE OF LIST OF IMAGE VIEW", ""
		// + menuListArray.size() + "SIZE OF " + listImageView.size());
		displayView();
	}

	private void disableAutoScrollin() {
		scroll.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	private void displayView() {

		Fragment fragment = new Events(MenuAndNotification.this, clientId,
				menuListArray.get(menuListArray.size() - 1).get(3).toString(),
				email_string);
		// Log.e("TESTING CLIENTS ID", "" +
		// menuListArray.get(menuListArray.size() - 1)
		// .get(3).toString());
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}

	}

	private void checkForFirstDefaultImage() {
		String imageName = "";
		String textString;
		String menuClientName = "";
		ImageView setOrderImageView;
		for (int i = 0; i < menuListArray.size(); i++) {
			imageName = menuListArray.get(i).get(4).toString().trim();
			textString = imageName;
			menuClientName = menuListArray.get(i).get(6).toString().trim();
			setOrderImageView = listImageView.get(i);
			TextView textView = listOfTextView.get(i);
			if (i == 0) {
				imageName = imageName + AppConstant.SELECTED + ".png";
			} else {
				imageName = imageName + AppConstant.NOTSELECTED + ".png";
			}
			// Log.e("image name", "" + imageName);
			// Log.e("set order image view", ""+ setOrderImageView);
			imageBackgroundChange.add(imageName);
			setThumbnailImage(imageName, menuClientName, setOrderImageView);
			setTextOnTextView(textView, textString);
		}

//		Log.e("TAG COUNT ", "" + imageBackgroundChange.size());
		dynamicTopBar(imageBackgroundChange.size());
	}

	private void setTextOnTextView(TextView textView, String textString) {
		// Log.e("TExtNAMAS", " " + textString);
		textView.setText(textString);
	}

	public Bitmap getThumbnail(String filename, String folder) {
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(getFilesDir(),
					"/Images/" + folder + "/" + "ThemeImages/" + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			linearLayout.setBackgroundDrawable(d);
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
		return thumbnail;
	}

	public Bitmap setThumbnailImage(String filename, String filepath,
			ImageView image_View) {

		filepath = filepath.replace("\\", "/");
		// Log.e("MY FILE PATH", "" + filepath);
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(getFilesDir(),
					filepath + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			// relativeLayout.setBackgroundDrawable(d);
			image_View.setImageBitmap(thumbnail);
			image_View.setTag(filename);
			image_View.setOnClickListener(menuTableClkListener);
		} catch (Exception ex) {
			Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
		return thumbnail;
	}

	MenuTabClickListener menuTableClkListener = new MenuTabClickListener() {

		@Override
		public void onThirdBtnClk(View view) {
			// TODO Auto-generated method stub
			String imageName = (String) view.getTag();
			Log.e("<<<<<<<<<<THIRD BUTTON CLICKED>>>>>>>>", "" + imageName);
			setBackgroundImageOnImageView(imageName);
			// displayRotas();

			startClickEvent(imageName);

		}

		@Override
		public void onSecondBtnClk(View view) {
			// TODO Auto-generated method stub
			String imageName = (String) view.getTag();
			Log.e("<<<<<<<<<<SECOND BUTTON CLICKED>>>>>>>>", "" + imageName);
			setBackgroundImageOnImageView(imageName);
			// displayNotification();

			startClickEvent(imageName);
		}

		@Override
		public void onFirstDefaultBtnClk(View view) {
			String imageName = (String) view.getTag();
			Log.e("<<<<<<<<<<FIRST BUTTON CLICKED>>>>>>>>", "" + imageName);
			setBackgroundImageOnImageView(imageName);
			// displayView();

			startClickEvent(imageName);
		}

		@Override
		public void onSignOutClk(View view) {
			// TODO Auto-generated method stub

			churchList = getDataFromDatabase.getClientChurch();
			SQLiteDatabase database = SplashActivity.databaseHelper
					.getWritableDatabase();
//			new InsertTable(database).deleteAllTable();
			putValueInPrefs();
		}

		@Override
		public void OnFourthBtnClk(View view) {
			// TODO Auto-generated method stub
			String imageName = (String) view.getTag();
			Log.e("<<<<<<<<<<FOURTH BUTTON CLK>>>>>>>>", "" + imageName);
			setBackgroundImageOnImageView(imageName);
			// displayChat();
			// displaySettings();

			startClickEvent(imageName);

		}

		@Override
		public void OnFifthBtnClk(View view) {
			// TODO Auto-generated method stub
			String imageName = (String) view.getTag();
			Log.e("<<<<<<<<<<FIFTH BUTTON CLK>>>>>>>>", "" + imageName);
			setBackgroundImageOnImageView(imageName);
			// displayApprovedScreen();

			startClickEvent(imageName);

		}

		@Override
		public void OnSixthBtnClk(View view) {
			// TODO Auto-generated method stub
			String imageName = (String) view.getTag();
			Log.e("<<<<<<<<<<SIXTH BUTTON CLK>>>>>>>>", "" + imageName);
			setBackgroundImageOnImageView(imageName);
			// displaySettings();

			startClickEvent(imageName);
		}

		@Override
		public void onRightArrowClick(View view) {
			// TODO Auto-generated method stub
			if (x <= sixthView.getLeft() + sixthView.getWidth()) {
				x = x + 250;
				scroll.scrollTo(x, 0);
			} else {

			}
		}

		@Override
		public void onLeftArrowClick(View view) {
			// TODO Auto-generated method stub
			if (x == first_defaultView.getX()) {

			} else {
				x = x - 250;
				scroll.scrollTo(x, 0);
			}
		}
	};

	private void setBackgroundImageOnImageView(String imageString) {
		String matchImageString = "";
		String compareString;
		// Log.e("BEFORE REPLACEMENT", "" + imageBackgroundChange);
		for (int i = 0; i < imageBackgroundChange.size(); i++) {
			matchImageString = imageBackgroundChange.get(i).toString().trim();
			// compareString = matchImageString;
			if (matchImageString.equals(imageString)) {
				matchImageString = matchImageString.replace(
						AppConstant.NOTSELECTED, AppConstant.SELECTED);
			} else {
				matchImageString = matchImageString.replace(
						AppConstant.NOTSELECTED, AppConstant.SELECTED);
				matchImageString = matchImageString.replace(
						AppConstant.SELECTED, AppConstant.NOTSELECTED);
			}

			imageBackgroundChange.set(i, matchImageString);
			listImageView.get(i).setTag(imageBackgroundChange.get(i));
			changeBackGroundImageAccordinglt(listImageView.get(i),
					imageBackgroundChange.get(i), menuListArray.get(i).get(6)
							.toString().trim());

		}

		// Log.e("AFTER REPLACEMENT", "" + imageBackgroundChange);
	}

	private void changeBackGroundImageAccordinglt(ImageView imageView,
			String filename, String filepath) {
		// TODO Auto-generated method stub
		filepath = filepath.replace("\\", "/");
		// Log.e("MY FILE PATH", "" + filepath);
		Bitmap thumbnail = null;
		try {
			FileInputStream fi = new FileInputStream(new File(getFilesDir(),
					filepath + filename));
			thumbnail = BitmapFactory.decodeStream(fi);
			Drawable d = new BitmapDrawable(getResources(), thumbnail);
			// relativeLayout.setBackgroundDrawable(d);
			imageView.setImageBitmap(thumbnail);
		} catch (Exception ex) {
			// Log.e("getThumbnail() on internal storage", ex.getMessage());
		}
	}

	private void putValueInPrefs() {
		SharedPreferences.Editor prefs = MenuAndNotification.this
				.getSharedPreferences("com.example.app", Context.MODE_PRIVATE)
				.edit();
		prefs.putBoolean(AppConstant.PREFS_KEYS, false);
		prefs.putInt(AppConstant.PREFS_KEYS_CLIENT_ID, clientId);
		prefs.commit();
		Intent mIntent = new Intent(MenuAndNotification.this,
				LoginActivity.class);
		mIntent.putExtra("LIST", churchList);
		startActivity(mIntent);
		// startActivity(new Intent(MenuAndNotification.this,
		// LoginActivity.class));
		finish();
	}

	private void displayNotification() {

		Fragment fragment = new Notifications(MenuAndNotification.this,
				clientId, menuListArray.get(menuListArray.size() - 1).get(3)
						.toString(), email_string, surveyFlag,
				notificationMessage, notificationID);
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}

	}

	private void displayRotas() {

		// Log.e("Dispaly Rotas", "YEs");
		Fragment fragment = new Rotas(MenuAndNotification.this, clientId,
				menuListArray.get(menuListArray.size() - 1).get(3).toString());
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}

	}

	private void displayChat() {

		Fragment fragment = new Chat(MenuAndNotification.this, clientId,
				menuListArray.get(menuListArray.size() - 1).get(3).toString());
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}
	}

	private void displaySettings() {

		Fragment fragment = new Settings(MenuAndNotification.this, clientId,
				menuListArray.get(menuListArray.size() - 1).get(3).toString());
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}

	}

	private void displayApprovedScreen() {

		Fragment fragment = new ApprovedScree(MenuAndNotification.this,
				clientId, menuListArray.get(menuListArray.size() - 1).get(3)
						.toString(), email_string);
		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();
		}
	}

	private void startClickEvent(String imageViewName) {
		if (imageViewName.equals("NotificationsNotSelected.png")) {
			displayNotification();
		} else if (imageViewName.equals("SettingsNotSelected.png")) {
			displaySettings();
		} else if (imageViewName.equals("RotaNotSelected.png")) {
			displayRotas();
		} else if (imageViewName.equals("ChatsNotSelected.png")) {
			displayChat();
		} else if (imageViewName.equals("ApproveNotSelected.png")) {
			displayApprovedScreen();
		} else if (imageViewName.equals("EventsNotSelected.png")) {
			displayView();
		}
	}

	private void dynamicTopBar(int count) {
		switch (count) {
		case 1:
			relative_one.setVisibility(View.VISIBLE);
			relative_two.setVisibility(View.GONE);
			relative_three.setVisibility(View.GONE);
			relative_four.setVisibility(View.GONE);
			relative_five.setVisibility(View.GONE);
			relative_six.setVisibility(View.GONE);
			break;
		case 2:
			relative_one.setVisibility(View.VISIBLE);
			relative_two.setVisibility(View.VISIBLE);
			relative_three.setVisibility(View.GONE);
			relative_four.setVisibility(View.GONE);
			relative_five.setVisibility(View.GONE);
			relative_six.setVisibility(View.GONE);
			break;

		case 3:
			relative_one.setVisibility(View.VISIBLE);
			relative_two.setVisibility(View.VISIBLE);
			relative_three.setVisibility(View.VISIBLE);
			relative_four.setVisibility(View.GONE);
			relative_five.setVisibility(View.GONE);
			relative_six.setVisibility(View.GONE);
			break;

		case 4:
			relative_one.setVisibility(View.VISIBLE);
			relative_two.setVisibility(View.VISIBLE);
			relative_three.setVisibility(View.VISIBLE);
			relative_four.setVisibility(View.VISIBLE);
			relative_five.setVisibility(View.GONE);
			relative_six.setVisibility(View.GONE);
			break;

		case 5:
			relative_one.setVisibility(View.VISIBLE);
			relative_two.setVisibility(View.VISIBLE);
			relative_three.setVisibility(View.VISIBLE);
			relative_four.setVisibility(View.VISIBLE);
			relative_five.setVisibility(View.VISIBLE);
			relative_six.setVisibility(View.GONE);
			break;

		case 6:
			relative_one.setVisibility(View.VISIBLE);
			relative_two.setVisibility(View.VISIBLE);
			relative_three.setVisibility(View.VISIBLE);
			relative_four.setVisibility(View.VISIBLE);
			relative_five.setVisibility(View.VISIBLE);
			relative_six.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

}
