package com.mcm.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class SurveyNotificationPostJson {

	
	int clientId, memberId, pushNotificationId;
	boolean response;
	String url;
	
	String idClient, idMember, idpush;

	public SurveyNotificationPostJson(String url, int clientId , int memberId, int pushNotificationId, boolean response) {
		this.url = url;
		this.clientId = clientId;
		this.memberId = memberId;
		this.pushNotificationId = pushNotificationId;
		this.response = response;
		idClient = String.valueOf(clientId);
		idMember = String.valueOf(memberId);
		idpush  = String.valueOf(pushNotificationId);
		
		Log.e("MAY AYA", "" + "" + idClient + " , " + idMember + " , " + idpush);
	}

	public String postDataToServer() {
		InputStream inputStream = null;
		String result = "";
		try {

			// 1. create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 2. make POST request to the given URL
			HttpPost httpPost = new HttpPost(url);

			String json = "";

			// 3. build jsonObject
			JSONObject jsonObject = new JSONObject();
			jsonObject.accumulate("ClientId", idClient);
			jsonObject.accumulate("MemberId", idMember);
			jsonObject.accumulate("PushNotificationId",idpush);
			jsonObject.accumulate("Response", response);
			// 4. convert JSONObject to JSON to String
			json = jsonObject.toString();

			// ** Alternative way to convert Person object to JSON string usin
			// Jackson Lib
			// ObjectMapper mapper = new ObjectMapper();
			// json = mapper.writeValueAsString(person);

			// 5. set json to StringEntity
			StringEntity se = new StringEntity(json);

			// 6. set httpPost Entity
			httpPost.setEntity(se);

			// 7. Set some headers to inform server about the type of the
			// content
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			// 8. Execute POST request to the given URL
			HttpResponse httpResponse = httpclient.execute(httpPost);
			Log.e("RESPONSE FROM SERVER", ""
					+ httpResponse.getStatusLine().getStatusCode());

			// 9. receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// 10. convert inputstream to string
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 11. return result
//		Log.e("RESULT", "" + result);
		return result;
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
