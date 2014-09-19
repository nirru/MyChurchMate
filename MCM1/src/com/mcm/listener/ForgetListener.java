package com.mcm.listener;
import android.view.View;
import android.view.View.OnClickListener;

import com.mcm.R;

public abstract class ForgetListener implements OnClickListener {

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		
		case R.id.la_btnLogin:
			onSubmitBtnClk(view);
			break;
		
		default:
			break;
		}
	}
	
	public abstract void onSubmitBtnClk(View view);

}
