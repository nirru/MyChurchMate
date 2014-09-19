package com.mcm.datepicker;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UiDatePicker extends Dialog {

	private Context context;
	private View view;
	private String pattern = "/";
	private UiToast uiToast;

	public int mYear;
	public int mMonth;
	public int mDay;
	private boolean isMultiNeed;
	InterfaceDateToPass dateToPass;
	public ArrayList<ArrayList<Integer>> listOfDate;

	public UiDatePicker(Context context, boolean isSupprotMultiple,
			InterfaceDateToPass interfaceDateToPass) {
		super(context);
		this.context = context;
		uiToast = new UiToast(context);
		this.isMultiNeed = isSupprotMultiple;
		listOfDate = new ArrayList<ArrayList<Integer>>();
		this.dateToPass = interfaceDateToPass;
	}

	/**
	 * 
	 * @param view
	 *            View of that widget which can handle returned string.
	 * @param separator
	 *            Provide a separator for returned date.
	 */
	public void showDialog(View view, String separator) {
		this.view = view;
		this.pattern = separator;

		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dialog = new DatePickerDialog(context,
				mDateSetListener, mYear, mMonth, mDay);
		dialog.show();
	}

	DatePickerDialog.OnDateSetListener mDateSetListener = new OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker v, int year, int monthOfYear,
				int dayOfMonth) {

			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			if (isMultiNeed) {
				ArrayList<Integer> setOfDate = new ArrayList<Integer>();
				setOfDate.add(mYear);
				setOfDate.add(mMonth);
				setOfDate.add(mDay);

				listOfDate.add(setOfDate);

			}
			setDate();
		}
	};

	private void setDate() {
		String str = String.format("%02d", mDay) + pattern
				+ String.format("%02d", (mMonth + 1)) + pattern
				+ String.format("%02d", mYear) + " ";

		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			if ((tv.getTag() == null) || (tv.getTag() != null)
					&& !(tv.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					tv.setText(tv.getText().toString() + str + " ");
				else
					tv.setText(str);
				dateToPass.onDateSet(mYear, mMonth, mDay);

				Log.e("I AM IN IF", "YES");
			}
		} else if (view instanceof EditText) {
			EditText ed = (EditText) view;
			if ((ed.getTag() == null) || (ed.getTag() != null)
					&& !(ed.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					ed.setText(ed.getText().toString() + str + " ");
				else
					ed.setText(str);
				dateToPass.onDateSet(mYear, mMonth, mDay);
			}
			Log.e("I AM IN ELSE", "YES");
		}

		Log.e("SETDATE FIRST", "" + "YES");
	}

	private void setDate(View v) {
		String str = String.format("%02d", mDay) + pattern
				+ String.format("%02d", (mMonth + 1)) + pattern
				+ String.format("%02d", mYear);

		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			if ((tv.getTag() == null) || (tv.getTag() != null)
					&& !(tv.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					tv.setText(tv.getText().toString() + str + " ");
				else
					tv.setText(str);
			}
		} else if (v instanceof EditText) {
			EditText ed = (EditText) v;
			if ((ed.getTag() == null) || (ed.getTag() != null)
					&& !(ed.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					ed.setText(ed.getText().toString() + str + " ");
				else
					ed.setText(str);
			}
		}
		Log.e("SETDATE SECOND", "" + "YES");
	}

	private void setDate(int year, int month, int date, View view) {
		String str = String.format("%02d", date) + pattern
				+ String.format("%02d", (month + 1)) + pattern
				+ String.format("%02d", year) + " ";

		if (view instanceof TextView) {
			TextView tv = (TextView) view;
			if ((tv.getTag() == null) || (tv.getTag() != null)
					&& !(tv.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					tv.setText(tv.getText().toString() + str + " ");
				else
					tv.setText(str);
			}
		} else if (view instanceof EditText) {
			EditText ed = (EditText) view;
			if ((ed.getTag() == null) || (ed.getTag() != null)
					&& !(ed.getTag().toString().equals("useNormalFont"))) {
				if (isMultiNeed)
					ed.setText(ed.getText().toString() + str + " ");
				else
					ed.setText(str);
			}
		}

		Log.e("SETDATE THIRD", "" + "YES");
	}

	public void setDateText(View view) {

		for (int i = 0; i < listOfDate.size(); i++) {
			setDate(listOfDate.get(i).get(0), listOfDate.get(i).get(1),
					listOfDate.get(i).get(2), view);
		}
	}

	public void setDate(long timeInMills, View view, String separator) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMills);
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);

		this.pattern = separator;

		setDate(view);
	}

	@SuppressWarnings("unused")
	private void checkPattern(String str) {
		String[] patternList = { "/", "-", "." };
		if (str.matches(patternList[0])) {
			this.pattern = str;
		} else if (str.matches(patternList[1])) {
			this.pattern = str;
		} else if (str.matches(patternList[2])) {
			this.pattern = str;
		} else {
			uiToast.showToast(
					"Invalid Pattern please choose form these /n / - .",
					Toast.LENGTH_LONG);
			return;
		}
	}

	public void removeDate(View view) {
		if (listOfDate.size() > 0) {
			listOfDate.remove(listOfDate.size() - 1);
			setDateText(view);
		}
	}

	public void addDate(long timeInMills) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeInMills);

		Log.w("Row",
				"" + "Year " + calendar.get(Calendar.YEAR) + " Month "
						+ calendar.get(Calendar.MONTH) + " Day "
						+ calendar.get(Calendar.DAY_OF_MONTH));
		ArrayList<Integer> setOfDate = new ArrayList<Integer>();
		setOfDate.add(calendar.get(Calendar.YEAR));
		setOfDate.add(calendar.get(Calendar.MONTH));
		setOfDate.add(calendar.get(Calendar.DAY_OF_MONTH));

		listOfDate.add(setOfDate);
	}

	public String getMonthInString() {

		// Log.e("day", "getDayOfMonth");

		Calendar calendar = Calendar.getInstance();
		calendar.set(mYear, mMonth, mDay);
		int i = calendar.get(Calendar.MONTH);
		String dayOfMonth = "";

		switch (i) {
		case 0:
			dayOfMonth = "January";
			break;
		case 1:
			dayOfMonth = "February";
			break;
		case 2:
			dayOfMonth = "March";
			break;
		case 3:
			dayOfMonth = "April";
			break;
		case 4:
			dayOfMonth = "May";
			break;
		case 5:
			dayOfMonth = "June";
			break;
		case 6:
			dayOfMonth = "July";
			break;
		case 7:
			dayOfMonth = "August";
			break;
		case 8:
			dayOfMonth = "September";
			break;
		case 9:
			dayOfMonth = "October";
			break;
		case 10:
			dayOfMonth = "November";
			break;
		case 11:
			dayOfMonth = "December";
			break;
		default:
			break;
		}

		// Log.e("day", dayOfMonth);

		return dayOfMonth;

	}
}