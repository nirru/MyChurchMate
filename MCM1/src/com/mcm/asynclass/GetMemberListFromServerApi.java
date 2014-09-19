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
import android.util.Log;

import com.mcm.appconstant.AppConstant;

public class GetMemberListFromServerApi {
	ProgressDialog pDialog;
	Context context;
	String url = "http://mcmwebapi.victoriatechnologies.com/api/MembershipTypes";
	String result = "";
	HttpResponse httpResponse = null;

	public GetMemberListFromServerApi(Context context) {
		this.context = context;
	}

	public String getMemeberTypes(String url) // get data.. list. from server
	{
		InputStream inputStream = null;

		try {
			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// 2. create HttpGet object:
			HttpGet httpGet = new HttpGet(url);
			// 3. finally make call to server and get the httpResponse:
			httpResponse = httpclient.execute(httpGet);
			// 4. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpGet);
			Log.e("RESPONSE MESSAGE", ""
					+ httpResponse.getStatusLine().getStatusCode());
			// 5. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
			// 6. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			e.printStackTrace();
		}// end catch..
		Log.e("result: ", result);
		// 7. return result
		return result;

	}

	public void parseJsonToArrayList() {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

		try {
			JSONArray jArray = new JSONArray(result.toString().trim());
			for (int i = 0; i < jArray.length(); i++) {
				ArrayList<String> memeberFeild = new ArrayList<String>();
				JSONObject jsonObject = jArray.getJSONObject(i);
				memeberFeild.add(""
						+ jsonObject.getInt(AppConstant.CHURCH_MEMEBER_TAG_ID));
				memeberFeild.add(jsonObject
						.getString(AppConstant.CHURCH_MEMEBER_TAG_NAME));
				data.add(memeberFeild);
			}
			Log.e("memberList", "" + data);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;
	}
}
