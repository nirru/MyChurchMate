package com.mcm.listener;



import android.view.View;
import android.view.View.OnClickListener;

import com.mcm.R;

public abstract class RegisterClickListner implements OnClickListener {

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		
		case R.id.ra_btnNextStep2:
			onNextBtnClk(view);
			break;
			
		case R.id.ra_btnSubmit:
			onstepTwoNextBtnClk(view);
			break;
			
		case R.id.ra_btnprevious:
			onPreviousBtnClk(view);
			break;
			
		case R.id.ra_btnprevious_stepthree:
			onStepThreePreviousBtnClk(view);
			break;
			
		case R.id.ra_btnSubmit_stepthree:
			onStepThreeSubmitBtnClk(view);
			break;
			
		case R.id.ra_editCountry:
			onCountryBtnClk(view);
			break;
			
		case R.id.ra_editEmail:
			onDateOfBirthClick(view);
			break;
			
		default:
			break;
		}
	}
	public abstract void onCountryBtnClk(View view);
	public abstract void onStepThreePreviousBtnClk(View view);
	public abstract void onStepThreeSubmitBtnClk(View view);
	public abstract void onPreviousBtnClk(View view);
	public abstract void onNextBtnClk(View view);
	public abstract void onstepTwoNextBtnClk(View view);
	public abstract void onDateOfBirthClick(View view);

}
