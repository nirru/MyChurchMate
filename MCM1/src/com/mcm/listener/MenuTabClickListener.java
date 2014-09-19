package com.mcm.listener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;

import com.mcm.R;

public abstract class MenuTabClickListener implements OnClickListener {

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		
		case R.id.img_events:
			onFirstDefaultBtnClk(view);
			break;
			
		case R.id.img_notif:
			onSecondBtnClk(view);
			break;
			
		case R.id.img_rotas:
			onThirdBtnClk(view);
			break;
			
		case R.id.img_chat:
			OnFourthBtnClk(view);
			break;
			
		case R.id.img_settings:
			OnSixthBtnClk(view);
			break;
			
		case R.id.img_approved:
			OnFifthBtnClk(view);
			break;
			
		case R.id.btn_sign_out:
			onSignOutClk(view);
			break;
			
		case R.id.right_arrow:
			onRightArrowClick(view);
			break;
			
		case R.id.left_arrow:
			onLeftArrowClick(view);
			break;
		
		default:
			break;
		}
	}
	
	
	public abstract void onFirstDefaultBtnClk(View view);
	public abstract void onSecondBtnClk(View view);
	public abstract void onThirdBtnClk(View view);
	public abstract void OnFourthBtnClk(View view);
	public abstract void OnFifthBtnClk(View view);
	public abstract void OnSixthBtnClk(View view);
	public abstract void onRightArrowClick(View view);
	public abstract void onLeftArrowClick(View view);
	public abstract void onSignOutClk(View view);
}
