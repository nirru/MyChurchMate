package com.mcm;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import com.mcm.menuandnotification.EventsDetails;

public class ReminderService extends IntentService {
	private static final int NOTIF_ID = 1;

	public ReminderService() {
		super("ReminderService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		long when = System.currentTimeMillis(); // notification time
		Notification notification = new Notification(R.drawable.remaindericon,
				"reminder", when);
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= notification.FLAG_AUTO_CANCEL;
		Intent notificationIntent = new Intent(this, EventsDetails.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		notification
				.setLatestEventInfo(getApplicationContext(), "It's about time",
						"You should open the app now", contentIntent);
		nm.notify(NOTIF_ID, notification);
		
	}

	private void showOKAleart()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle("Message");
		alertDialog.setMessage("All the existing details will be removed. Do you want to continue?");
	    alertDialog.setPositiveButton("Yes", dialogClickListener);
	    alertDialog.setNegativeButton("No", dialogClickListener);
	    alertDialog.setCancelable(false);
	    AlertDialog alert = alertDialog.create();
	    alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	    alert.show();
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes button clicked
	        	dialog.dismiss();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	        	dialog.dismiss();
	            break;
	        }
	    }
	};

}