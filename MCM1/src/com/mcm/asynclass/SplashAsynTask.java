package com.mcm.asynclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.mcm.SplashActivity;
import com.mcm.appconstant.AppConstant;
import com.mcm.home.HomeActivity;

public class SplashAsynTask extends AsyncTask<String, String, String> {

	private ProgressDialog mProgressDialog;
	private Context context;
	private SQLiteDatabase database;
	ArrayList<String> myStrings;

	String url;

	public SplashAsynTask(Context context, String url,ProgressDialog mProgressDialog) {
		this.context = context;
		this.url = url;
		this.mProgressDialog = mProgressDialog;
		Log.e("MAY AYA", "" + url);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		showDialog();
	}

	@Override
	protected String doInBackground(String... aurl) {

		Log.e("MAY AYA", "" + "do in background");
		 parseJsonToArrayList();
		 return null;

	}

	@Override
	protected void onPostExecute(String unused) {
		// mProgressDialog.dismiss();
		 closeDialog();
		 Intent i = new Intent(context, HomeActivity.class);
		 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		 context.startActivity(i);
		 ((SplashActivity)context).finish();
	}

	

	void showDialog() {
		mProgressDialog.setMessage("Please Wait...updating form.");
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	void closeDialog() {
		// dismiss pDialog
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}
	
	
	public String getMemeberTypes() 
	  {
		String result = "";
	    InputStream inputStream = null;
	   
	    try {

	       // 1. create HttpClient
	        HttpClient httpclient = new DefaultHttpClient();

	       	//2. create HttpGet object:
			HttpGet httpGet = new HttpGet(url);
	        HttpResponse httpResponse = httpclient.execute(httpGet);
	         Log.e("RESPONSE MESSAGE", "" + httpResponse.getStatusLine().getStatusCode());
	        // 5. receive response as inputStream
	        inputStream = httpResponse.getEntity().getContent();
	        if(inputStream != null)
	            result = convertInputStreamToString(inputStream);
	        else
	            result = "Did not work!";

	       } catch (Exception e) 
			     {
			        e.printStackTrace();
			     }//end catch..
	    Log.e("result: ",result);
	    // 7. return result
	    return result;
	    
	}
	
	public void parseJsonToArrayList()
	{
		try
         {
         	JSONArray jArray = new JSONArray(getMemeberTypes().toString().trim());

			Log.e("JSON LENGHT ", "" + jArray.length());
         	 for(int i=0;i<jArray.length();i++)
         	 {
         		 ArrayList<String>memeberFeild = new ArrayList<String>();
         		 JSONObject jsonObject = jArray.getJSONObject(i); 
         		 memeberFeild.add("" + jsonObject.getInt(AppConstant.CHURCH_MEMEBER_TAG_ID));
         	     memeberFeild.add(jsonObject.getString(AppConstant.CHURCH_MEMEBER_TAG_NAME));
         	     SplashActivity.churchMemeberList.add(memeberFeild);
         	 }
             Log.e("memberList","" + SplashActivity.churchMemeberList);	//update well the list
         	
         }
          catch(Exception e)
          {
         	e.printStackTrace(); 
         	 
          }
	}
	
	
	private String convertInputStreamToString(InputStream inputStream) throws IOException
	 {
	    BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
	    String line = "";
	    String result = "";
	    while((line = bufferedReader.readLine()) != null)
	        result += line;

	    inputStream.close();
	    return result;

	 }
	
}
