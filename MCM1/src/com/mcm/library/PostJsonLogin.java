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

public class PostJsonLogin {

	String appPin, fName, sName, eMail, passWord, address_One, address_Two,
			street, town, city, county, state, country, postalCode,
			mobileNumber, sex, homeTelephone, physicalDeviceId, deviceOS,
			deviceIdFromGCMorAPNS, deviceType;
	String churMemeberShip_ID;
	String url;

	public PostJsonLogin(String url, String appPin, String fName, String sName,
			String eMail, String passWord, String address_One,
			String address_Two, String street, String town, String city,
			String county, String state, String country, String postalCode,
			String mobileNumber, String sex, String churMemeberShip_ID,
			String homeTelephone, String physicalDeviceId, String deviceOS,
			String deviceIdFromGCMorAPNS, String deviceType) {
		this.url = url;
		this.appPin = appPin;
		this.fName = fName;
		this.sName = sName;
		this.eMail = eMail;
		this.passWord = passWord;
		this.address_One = address_One;
		this.address_Two = address_Two;
		this.street = street;
		this.town = town;
		this.city = city;
		this.county = county;
		this.state = state;
		this.country = country;
		this.postalCode = postalCode;
		this.mobileNumber = mobileNumber;
		this.sex = sex;
		this.homeTelephone = homeTelephone;
		this.physicalDeviceId = physicalDeviceId;
		this.deviceOS = deviceOS;
		this.deviceIdFromGCMorAPNS = deviceIdFromGCMorAPNS;
		this.deviceType = deviceType;
		this.churMemeberShip_ID = churMemeberShip_ID;
		this.url = url;
		Log.e("MAY AYA", "" + "do in context");
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
			jsonObject.accumulate("ClientId", appPin);
			jsonObject.accumulate("FirstName", fName);
			jsonObject.accumulate("SurName", sName);
			jsonObject.accumulate("EmailId", eMail);
			jsonObject.accumulate("Password", passWord);
			jsonObject.accumulate("Address1", address_One);
			jsonObject.accumulate("Address2", address_Two);
			jsonObject.accumulate("Street", street);
			jsonObject.accumulate("Town", town);
			jsonObject.accumulate("City", city);
			jsonObject.accumulate("County", county);
			jsonObject.accumulate("State", state);
			jsonObject.accumulate("Country", country);
			jsonObject.accumulate("PostCode", postalCode);
			jsonObject.accumulate("Mobile", mobileNumber);
			jsonObject.accumulate("Sex", sex);
			jsonObject
					.accumulate("ChurchMembershipId", "" + churMemeberShip_ID);
			jsonObject.accumulate("HomeTelephone", "" + homeTelephone);
			jsonObject.accumulate("PhysicalDeviceId", ""+ physicalDeviceId);
			jsonObject.accumulate("DeviceOS", deviceOS);
			jsonObject.accumulate("DeviceIdFromGCMorAPNS",
					deviceIdFromGCMorAPNS);
			jsonObject.accumulate("DeviceType", deviceType);
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
