package com.mcm.registration;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class ChurchMemeberShipSpinner implements OnItemSelectedListener{
	InterfaceSPinnerId sPinnerId;
	public ChurchMemeberShipSpinner(InterfaceSPinnerId sPinnerId)
	{
		this.sPinnerId = sPinnerId;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		// TODO Auto-generated method stub
		sPinnerId.getSpinnerId(parent.getSelectedItemPosition(),true);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
