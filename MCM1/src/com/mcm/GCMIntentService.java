package com.mcm;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mcm.appconstant.AppConstant;
import com.mcm.database.InsertTable;
import com.mcm.login.LoginActivity;

public class GCMIntentService extends GCMBaseIntentService {

	String message;
	private static final String TAG = "GCM Tutorial::Service";
	SharedPreferences sharedPreferences;
	boolean isLoginAlready;
	int clientid, notificationId, surveyFlag;
	
	String clientEmail;
	String customData, ad, df, cs;
	// Use your PROJECT ID from Google API into SENDER_ID
	public static final String SENDER_ID = AppConstant.PROJECTS_NUMBER;
	public static String reg_ID;

	public GCMIntentService() {
		super(SENDER_ID);

	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		reg_ID = registrationId;
		Log.i(TAG, "onRegistered: registrationId=" + registrationId);
		// putValueInPrefs(context,registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent data) {

		getValueFromPrefs(context);

		// Message from PHP server
		message = data.getStringExtra("message");
//		customData = "CId=37;Id=140;SurveyFlag=0";
		customData = data.getStringExtra("custom");
//		Log.e("<<<< MESSAGE>>", "" + message);
		Log.e("<<<<Custom MESSAGE>>", "" + customData);
		seperateString(customData);
		setNotificationOnMessageArrived(context);
		//
		startAsynTask();
		Looper.loop();
	}

	@Override
	protected void onError(Context arg0, String errorId) {

		Log.e(TAG, "onError: errorId=" + errorId);
	}

	private void putValueInPrefs(Context context) {
		SharedPreferences.Editor prefs = context.getSharedPreferences(
				"com.example.app", Context.MODE_PRIVATE).edit();
		prefs.putBoolean(com.mcm.appconstant.AppConstant.PREFS_KEYS,
				isLoginAlready);
		prefs.putInt(com.mcm.appconstant.AppConstant.PREFS_KEYS_CLIENT_ID,
				clientid);
		prefs.putString(com.mcm.appconstant.AppConstant.EMAIL_ID, clientEmail
				.toString().trim());
		prefs.commit();
	}

	private void getValueFromPrefs(Context context) {
		sharedPreferences = context.getSharedPreferences("com.example.app",
				LoginActivity.MODE_PRIVATE);
		isLoginAlready = sharedPreferences.getBoolean(
				com.mcm.appconstant.AppConstant.PREFS_KEYS, false);
		clientid = sharedPreferences.getInt(
				com.mcm.appconstant.AppConstant.PREFS_KEYS_CLIENT_ID, 0);
		clientEmail = sharedPreferences.getString(
				com.mcm.appconstant.AppConstant.EMAIL_ID, null);

		// Log.e("IS LOGIN ALREADY", "" + isLoginAlready);
	}

	private void setNotificationOnMessageArrived(Context context) {
		// Open a new activity called GCMMessageView
		Intent intent;
		if (isLoginAlready) {
			putValueInPrefs(context);
			intent = new Intent(this,
					com.mcm.menuandnotification.MenuAndNotification.class);
			intent.putExtra(
					com.mcm.appconstant.AppConstant.CHURCH_MENU_CLIENT_ID,
					clientid);
			intent.putExtra(com.mcm.appconstant.AppConstant.EMAIL_ID,
					clientEmail.toString().trim());
			intent.putExtra(com.mcm.appconstant.AppConstant.NOTIFICATION_SURVEY_FLAG,
					surveyFlag);
			intent.putExtra(com.mcm.appconstant.AppConstant.NOTIFICATION_PUSH_NOTIFICATION_ID,
					notificationId);
			intent.putExtra(com.mcm.appconstant.AppConstant.NOTIFICATION_DETAILS,
					message);
		} else {
			intent = new Intent(this, com.mcm.home.HomeActivity.class);
		}

		// Starts the activity on notification click
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// Create the notification with a notification builder
		Notification notification = new NotificationCompat.Builder(context)
				.setSmallIcon(R.drawable.app_icon)
				.setWhen(System.currentTimeMillis())
				.setContentTitle("MyChurchMateApp").setContentText(message)
				.setContentIntent(pIntent).getNotification();

		// Remove the notification on click
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		manager.notify(R.string.app_name, notification);

		{
			// Wake Android Device when notification received
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(
					PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
			mWakelock.acquire();

			// Timer before putting Android Device to sleep mode.
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		}
	}

	private void startAsynTask() {

		String urlNotification = "http://mcmwebapi.victoriatechnologies.com/api/PushNotification/GetPushNotification?ClientId=" + clientid + "&NofiticationId=" + notificationId;
		Log.e("GSDD", "" + urlNotification);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(urlNotification, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String response) {
//				Log.e("RESPONSE NOTIFICATION", "" + response);
				try {
					JSONObject jsonObject = new JSONObject(response);
					int push_notid_id = jsonObject
							.getInt(AppConstant.NOTIFICATION_PUSH_NOTIFICATION_ID);
					int client_id = jsonObject
							.getInt(AppConstant.NOTIFICATION_CLIENT_ID);
					String notif_date = jsonObject
							.getString(AppConstant.NOTIFICATION_DATE);
					String notif_title = jsonObject
							.getString(AppConstant.NOTIFICATION_TITLE);
					String notif_details = jsonObject
							.getString(AppConstant.NOTIFICATION_DETAILS);
					String notif_status = jsonObject
							.getString(AppConstant.NOTIFICATION_STATUS);
					String notif_set_reminder = jsonObject
							.getString(AppConstant.NOTIFICATION_SET_REMINDER);
					String notif_recuiring_reminder_days = jsonObject
							.getString(AppConstant.NOTIFICATION_RECURRING_REMINDER_DAYS);
					String notif_survey_flag = jsonObject
							.getString(AppConstant.NOTIFICATION_SURVEY_FLAG);
					// Log.e("PUSH ID", "" + push_notid_id);

					SQLiteDatabase database = SplashActivity.databaseHelper
							.getReadableDatabase();
					InsertTable insertTable = new InsertTable(database);
					insertTable.addRowforNotificationTable(push_notid_id,
							client_id, notif_date, notif_title, notif_details,
							notif_status, notif_set_reminder,
							notif_recuiring_reminder_days, notif_survey_flag);
				} catch (Exception e) {
					e.printStackTrace();

				}
			}
		});

	}

	private void seperateString(String data) {
		String[] s = data.split(";");
//		Log.e("MESSAGE STRING LENNGHT", "" + s.length);
		for (String string : s)
			Log.e("STRING MESASAGE", "" + string);

		ad = s[0].substring(s[0].indexOf("=") + 1);
		clientid = Integer.parseInt(ad);
//		Log.e("FIRST VALUE", "" + ad);

		df = s[1].substring(s[1].indexOf("=") + 1);
		notificationId = Integer.parseInt(df);
		
		cs = s[2].substring(s[2].indexOf("=") + 1);
		surveyFlag = Integer.parseInt(cs);
//		Log.e("Last VAlue", "" + df);
	}

}