package com.mcm.registration;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MaleFemaleSpinner implements OnItemSelectedListener{
	InterfaceMaleFemaleSPinnerId sPinnerId;
	public MaleFemaleSpinner(InterfaceMaleFemaleSPinnerId sPinnerId)
	{
		this.sPinnerId = sPinnerId;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		sPinnerId.getMaleFemaleSpinnerId(parent.getSelectedItemPosition());
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
