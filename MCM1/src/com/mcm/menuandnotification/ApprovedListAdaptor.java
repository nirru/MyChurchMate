package com.mcm.menuandnotification;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.mcm.R;

public class ApprovedListAdaptor extends BaseAdapter {

	Context context;
	int layoutResourceId;
	String folderName;
	String clientId, firstName, emailId, url;
	ArrayList<ArrayList<String>> values;
	ProgressDialog mpProgressDialog;
	ListView listView;
	public ApprovedListAdaptor(Context context,
			ArrayList<ArrayList<String>> values,ListView listView ,int layoutResourceId, String folderName,ProgressDialog mProgressDialog) {
		this.context = context;
		this.values = values;
		this.layoutResourceId = layoutResourceId;
		this.folderName = folderName;
		this.mpProgressDialog = mProgressDialog;
		this.listView = listView;
		Log.e("VALUE NAME", "" + values);
	}

	@Override
	public int getCount() {
		if (values != null)
			return values.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return values.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {

		View row = convertView;
		ItemHolder holder = null;
		final int position = pos;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ItemHolder();
			row.setTag(holder);

		} else {
			holder = (ItemHolder) row.getTag();
		}
		holder.approved_clienName = (TextView) row.findViewById(R.id.approved_client_name);
		holder.approved_clientEmail = (TextView) row.findViewById(R.id.approved_client_email);
		holder.approved = (ImageView)row.findViewById(R.id.approved_icon);
		holder.reject = (ImageView)row.findViewById(R.id.reject_icon);
		
		
		holder.approved_clienName.setText(values.get(position).get(0).toString());
		holder.approved_clientEmail.setText(values.get(position).get(2).toString());
		holder.approved.setTag(pos);
		holder.reject.setTag(pos);
		holder.approved.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clientId = values.get((Integer) v.getTag()).get(3).toString().trim();
				emailId = values.get((Integer) v.getTag()).get(2).toString().trim();
				url = "http://mcmwebapi.victoriatechnologies.com/api/Member/ApproveMember?ClientId=" + clientId + "&" + "EmailId=" + emailId + "&" + "Approved=1&ApproverEmailId=jbunian@yahoo.com&ApprovedFrom=0";
				startAsynTask(url, position);
			}
		});
		
		holder.reject.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				clientId = values.get((Integer) v.getTag()).get(3).toString().trim();
				emailId = values.get((Integer) v.getTag()).get(2).toString().trim();
				url = "http://mcmwebapi.victoriatechnologies.com/api/Member/ApproveMember?ClientId=" + clientId + "&" + "EmailId=" + emailId + "&" + "Approved=0&ApproverEmailId=jbunian@yahoo.com&ApprovedFrom=0";
				startAsynTask(url , position);
			}
		});
		return row;
	}
	
	
	private void startAsynTask(String url , final int posRemove) {
		mpProgressDialog
		.setMessage("Sync data from Server is in progress. Please wait patiently....");
		mpProgressDialog.show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		        Log.e("RESPONSE", "" + response);
		        try {
		        	values.remove(posRemove);
		        	notifyDataSetChanged();
		        	mpProgressDialog.dismiss();
		        } catch (Exception e) {
					e.printStackTrace();

				}
		    }
		});
	}

	

	static class ItemHolder {
		TextView approved_clienName;
		TextView approved_clientEmail;
		
		ImageView approved;
		ImageView reject;
	}

	
	
}