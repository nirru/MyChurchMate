package com.mcm.login;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.mcm.registration.InterfaceSPinnerId;
import com.mcm.spinneradapter.SpinnerAdapter;

public class ActiveMemeberShipSpinner implements OnItemSelectedListener{
	InterfaceSPinnerId sPinnerId;
	Spinner spinner;
	Context context;
	ArrayList<ArrayList<String>> clientChurchList;
	boolean isSpiinerItem;
	public ActiveMemeberShipSpinner(Context context,InterfaceSPinnerId sPinnerId, Spinner spinner,ArrayList<ArrayList<String>> clientChurchList,boolean isSpiinerItem)
	{
		this.context = context;
		this.sPinnerId = sPinnerId;
		this.spinner = spinner;
		this.clientChurchList = clientChurchList;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,
			long id) {
		Log.e("SPINNER COUNT", ""+ spinner.getAdapter().getCount());
		isSpiinerItem = false;
		sPinnerId.getSpinnerId(parent.getSelectedItemPosition(),isSpiinerItem);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
