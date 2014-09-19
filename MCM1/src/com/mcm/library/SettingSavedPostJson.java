package com.mcm.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class SettingSavedPostJson {

	int clientId, memberId, pushNotificationId;
	boolean response;
	String url;
	ArrayList<String> savedCheckedItem;

	public SettingSavedPostJson(String url, int memberId,
			ArrayList<String> savedCheckedItem) {
		this.url = url;
		this.memberId = memberId;
		this.savedCheckedItem = savedCheckedItem;
	}

	public String postDataToServer() {
		String result = "";

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			for (int i = 0; i < savedCheckedItem.size(); i++) {
				nameValuePairs.add(new BasicNameValuePair("MemberId", ""
						+ memberId));
				nameValuePairs.add(new BasicNameValuePair("InterestId", ""
						+ savedCheckedItem.get(i)));
			}

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 11. return result
		Log.e("RESULT", "" + result);
		return result;
	}
}
