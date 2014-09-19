package com.mcm.datepicker;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class UiToast extends Toast {
	Context context;
	
	public UiToast(Context context) {
		super(context);
		this.context = context;
	}
	
	public void showToast(final String string, final int toastLength) {
		((Activity) context).runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				Toast.makeText(context, string, toastLength).show();
			}
		});
	}
}
