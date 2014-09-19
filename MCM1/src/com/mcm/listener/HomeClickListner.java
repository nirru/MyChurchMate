package com.mcm.listener;



import android.view.View;
import android.view.View.OnClickListener;

import com.mcm.R;

public abstract class HomeClickListner implements OnClickListener {

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		
		case R.id.reg_btn:
			onRegisterBtnClk(view);
			break;
			
		case R.id.signin_btn:
			onSignInBtnClk(view);
			break;
			
		default:
			break;
		}
	}
	public abstract void onRegisterBtnClk(View view);
	public abstract void onSignInBtnClk(View view);

}
