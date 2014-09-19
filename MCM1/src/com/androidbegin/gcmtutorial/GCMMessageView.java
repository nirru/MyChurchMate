package com.androidbegin.gcmtutorial;

import com.mcm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GCMMessageView extends Activity {
	String message;
	TextView txtmsg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messageview);

		// Retrive the data from GCMIntentService.java
		Intent i = getIntent();

		message = i.getStringExtra("message");

		// Locate the TextView
		txtmsg = (TextView) findViewById(R.id.message);

		// Set the data into TextView
		txtmsg.setText(message);
		
		Log.i("MESSAGE RECEIVED", "" + message);
	}
}