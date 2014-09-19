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

import com.google.android.gms.internal.fn;
import com.mcm.appconstant.AppConstant;

import android.util.Log;

public class SettingSavedMemberDetail {

	String message, url, login_member_first_name, login_member_sur_name,
			login_member_date_of_birth, login_member_profeesional_id,
			login_member_marital_status_id, login_member_number_of_children,
			login_member_nationality_id, login_member_sex,
			login_member_mobile_number, login_member_email,
			login_member_password, monthOFCalender;
	int memberID, clientID, login_member_church_member_ship_id;

	public SettingSavedMemberDetail(String url, int memberID, int clientID,
			String login_member_first_name, String login_member_sur_name,
			String login_member_date_of_birth,
			String login_member_profeesional_id,
			String login_member_marital_status_id,
			String login_member_number_of_children,
			String login_member_nationality_id,
			int login_member_church_member_ship_id, String login_member_sex,
			String login_member_mobile_number, String login_member_email,
			String login_member_password,String monthOFCalender) {

		this.url = url;
		this.memberID = memberID;
		this.clientID = clientID;
		this.monthOFCalender = monthOFCalender;
		this.login_member_first_name = login_member_first_name;
		this.login_member_sur_name = login_member_sur_name;
		this.login_member_date_of_birth = login_member_date_of_birth;
		this.login_member_profeesional_id = login_member_profeesional_id;
		this.login_member_marital_status_id = login_member_marital_status_id;
		this.login_member_number_of_children = login_member_number_of_children;
		this.login_member_nationality_id = login_member_nationality_id;
		this.login_member_church_member_ship_id = login_member_church_member_ship_id;
		this.login_member_sex = login_member_sex;
		this.login_member_mobile_number = login_member_mobile_number;
		this.login_member_email = login_member_email;
		this.login_member_password = login_member_password;
		Log.e("MAY AYA", "" + url);
		Log.e("FIRST NAME", "" + login_member_first_name);
		Log.e("login_member_sur_name", "" + login_member_sur_name);
		Log.e("FIRST NAME", "" + login_member_first_name);
		Log.e("DOB", "" + monthOFCalender);
		Log.e("SETTING_PROFESSIONAL_ID", "" + login_member_profeesional_id);
		Log.e("SETTING_MARITAL_ID", "" + login_member_marital_status_id);
		Log.e("SETTING_NATIONALITY_ID", "" + login_member_nationality_id);
		Log.e("SETTING_CHURCH_MEMBERSHIP_ID", "" + login_member_church_member_ship_id);
		Log.e("SETTING_SEX", "" + login_member_sex);
		Log.e("SETTING_EMAIL", "" + login_member_email);
		Log.e("SETTING_PASSWORD", "" + login_member_password);
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
			jsonObject.accumulate(AppConstant.SETTING_MEMBER_ID, "" + memberID);
			jsonObject.accumulate(AppConstant.SETTING_CLIENT_ID, "" + clientID);
			jsonObject.accumulate(AppConstant.SETTING_FIRST_NAME,
					login_member_first_name);
			jsonObject.accumulate(AppConstant.SETTING_SURNAME,
					login_member_sur_name);
			jsonObject.accumulate(AppConstant.SETTING_DATE_OF_BIRTH,
					monthOFCalender);
			jsonObject.accumulate(AppConstant.SETTING_PROFESSIONAL_ID, "" + login_member_profeesional_id);
			jsonObject.accumulate(AppConstant.SETTING_MARITAL_ID, "" + login_member_marital_status_id);
			jsonObject.accumulate(AppConstant.SETTING_NATIONALITY_ID, "" + login_member_nationality_id);
			jsonObject.accumulate(AppConstant.SETTING_CHURCH_MEMBERSHIP_ID,
					"" + login_member_church_member_ship_id);
			jsonObject.accumulate(AppConstant.SETTING_SEX, login_member_sex);
			jsonObject.accumulate(AppConstant.SETTING_MOBILE_NUMBER,
					login_member_mobile_number);
			jsonObject.accumulate("EmailId", login_member_email);
			jsonObject.accumulate("Password", login_member_password);

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
			Log.d("InputStream", e.getLocalizedMessage());
		}

		// 11. return result
		 Log.e("RESULT", "" + result);
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
