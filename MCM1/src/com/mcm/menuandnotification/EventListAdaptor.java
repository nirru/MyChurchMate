package com.mcm.menuandnotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcm.R;
import com.mcm.SplashActivity;

public class EventListAdaptor extends BaseAdapter {

	Context context;
	int layoutResourceId;
	String folderName;
	ArrayList<ArrayList<String>> values;
	String dateToPass, dayOfTheWeek, month, day, year, enddateToPass,
			enddayOfTheWeek, endmonth, endday, endyear, startdateToPass,
			startdayOfTheWeek, startmonth, startday, startyear;
	int hour, min, endhour, endmin, starthour, startmin;
	String amPm, endamPm, startamPm;
	Date customDate, eventStartTime, eventEndTime, eventDate,
			currentSystemDate;
	String email_String;
     int clientID;
	public EventListAdaptor(Context context,
			ArrayList<ArrayList<String>> values, int layoutResourceId,
			String folderName, Date currentSystemDate, String email_String,int clientID) {
		this.context = context;
		this.values = values;
		this.layoutResourceId = layoutResourceId;
		this.folderName = folderName;
		this.currentSystemDate = currentSystemDate;
		this.email_String = email_String;
		this.clientID = clientID;
		Log.e("VALUE COUNNR", "" + values.size());
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

		holder.eventFragementRow = (RelativeLayout) row
				.findViewById(R.id.relative_one);
		holder.fg_month_tv = (TextView) row.findViewById(R.id.fg_month);
		holder.fg_date_tv = (TextView) row.findViewById(R.id.fg_date);
		holder.fg_day_tv = (TextView) row.findViewById(R.id.fg_day);
		holder.fg_time_tv = (TextView) row.findViewById(R.id.fg_time);
		holder.fg_title_tv = (TextView) row.findViewById(R.id.fg_title);
		holder.fg_long_desc_tv = (TextView) row.findViewById(R.id.fg_long_desc);

		holder.fg_time_tv.setTypeface(SplashActivity.mpSemiBold);
		holder.fg_title_tv.setTypeface(SplashActivity.mpBold);
		holder.fg_long_desc_tv.setTypeface(SplashActivity.mpSemiBold);
		holder.fg_day_tv.setTypeface(SplashActivity.mpSemiBold);
		holder.fg_date_tv.setTypeface(SplashActivity.mpBold);
		holder.fg_month_tv.setTypeface(SplashActivity.mpSemiBold);

		holder.imageView = (ImageView) row.findViewById(R.id.imageView1);

		convertstringTodate(values.get(position).get(2).toString().trim());
		holder.fg_month_tv.setText(month);
		holder.fg_date_tv.setText(day);
		holder.fg_day_tv.setText(dayOfTheWeek);

		holder.fg_long_desc_tv.setText(values.get(position).get(6).toString()
				.trim());
		holder.fg_title_tv.setText(values.get(position).get(5).toString()
				.trim());
		holder.fg_time_tv.setText(convertstringTotime(values.get(position)
				.get(2).toString().trim()));

		eventStartTime = getDateTime(values.get(position).get(3).toString()
				.trim());
		eventEndTime = getDateTime(values.get(position).get(4).toString()
				.trim());

		eventDate = getDateTime(values.get(position).get(2).toString().trim());

		getTimeInCurrent(holder, eventEndTime, eventStartTime, eventDate);

		row.setId(position);
		row.setOnClickListener(l);
		return row;
	}

	View.OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {

			int id = v.getId();
			Log.e("ID IN RECEIVED", "" + id);
			displayView(id);
		}
	};

	static class ItemHolder {
		TextView fg_month_tv;
		TextView fg_date_tv;
		TextView fg_day_tv;
		TextView fg_time_tv;
		TextView fg_title_tv;
		TextView fg_long_desc_tv;
		ImageView imageView;
		RelativeLayout eventFragementRow;
	}

	private void displayView(int id) {
		convertstringTodate(values.get(id).get(2).toString().trim());
		convertstringToEnddate(values.get(id).get(4).toString().trim());
		convertstringToStartdate(values.get(id).get(3).toString().trim());
		String eventName = values.get(id).get(5).toString().trim();
		String eventDateTime = values.get(id).get(2).toString().trim();
		String eventlongDesc = values.get(id).get(7).toString().trim();
		String location = values.get(id).get(9).toString().trim();
		String reminder_flag = values.get(id).get(10).toString().trim();
		Date eventTime = getDateTime(values.get(id).get(2).toString().trim());
		eventStartTime = getDateTime(values.get(id).get(3).toString().trim());
		Log.e("DATE START TIME", "" + eventStartTime);
		Fragment fragment = new EventsDetails(context, eventName,
				eventDateTime, eventlongDesc, dateToPass, day, month, year,
				hour, min, amPm, enddateToPass, endday, endmonth, endyear,
				endhour, endmin, endamPm, startdateToPass,startday, startmonth, startyear,
				starthour, startmin, startamPm, folderName, eventTime,
				eventStartTime, location, reminder_flag,email_String, clientID);
		if (fragment != null) {
			if (context instanceof FragmentActivity) {
				// We can get the fragment manager
				FragmentActivity activity = (FragmentActivity) context;
				FragmentManager fragmentManager = activity
						.getSupportFragmentManager();
				fragmentManager.beginTransaction()
						.replace(R.id.frame_container, fragment)
						.addToBackStack(null).commit();
			}
		}

	}

	private String convertstringTodate(String date_String) {

		String dtStart = date_String;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(dtStart);
			dtStart = getMonthYear(date, dtStart);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("DATE HOUR", "" + date);

		return dtStart;
	}

	private String getMonthYear(Date date, String dateTimeString) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		dayOfTheWeek = (String) android.text.format.DateFormat.format("EEEE",
				date);
		month = (String) android.text.format.DateFormat.format("MMM", date);
		day = (String) android.text.format.DateFormat.format("dd", date); // 20
		year = (String) android.text.format.DateFormat.format("yyyy", date); // 20
		String textdate = month + "\n" + day + "\n" + dayOfTheWeek;

		String time_Am_Pm = convertstringTotime(dateTimeString);
		dateToPass = time_Am_Pm + " on " + dayOfTheWeek + " " + month + " "
				+ day + "," + year;

		return textdate;
	}

	private String convertstringTotime(String date_String) {
		String dtStart = "";

		SimpleDateFormat inputFormat24 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("KK:mm a");
		SimpleDateFormat outputFormatHour = new SimpleDateFormat("KK");
		SimpleDateFormat outputFormatMin = new SimpleDateFormat("mm");
		SimpleDateFormat outputFormatHMAmPm = new SimpleDateFormat("a");
		try {
			Date date = inputFormat24.parse(date_String);
			dtStart = outputFormatAmPm.format(date);
			hour = Integer.parseInt(outputFormatHour.format(date));
			min = Integer.parseInt(outputFormatMin.format(date));
			amPm = outputFormatHMAmPm.format(date);
			// Log.e("DATE HOUR", "" + date);
			return outputFormatAmPm.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	private Date getDateTime(String convertDateString) {
		SimpleDateFormat inputFormat24 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		try {
			customDate = inputFormat24.parse(convertDateString);

//			 Log.e("DATE customDate", "" + customDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return customDate;
	}

	private void getTimeInCurrent(ItemHolder myHolder, Date eventEnd,
			Date eventStart, Date eventFixedTime) {
		long diff = eventFixedTime.getTime() - currentSystemDate.getTime();
		if (diff >= 0) {
			myHolder.eventFragementRow.setVisibility(View.VISIBLE);
		} else {
			myHolder.eventFragementRow.setVisibility(View.GONE);
		}

	}

	private String convertstringToEnddate(String date_String) {

		String dtStart = date_String;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(dtStart);
			dtStart = getMonthYearforEndTime(date, dtStart);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("DATE HOUR", "" + date);

		return dtStart;
	}

	private String getMonthYearforEndTime(Date date, String dateTimeString) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		enddayOfTheWeek = (String) android.text.format.DateFormat.format(
				"EEEE", date);
		endmonth = (String) android.text.format.DateFormat.format("MMM", date);
		endday = (String) android.text.format.DateFormat.format("dd", date); // 20
		endyear = (String) android.text.format.DateFormat.format("yyyy", date); // 20
		String textdate = endmonth + "\n" + endday + "\n" + enddayOfTheWeek;

		String time_Am_Pm = convertstringTotimeforEndTime(dateTimeString);
		enddateToPass = time_Am_Pm + " on " + enddayOfTheWeek + " " + month
				+ " " + endday + "," + year;

		return textdate;
	}

	private String convertstringTotimeforEndTime(String date_String) {
		String dtStart = "";

		SimpleDateFormat inputFormat24 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("KK:mm a");
		SimpleDateFormat outputFormatHour = new SimpleDateFormat("KK");
		SimpleDateFormat outputFormatMin = new SimpleDateFormat("mm");
		SimpleDateFormat outputFormatHMAmPm = new SimpleDateFormat("a");
		try {
			Date date = inputFormat24.parse(date_String);
			dtStart = outputFormatAmPm.format(date);
			endhour = Integer.parseInt(outputFormatHour.format(date));
			endmin = Integer.parseInt(outputFormatMin.format(date));
			endamPm = outputFormatHMAmPm.format(date);
			// Log.e("DATE HOUR", "" + date);
			return outputFormatAmPm.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	////start time
	private String convertstringToStartdate(String date_String) {

		String dtStart = date_String;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(dtStart);
			dtStart = getMonthYearforStartTime(date, dtStart);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Log.e("DATE HOUR", "" + date);

		return dtStart;
	}
	
	private String getMonthYearforStartTime(Date date, String dateTimeString) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		startdayOfTheWeek = (String) android.text.format.DateFormat.format(
				"EEEE", date);
		startmonth = (String) android.text.format.DateFormat.format("MMM", date);
		startday = (String) android.text.format.DateFormat.format("dd", date); // 20
		startyear = (String) android.text.format.DateFormat.format("yyyy", date); // 20
		String textdate = endmonth + "\n" + endday + "\n" + enddayOfTheWeek;

		String time_Am_Pm = convertstringTotimeforStartTime(dateTimeString);
		startdateToPass = time_Am_Pm + " on " + enddayOfTheWeek + " " + month
				+ " " + endday + "," + year;

		return textdate;
	}

	private String convertstringTotimeforStartTime(String date_String) {
		String dtStart = "";

		SimpleDateFormat inputFormat24 = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat outputFormatAmPm = new SimpleDateFormat("KK:mm a");
		SimpleDateFormat outputFormatHour = new SimpleDateFormat("KK");
		SimpleDateFormat outputFormatMin = new SimpleDateFormat("mm");
		SimpleDateFormat outputFormatHMAmPm = new SimpleDateFormat("a");
		try {
			Date date = inputFormat24.parse(date_String);
			dtStart = outputFormatAmPm.format(date);
			starthour = Integer.parseInt(outputFormatHour.format(date));
			startmin = Integer.parseInt(outputFormatMin.format(date));
			startamPm = outputFormatHMAmPm.format(date);
			// Log.e("DATE HOUR", "" + date);
			return outputFormatAmPm.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	private void showEventList(ItemHolder myHolder, Date eventEnd,
			Date eventStart, Date eventFixedTime) {
		if (eventFixedTime.after(eventStart) && eventFixedTime.before(eventEnd)) {
			myHolder.eventFragementRow.setVisibility(View.VISIBLE);
		} else {
			myHolder.eventFragementRow.setVisibility(View.GONE);
		}
	}
}